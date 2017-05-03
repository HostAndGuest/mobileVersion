<?php
require_once('connect.php');

$username=$_GET['username'];
$usernamecanonical=ucfirst($username);
$password=$_GET['password'];
$passwordenc=password_hash($password, PASSWORD_DEFAULT);
$email=$_GET['email'];
$emailcanonical=ucfirst($email);
$lastname=$_GET['lastname'];
$firstname=$_GET['firstname'];
$enabled=1;
$role="a:0:{}";
$sql = "INSERT INTO fos_user ( username,username_canonical, password, email,email_canonical,roles,enabled,last_name,first_name)
VALUES ( '$username','$usernamecanonical','$passwordenc','$email','$emailcanonical','$role','$enabled','$lastname','$firstname')";

if (mysqli_query($conn, $sql)) {
    echo "success";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}

mysqli_close($conn);
?>