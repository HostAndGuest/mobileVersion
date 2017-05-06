<?php
	require_once('connect.php');

	$json = new SimpleXMLElement('<xml/>');						
	
	if (isset($_GET['property_id']) && isset($_GET['bookingDate']) && isset($_GET['term']) && 
		!empty($_GET['property_id']) && !empty($_GET['bookingDate']) && !empty($_GET['term']))
	{
		$getFreeRoomsQuery = 'SELECT COALESCE(p.nb_rooms - COALESCE(SUM(b.nb_reserved_rooms), 0), 0) as FreeRooms ' . 
											'FROM booking b, property p ' . 
											'WHERE b.property_id = p.id ' . 
											'AND (booking_date BETWEEN "'.$_GET["bookingDate"].'" AND DATE_ADD("'.$_GET["bookingDate"].'", INTERVAL '.$_GET["term"].' DAY))  ' . 
											'AND p.id = ' . $_GET["property_id"];
		
		// if ($stmt = $conn->prepare($getFreeRoomsQuery))
		{
			// $stmt->bind_param("ssii", $_GET['bookingDate'], $_GET['bookingDate'], $_GET['term'], $_GET['property_id']);
			
			// $stmt->execute();
			
			// $stmt->bind_result($freeRooms);
			// $stmt->store_result();
			// $stmt->fetch();
			
			$res = mysqli_query($conn,$getFreeRoomsQuery);
			
			$row = mysqli_fetch_row($res);
			$sum = $row[0];
			
			$json->addChild('FreeRooms', $sum);
		}
		// else
		{
			// $json->addChild('status', 'failed - prepare_statement');
		}
	}
	else
	{
		$json->addChild('status', 'failed - missing_or_empty_params');
	}
	
	$conn->close();
	
	echo(json_encode($json));
?>