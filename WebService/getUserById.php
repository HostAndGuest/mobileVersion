<?php
include('connect.php');
 $querySelectId = "Select username from fos_user where id =".$_GET['userId'];
 $result2 = $conn->query($querySelectId);
 $row2 = $result2->fetch_assoc();
 echo $row2['username'];

 mysqli_close($conn);
 ?>
