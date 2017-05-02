<?php
require_once('MyConnexion.php');
$host_id=$_GET['hostId'];
$sql = "SELECT * FROM property where host_id =$host_id";
$result = $conn->query($sql);
$json = new SimpleXMLElement('<xml/>');
if ($result->num_rows > 0) {
    while($row = $result->fetch_array()) {
       $mydata = $json->addChild('Properties');
        $mydata->addChild('Id',$row['id']);
        $mydata->addChild('hostId',$row['host_id']);
        $mydata->addChild('nbRooms',$row['nb_rooms']);
        $mydata->addChild('price',$row['price']);
        $mydata->addChild('publicationDate',$row['publication_date']);
        $mydata->addChild('description',utf8_encode($row['description']));
        $mydata->addChild('equipements',$row['equipements']);
        $mydata->addChild('imagesPath',$row['images_path']);
        $mydata->addChild('location',$row['location']);
         }
} else {
    echo "0 results";
}
$conn->close();

echo(json_encode($json,JSON_UNESCAPED_UNICODE));

?>