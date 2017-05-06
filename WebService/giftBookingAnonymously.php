<?php
	require_once('connect.php');

	$json = new SimpleXMLElement('<xml/>');
	
	$adBookingQuery = "insert into booking (id, property_id, isGift, term, reviewed, booking_date, nb_reserved_rooms, "
								. "total_amount) values (null, ?, 1, ?, 0, ?, ?, ?)";	
		
	if (isset($_POST['property_id']) && isset($_POST['term']) && isset($_POST['booking_date']) 
		&& isset($_POST['nb_reserved_rooms']) && isset($_POST['total_amount']) && $stmt = $conn->prepare($adBookingQuery))
	{
		$stmt->bind_param("iisii", $_POST['property_id'], $_POST['term'], $_POST['booking_date'], $_POST['nb_reserved_rooms'], $_POST['total_amount']);
		
		$stmt->execute();
		
		if (mysqli_stmt_affected_rows($stmt) > 0)
		{
			$json->addChild('status', 'success');
		}
		else
		{
			$json->addChild('status', 'failed - gift booking');
		}
	}
	else
	{
		$json->addChild('status', 'failed - missing params');
	}
	
	$conn->close();
	
	echo(json_encode($json));
?>