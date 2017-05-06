<?php
	require_once('connect.php');
	
	$json = new SimpleXMLElement('<xml/>');
	
	$deleteBookingQuery = "DELETE FROM booking WHERE id = ?";
	
	if (isset($_POST['booking_id']) && !empty($_POST['booking_id']) && $stmt = $conn->prepare($deleteBookingQuery))
	{
		$stmt->bind_param('i', $_POST['booking_id']);
		
		$stmt->execute();
		
		if (mysqli_stmt_affected_rows($stmt) > 0)
		{
			$json->addChild('status', 'success');
		}
		else
		{
			$json->addChild('status', 'failed - delete_booking');
		}
	}
	else
	{
		$json->addChild('status', 'failed - missing_params');
	}
	
	echo $conn->error;
	$conn->close();
	
	echo(json_encode($json));
?>