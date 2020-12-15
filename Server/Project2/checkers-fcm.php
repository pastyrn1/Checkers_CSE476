<?php
//FIle: checkers-fcm.php

$pdo = pdo_connect();
$sql = "select token from checkers-fcm";
$stmt = $pdo->prepare($sql);
$stmt->execute(array());

$fcmIds = array();
foreach($stmt as $row) {
    echo $row['token'];
    $fcmIds[] = $row['token'];
}

// Open connection
$url = 'https://fcm.googleapis.com/fcm/send';
$ch = curl_init($url);
curl_setopt( $ch, CURLOPT_POST, true );
curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );

// Create the HTTP header
//API key for cloud messaging
$apiKey = "AAAAFWiSSsQ:APA91bHoTVDZWLefLrt_kIWYcbaIGfnqGHKVQ52ic3R1q234MXeSzyK7VCEkK8lV_i1J5EGUlcSGUONq73lZNzKiXjGqpqJtIPQilRAxIrTw9rMNKSKs2bYv1_YZ8-6D84BZ9Lk39-Ns";
$headers[0] = 'Authorization: key=' . $apiKey;
$headers[1] = 'Content-Type: application/json';
curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers);

// Message to send and ID's are POST data
$data['body'] = "update";

$fields['registration_ids'] = $fcmIds;
$fields['notification'] = $data;
echo '<pre>';
print_r($fields);


curl_setopt( $ch, CURLOPT_POSTFIELDS, json_encode( $fields ) );

// Execute HTTP post
$result = curl_exec($ch);

if (curl_errno($ch))
{
    echo 'FCM error: ' . curl_error($ch);
}

print_r($result);
echo '</pre>';

// Close connection
curl_close($ch);

function pdo_connect() {
    try {
        // Production server
        $dbhost="mysql:host=mysql-user.cse.msu.edu;dbname=pastyrn1";
        $user = "pastyrn1";
        $password = "cavaliere2";
        return new PDO($dbhost, $user, $password);
    } catch(PDOException $e) {
        die( "Unable to select database");
    }
}

