<?php
	require_once('connect.php');

	$json = new SimpleXMLElement('<xml/>');
	
	// retrieving the video url if exists
	$getVideoUrlQuery = "SELECT video_url FROM review where id = ?";
	
	if (isset($_POST['review_id']) && isset($_POST['booking_id']) && $stmt = $conn->prepare($getVideoUrlQuery))
	{
		$stmt->bind_param("i", $_POST['review_id']);
		
		$stmt->execute();
		
		$stmt->bind_result($videoName);
		$stmt->store_result();
		$stmt->fetch();
		
		if (!empty($videoName))
		{
			$videoName = substr($videoName, strripos($videoName, '/') + 1);
			$name = substr($videoName, 0, strpos($videoName, '.mp4'));
			
			// calling delete video file web service
			$url = 'http://localhost/host_and_guest_webservices/deleteFile.php';
			$data = array('videoName' => $name);

			$options = array(
				'http' => array(
					'header'  => "Content-type: application/x-www-form-urlencoded\r\n",
					'method'  => 'POST',
					'content' => http_build_query($data),
				),
			);
			$context  = stream_context_create($options);
			$response = file_get_contents($url, false, $context);
			
			if ($response == 0)
				// $json->addChild('status', 'failed - delete_video_file');
				$json->addChild('failed - delete_video_file');
		}
		
		// deleting review
		$deleteReviewQuery = "DELETE FROM review WHERE id = ?";
		
		if ($stmt = $conn->prepare($deleteReviewQuery))
		{
			$stmt->bind_param("i", $_POST['review_id']);
		
			$stmt->execute();
			
			if (mysqli_stmt_affected_rows($stmt) > 0)
			{
				// updating booking reviwed status
				$updateBknReviewed = "UPDATE booking SET reviewed = 0 WHERE id = ?";
				
				if ($stmt = $conn->prepare($updateBknReviewed))
				{
					$stmt->bind_param("i", $_POST['booking_id']);
					$stmt->execute();
					
					if (mysqli_stmt_affected_rows($stmt) > 0)
						$json->addChild('success');
					else
						$json->addChild('failed - error_updating_booking - no_rows_affected');
				}
				else
					$json->addChild('failed - error_updating_booking_reviewed_status');
			}
			else
				$json->addChild('failed - no_rows_deleted');
		}
	}
	else
	{
		$json->addChild('failed - delete_review');
	}
	
	$conn->close();
	
	echo(json_encode($json));
?>