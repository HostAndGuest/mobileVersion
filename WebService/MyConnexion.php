<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "hostnguest";
// SETUP MyConnexion
$conn = new mysqli($servername, $username, $password, $dbname);
// Check MyConnexion
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>