<?php
require_once('connect.php');

 $username = $_GET['username'];


$sql = "SELECT * FROM fos_user WHERE username='$username'  ";

$json = new SimpleXMLElement('<xml/>');

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
      $mydata = $json->addChild('user');
        $mydata->addChild('id',$row['id']);
        $mydata->addChild('username',$row['username']);
        $mydata->addChild('password',$row['password']);
		$mydata->addChild('lastname',$row['last_name']);
		$mydata->addChild('firstname',$row['first_name']);
        $mydata->addChild('enabled',$row['enabled']);
	}
         }


	else {
    echo "0 results";
	}
	

/////////////////////////////////////////////////////////////
$conn->close();
	 echo( json_encode ($json));
?>