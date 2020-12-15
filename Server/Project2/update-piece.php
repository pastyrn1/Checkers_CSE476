<?php
require_once "db.inc.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';

// Ensure the xml post item exists
if(!isset($_POST['xml'])) {
    echo '<checkers status="no" msg="missing XML" />';
    exit;
}

processXml(stripslashes($_POST['xml']));

echo '<checkers status="yes"/>';

/**
 * Process the XML query
 * @param $xmltext the provided XML
 */
function processXml($xmltext) {
    // Load the XML
    $xml = new XMLReader();
    if(!$xml->XML($xmltext)) {
        echo '<checkers status="no" msg="invalid XML" />';
        exit;
    }

    // Connect to the database
    $pdo = pdo_connect();

    // Read to the start tag
    while($xml->read()) {
        if($xml->nodeType == XMLReader::ELEMENT && $xml->name == "checkers") {

            $user = $xml->getAttribute("user");
            $password = $xml->getAttribute("pw");

            $userid = getUser($pdo, $user, $password);

            // Read to the checkerboard tag
            while($xml->read()) {
                if($xml->nodeType == XMLReader::ELEMENT &&
                    $xml->name == "checkertable") {

                    $xidx = $xml->getAttribute("xidx");
                    $yidx = $xml->getAttribute("yidx");
                    $king= $xml->getAttribute("king");

                    // Checks
                    if(!is_numeric($xidx) || !is_numeric($yidx) || !is_numeric($king)) {
                        echo '<checkers status="no" msg="invalid" />';
                        exit;
                    }

                    //TODO: create kill condition, check replace into does not create fresh pieces
                    $query = <<<QUERY
REPLACE INTO checkertable(userid, xidx, yidx, king)
VALUES('$userid', $xidx, $yidx, $king)
QUERY;

                    //$query = <<<QUERY
//DELETE FROM checkertable
//WHERE checkertable.xidx = '$xidx' AND checkertable.yidx = '$yidx'
//QUERY;

                    if(!$pdo->query($query)) {
                        echo '<checkers status="no" msg="insertfail">' . $query . '</checkers>';
                        exit;
                    }

                    echo '<checkers status="yes"/>';
                    exit;
                }
            }
        }
    }


}

/**
 * Ask the database for the user ID. If the user exists, the password
 * must match.
 * @param $pdo PHP Data Object
 * @param $user The user name
 * @param $password Password
 * @return id if successful or exits if not
 */
function getUser($pdo, $user, $password) {
    // Does the user exist in the database?
    $userQ = $pdo->quote($user);
    $query = "SELECT id, password from chessplayer where user=$userQ";

    $rows = $pdo->query($query);
    if($row = $rows->fetch()) {
        // We found the record in the database, check the password
        if($row['password'] != $password) {
            echo '<checkers status="no" msg="password error" />';
            exit;
        }

        return $row['id'];
    }

    echo '<checkers status="no" msg="user error" />';
    exit;
}
?>




?>