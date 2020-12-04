<?php
require_once "db.inc.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';

// Ensure the xml post item exists
if(!isset($_POST['xml'])) {
    echo '<checkers status="no" msg="missing XML" />';
    exit;
}

createPlayer(stripslashes($_POST['xml']));

echo '<checkers status="yes"/>';

/**
 * Process the XML query
 * @param $xmltext the provided XML
 */
function createPlayer($xmltext) {
    echo '<checkers status="yes"/>';
    exit;


    // Load the XML
    //$xml = new XMLReader();
    //if(!$xml->XML($xmltext)) {
   //     echo '<checkers status="no" msg="invalid XML" />';
   //     exit;
    //}

    // Connect to the database
    //$pdo = pdo_connect();

    // Read to the start tag
    //while($xml->read()) {
    //    if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == "checkers") {
            // We have the checkers tag
    //        $magic = $xml->getAttribute("magic");
    //        if ($magic != "NechAtHa6RuzeR8x") {
    //            echo '<checkers status="no" msg="magic" />';
    //            exit;
    //        }

     //       $user = $xml->getAttribute("user");
    //        $password = $xml->getAttribute("pw");

            //$query = <<<QUERY
//REPLACE INTO chessplayer(user, password)
//VALUES('$user', '$password')
//QUERY;

            //if(!$pdo->query($query)) {
            //    echo '<checkers status="no" msg="insertfail">' . $query . '</checkers>';
            //    exit;
            //}

    //        echo '<checkers status="yes"/>';
    //        exit;

    //    }

    //}

}
?>