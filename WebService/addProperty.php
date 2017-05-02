<?php
require_once('MyConnexion.php');
$hostId = $_GET['hostId'];
$imgs = $_GET['img'];
$price = $_GET['price'];
$description = $_GET['description'];
$check = $_GET['check'];
$location = $_GET['location'];
//$pic = array("../../../web/images/uploads/iconFind.png");
$nbroom = $_GET['nbroom'];
$date =  date('Y-m-d H:i:s');
$queryInsert = "INSERT INTO property (host_id,equipements,images_path,nb_rooms,price,description,location,reported,enabled,publication_date)VALUES (".$hostId.",'".serialize($check)."','".serialize($imgs)."',".$nbroom.",".$price.",'".$description."','".$location."',false,true,'".$date."');";       
if (mysqli_query($conn, $queryInsert)) {
    echo "Student Added";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}

mysqli_close($conn);
?>