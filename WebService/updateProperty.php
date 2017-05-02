<?php
require_once('MyConnexion.php');
$propId = $_GET['propId'];
$price = $_GET['price'];
$description = $_GET['description'];
$check = $_GET['check'];
$location = $_GET['location'];
$nbroom = $_GET['nbroom'];
$queryUpdate = "UPDATE property set equipements='".serialize($check)."',nb_rooms=".$nbroom.",price=".$price.",description='".$description."',location='".$location."' WHERE id=".$propId;
if (mysqli_query($conn, $queryUpdate)) {
    echo "Student Updated!";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
mysqli_close($conn);
?>