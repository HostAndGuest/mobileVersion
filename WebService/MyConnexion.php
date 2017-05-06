<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "hng_db_full_project";
// SETUP MyConnexion
$conn = new mysqli($servername, $username, $password, $dbname);
// Check MyConnexion
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>