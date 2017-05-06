<?php
	require_once('connect.php');

	$json = new SimpleXMLElement('<xml/>');						
	
	if (isset($_GET['property_id']) && isset($_GET['showGiftList']) && isset($_GET['user_id']) && 
		!empty($_GET['property_id']) && !empty($_GET['showGiftList']) && !empty($_GET['user_id']))
	{
		$getBookingsQuery = "select b.id, property_id, term, booking_date, nb_reserved_rooms, total_amount, first_name, last_name " . 
										"from booking b, fos_user u " . 
										"where guest_id = u.id and guest_id = ? ";
		
		if ($_GET['showGiftList'] == "true")
		{
			$getBookingsQuery = "select id, property_id, term, booking_date, nb_reserved_rooms, total_amount " . 
											"from booking b " . 
											"where isGift = 1 and property_id = ? ";
		}
		
		// default ASC
		$getBookingsQuery .= "Order by booking_date";
		
		if ($stmt = $conn->prepare($getBookingsQuery))
		{
			if ($_GET['showGiftList'] == "true")
				$stmt->bind_param("i", $_GET['property_id']);
			else
				$stmt->bind_param("i", $_GET['user_id']);
			
			$stmt->execute();
			
			$result = $stmt->get_result();
					
			while($row = $result->fetch_assoc()) 
			{
				$mydata = $json->addChild('booking');
		
				$mydata->addChild('id',$row['id']);
				$mydata->addChild('property_id',$row['property_id']);
				$mydata->addChild('term',$row['term']);
				$mydata->addChild('booking_date',$row['booking_date']);
				$mydata->addChild('nb_reserved_rooms',$row['nb_reserved_rooms']);
				$mydata->addChild('total_amount',$row['total_amount']);
				
				if ($_GET['showGiftList'] == "false")
			    {
					$mydata->addChild('first_name',$row['first_name']);
					$mydata->addChild('last_name',$row['last_name']);
				}
			}
		}
		else
		{
			$json->addChild('booking', 'failed - statement_preparation');
		}
	}
	else
	{
		$json->addChild('booking', 'failed - getBookings');
	}
	
	$conn->error;
	$conn->close();
	echo(json_encode($json));
?>