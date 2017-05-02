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

function newThread($createdBy,$receiver){
    include('connect.php');
   $date = new DateTime();
   $query = "Insert Into  Thread (created_by_id,subject,created_at,is_spam) VALUES (".$createdBy.",'".$createdBy."_".$receiver."',NOW(),0) ";
   if (mysqli_query($conn, $query)) {
     $threadId = getThreadId($createdBy, $receiver);

     $queryThreadMetadata = "Insert Into  thread_metadata (thread_id,participant_id,is_deleted) VALUES (".$threadId.",".$receiver.",0) ";
        if (mysqli_query($conn, $queryThreadMetadata)) {
          echo "Succees";
        }
        else {
           echo "Error: " . $queryThreadMetadata . "<br>" . mysqli_error($conn);
    }
   } else {
       echo "Error: " . $query . "<br>" . mysqli_error($conn);
}

}



$body=$_GET['body'];
$receiver=$_GET['receiver'];
$sender=$_GET['sender'];
$date = new DateTime();
$threadId = getThreadId($sender,$receiver);
if( $threadId == -1){
  newThread($sender,$receiver);
  $threadId = getThreadId($sender,$receiver);
}
$query = "Insert Into  Message (thread_id,sender_id,body,created_at) VALUES (".$threadId.",".$sender.",'".$body."',NOW() ) ";

if (mysqli_query($conn, $query)) {
    echo "success";
} else {
    echo "Error: " . $query . "<br>" . mysqli_error($conn);
}

mysqli_close($conn);
?>
