<?php
require_once "db.inc.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';

set();

/**
 * Set the board
 */
function set() {
    // Connect to the database
    $pdo = pdo_connect();

    $userid1 = getPlayer($pdo, 1);
    $userid2 = getPlayer($pdo, 2);

    $query = "TRUNCATE TABLE checkertable";

    if (!$pdo->query($query)) {
        echo '<checkers status="no" msg="truncatefail">' . $query . '</checkers>';
        exit;
    }

    // Create lower pieces (p1)
    for($i = 5; $i < 8; $i++){
        for($j = 0; $j < 8; $j++){
            if($i % 2 !=  $j % 2) {

                $query = <<<QUERY
INSERT INTO checkertable (id, xidx, yidx, king) 
VALUES ($userid1, $j, $i, 0)
QUERY;
                if (!$pdo->query($query)) {
                    echo '<checkers status="no" msg="pieceaddfail">' . $query . '</checkers>';
                    exit;
                }

            }
        }
    }

    // Create upper pieces (p2)
    for($i = 0; $i < 3; $i++){
        for($j = 0; $j < 8; $j++){
            if($i % 2 !=  $j % 2) {

                $query = <<<QUERY
INSERT INTO checkertable (id, xidx, yidx, king) 
VALUES ($userid2, $j, $i, 0)
QUERY;
                if (!$pdo->query($query)) {
                    echo '<checkers status="no" msg="pieceaddfail">' . $query . '</checkers>';
                    exit;
                }

            }
        }
    }

    echo '<checkers status="yes" msg="checkerboard is set"/>';
    exit;
}

/**
 * Ask the database for the user ID. If the user exists, the password
 * must match.
 * @param $pdo PHP Data Object
 * @param $player The player number
 * @return id if successful or exits if not
 */
function getPlayer($pdo, $player) {
    $query = "SELECT id from currentcplayer where player=$player";

    $rows = $pdo->query($query);
    if($row = $rows->fetch()) {
        // We found the player in the database
        return $row['id'];
    }

    echo '<checkers status="no" msg="player error" />';
    exit;
}
?>