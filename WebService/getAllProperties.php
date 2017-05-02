<?php
require_once('MyConnexion.php');
$sql = "SELECT * FROM property";
$result = $conn->query($sql);
$json = new SimpleXMLElement('<xml/>');
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
       $mydata = $json->addChild('Properties');
        $mydata->addChild('Id',$row['id']);
        $mydata->addChild('hostId',$row['host_id']);
        $mydata->addChild('nbRooms',$row['nb_rooms']);
        $mydata->addChild('price',$row['price']);
        $mydata->addChild('publicationDate',$row['publication_date']);
        $mydata->addChild('description',utf8_encode($row['description']));
        $mydata->addChild('equipements',$row['equipements']);
        //$x= str_replace("../../../web/images/uploads/","",$row['images_path']);
        $mydata->addChild('imagesPath',$row['images_path']);
        $mydata->addChild('location',$row['location']);
         }
} else {
    echo "0 results";
}
$conn->close();
echo(json_encode($json,JSON_UNESCAPED_UNICODE));

?>