<?php

function pdo_connect() {
    try {
        // Production server
        $dbhost="mysql:host=mysql-user.cse.msu.edu;dbname=pastyrn1";
        $user = "pastyrn1";
        $password = "cavaliere2";
        return new PDO($dbhost, $user, $password);
    } catch(PDOException $e) {
        echo '<checkers status="no" msg="Unable to select database" />';
        exit;
    }
}
?>
