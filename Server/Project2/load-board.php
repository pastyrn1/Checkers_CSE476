<?php
require_once "db.inc.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';

process();//$_GET['user'], $_GET['pw']);

/**
 * Process the query
 */
function process() {
    // Connect to the database
    $pdo = pdo_connect();

    //$userid = getUser($pdo, $user, $password);

    $query = "select player, xidx, yidx, king from currentcplayer inner join  checkertable on currentcplayer.id = checkertable.id";
    $rows = $pdo->query($query);

    echo "\r\n<checkers status=\"yes\">\r\n";
    foreach($rows as $row){
        $player = $row['player'];
        $xidx = $row['xidx'];
        $yidx = $row['yidx'];
        $king = $row['king'];

        echo "<checkertable player=\"$player\" xidx=\"$xidx\" yidx=\"$yidx\" king=\"$king\" />\r\n";
    }
    echo '</checkers>';
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
        // We found the record in the database
        // Check the password
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