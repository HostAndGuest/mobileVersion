<?php
require_once('MyConnexion.php');
$PropId = $_GET['PropId'];
$sql = "DELETE FROM property where id = $PropId";
$result = $conn->query($sql);
echo "Your Property Id: ".$PropId." DELETED !";

?>