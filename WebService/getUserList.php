<?php
include('connect.php');



$sql = "SELECT trim(leading'".$_GET['userId']."_' FROM subject) as id FROM hostnguest.thread  where subject like '".$_GET['userId']."\_%'  UNION SELECT trim(trailing'_".$_GET['userId']."' FROM subject) FROM hostnguest.thread  where subject like '%\_".$_GET['userId']."'";
//echo $sql;
$result = $conn->query($sql);

$json = new SimpleXMLElement('<xml/>');
if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
      $query = "Select * from fos_user where id =".$row['id'];
      $result2 = $conn->query($query);
      $row2 = $result2->fetch_assoc();

      $mydata = $json->addChild('users');
      $mydata->addChild('id',$row2['id']);
      $mydata->addChild('username',$row2['username']);
         }
} else {
    echo "0 results";
}
$conn->close();
	 echo( json_encode ($json));
?>
