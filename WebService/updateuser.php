<?php
require_once('connect.php');


$id=$_GET['id'];
$oldpassword=$_GET['oldpassword'];
$newpassword=$_GET['newpassword'];
$passwordenc=password_hash($newpassword, PASSWORD_DEFAULT);
$lastname=$_GET['lastname'];
$firstname=$_GET['firstname'];
$sql = "UPDATE `fos_user` SET `last_name`='".$lastname."',`first_name`='".$firstname."',`password`='".$passwordenc."'WHERE `id`=".$id."";

if (mysqli_query($conn, $sql)) {
    echo "success";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}

mysqli_close($conn);

/**
<?php
require_once('connect.php');


$id=$_GET['id'];
$oldpassword=$_GET['oldpassword'];
$newpassword=$_GET['newpassword'];
$lastname=$_GET['lastname'];
$firstname=$_GET['firstname'];
$sql = " UPDATE fos_user set last_name = ? , first_name = ? , password = ? WHERE id = ? ";
$stmt = $conn->prepare($sql);
mysqli_stmt_bind_param($stmt, 'sssi', $_GET['lastname'], $_GET['firstname'], $_GET['newpassword'], $_GET['id']);
$stmt->execute();
$stmt->bind_result($queryresult);
$stmt->store_result();
$stmt->fetch();
var_dump($id,$newpassword,$oldpassword,$lastname,$firstname);
if (!empty($queryresult))
{
	echo 'success';

}
else
	echo 'fail ! ';

mysqli_close($conn);
?>
*/
?>

