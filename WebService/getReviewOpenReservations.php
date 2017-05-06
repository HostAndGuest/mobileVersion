<?php
	require_once('connect.php');

	$json = new SimpleXMLElement('<xml/>');	
	
	$getReviewsQuery = "SELECT b.* " .
						"FROM booking b INNER JOIN property p ON p.id = b.property_id " .
						"WHERE b.reviewed = 0 " .
						"AND DATE_ADD(booking_date, INTERVAL term DAY) <= CURRENT_DATE() " .
						"AND b.guest_id = ? " .
						"AND p.id = ? " .
						"ORDER BY `b`.`booking_date` DESC";
						
	if (isset($_GET['user_id']) && isset($_GET['property_id']) && !empty($_GET['property_id']) && !empty($_GET['user_id'])
		&& $stmt = $conn->prepare($getReviewsQuery))
	{
		$stmt->bind_param("ii", $_GET['user_id'], $_GET['property_id']);
		
		$stmt->execute();
		
		$result = $stmt->get_result();
		
		while($row = $result->fetch_assoc()) 
		{
			$mydata = $json->addChild('booking');
			
			$mydata->addChild('id',$row['id']);
			$mydata->addChild('guest_id',$row['guest_id']);
			$mydata->addChild('term',$row['term']);
			$mydata->addChild('reviewed',$row['reviewed']);
			$mydata->addChild('booking_date',$row['booking_date']);
		}
	}
	else
	{
		$json->addChild('failed', 'open_bookings - missing_or_empty_params');
	}
	
	$conn->close();
	
	echo(json_encode($json));
?>