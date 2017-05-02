<?php
include('connect.php');

function getThreadId($createdBy,$receiver) {
  include('connect.php');
   $querySelectId = "select id from  Thread where subject = '".$createdBy ."_".$receiver."' OR subject ='".$receiver ."_".$createdBy."'";
   $res = $conn->query($querySelectId);
   $row1 = $res->fetch_assoc();
  if($row1['id'])
  return $row1['id'];
  else
  return -1;
 }

 $threadId = getThreadId($_GET['user1'],$_GET['user2']);

if($threadId != -1){
  $sql = "SELECT * FROM message WHERE thread_id =".$threadId." AND (sender_id=".$_GET['user1']." OR sender_id =".$_GET['user2'].")";
  //echo $sql;
  $result = $conn->query($sql);

  $json = new SimpleXMLElement('<xml/>');
  if ($result->num_rows > 0) {
      // output data of each row
      while($row = $result->fetch_assoc()) {
        $mydata = $json->addChild('messages');
        $mydata->addChild('id',$row['id']);
        $mydata->addChild('sender_id',$row['sender_id']);
        $mydata->addChild('created_at',$row['created_at']);
        $mydata->addChild('body',$row['body']);
           }
  } else {
      echo "0 results";
  }
}
$conn->close();
	 echo( json_encode ($json));
?>
