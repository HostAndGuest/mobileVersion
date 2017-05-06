<?php
	require_once('connect.php');

	$json = new SimpleXMLElement('<xml/>');
	
	$adBookingQuery = "insert into booking values (null, ?, ?, ?, 0, ?, ?, ?, 0)";	
		
	if (isset($_POST['property_id']) && isset($_POST['guest_id']) && isset($_POST['term']) && isset($_POST['booking_date']) 
		&& isset($_POST['nb_reserved_rooms']) && isset($_POST['total_amount']) && $stmt = $conn->prepare($adBookingQuery))
	{
		$stmt->bind_param("iiisii", $_POST['property_id'], $_POST['guest_id'], $_POST['term'], $_POST['booking_date'], $_POST['nb_reserved_rooms'], $_POST['total_amount']);
		
		$stmt->execute();
		
		if (mysqli_stmt_affected_rows($stmt) > 0)
		{
			$json->addChild('status', 'success');
		}
		else
		{
			$json->addChild('status', 'failed - add_booking');
		}
	}
	else
	{
		$json->addChild('status', 'failed - missing params');
	}
	
	$conn->close();
	
	echo(json_encode($json));
?>