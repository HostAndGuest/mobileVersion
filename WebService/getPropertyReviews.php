<?php
	require_once('connect.php');

	$getReviewsQuery = "SELECT r.id as rid, r.givenComment, r.date_comment, r.price_quality, r.lieu, r.precisionToDescription, r.communication, r.cleanliness, " .
                        "r.satisfaction_level, r.mood, r.video_url, b.id as bid, b.term, b.reviewed, b.booking_date, u.last_name, u.first_name, u.id as uid " .
                        "FROM review r " .
                        "INNER JOIN booking b ON b.id = r.booking_id " .
                        "INNER JOIN fos_user u on u.id = b.guest_id " .
                        "WHERE b.property_id = ? " .
						"ORDER BY r.date_comment DESC";

	$json = new SimpleXMLElement('<xml/>');
						
	if (isset($_GET['property_id']) && !empty($_GET['property_id']) && $stmt = $conn->prepare($getReviewsQuery))
	{
		$stmt->bind_param("i", $_GET['property_id']);
		
		$stmt->execute();
		
		$result = $stmt->get_result();
				
		while($row = $result->fetch_assoc()) 
		{	
			$mydata = $json->addChild('review');
	
			$mydata->addChild('rid',$row['rid']);
			$mydata->addChild('givenComment',$row['givenComment']);
			$mydata->addChild('date_comment',$row['date_comment']);
			$mydata->addChild('price_quality',$row['price_quality']);
			$mydata->addChild('lieu',$row['lieu']);
			$mydata->addChild('precisionToDescription',$row['precisionToDescription']);
			$mydata->addChild('communication',$row['communication']);
			$mydata->addChild('cleanliness',$row['cleanliness']);
			$mydata->addChild('satisfaction_level',$row['satisfaction_level']);
			$mydata->addChild('mood',$row['mood']);
			$mydata->addChild('video_url',$row['video_url']);
			$mydata->addChild('bid',$row['bid']);
			$mydata->addChild('term',$row['term']);
			$mydata->addChild('reviewed',$row['reviewed']);
			$mydata->addChild('booking_date',$row['booking_date']);
			$mydata->addChild('last_name',$row['last_name']);
			$mydata->addChild('first_name',$row['first_name']);
			$mydata->addChild('uid',$row['uid']);
		}
	}
	else
	{
		$json->addChild('failed', 'property_reviews - missing_or_empty_params');
	}
	
	$conn->close();
	
	echo(json_encode($json));
?>