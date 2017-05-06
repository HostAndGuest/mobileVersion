<?php
	require_once('connect.php');

	$json = new SimpleXMLElement('<xml/>');
	
	$acquireGiftQuery = "UPDATE Booking SET guest_id = ?, isGift = 0 where id = ?";	
		
	if (isset($_POST['booking_id']) && isset($_POST['user_id'])  && !empty($_POST['user_id']) && !empty($_POST['booking_id']) && $stmt = $conn->prepare($acquireGiftQuery))
	{
		$stmt->bind_param("ii", $_POST['user_id'], $_POST['booking_id']);
		
		$stmt->execute();
		
		if (mysqli_stmt_affected_rows($stmt) > 0)
		{
			$json->addChild('status', 'success');
		}
		else
		{
			$json->addChild('status', 'failed - acquire_gift');
		}
	}
	else
	{
		$json->addChild('status', 'failed - missing_or_empty_params');
	}
	
	$conn->close();
	
	echo(json_encode($json));
?>