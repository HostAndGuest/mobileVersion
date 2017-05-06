<?php
	require_once('connect.php');

	$json = new SimpleXMLElement('<xml/>');
	
	// adding videos not implemented
	
	if (isset($_POST['rid']) && isset($_POST['videoUrl']) && isset($_POST['booking_id']) && isset($_POST['comment']) && isset($_POST['priceQ']) && isset($_POST['position']) 
		&& isset($_POST['precision']) && isset($_POST['com']) && isset($_POST['clean']) && isset($_POST['sLevel']) && isset($_POST['mood']))
	{
		// settings reference to params
		$input['rid'] = $_POST['rid'];
		$input['videoUrl'] = $_POST['videoUrl'];
		$input['booking_id'] = $_POST['booking_id'];
		$input['reviewDate'] = date('Y-m-d');
		$input['comment'] = $_POST['comment'];
		$input['priceQ'] = $_POST['priceQ'];
		$input['position'] = $_POST['position'];
		$input['precision'] = $_POST['precision'];
		$input['com'] = $_POST['com'];
		$input['clean'] = $_POST['clean'];
		$input['sLevel'] = $_POST['sLevel'];
		$input['mood'] = $_POST['mood'];
		
		if (!in_array("", $input, true))
		{
			// update booking
			$updateBknQuery = "UPDATE booking SET reviewed = true WHERE id = ?";
			
			if ($stmt = $conn->prepare($updateBknQuery))
			{
				$stmt->bind_param("i", $input['booking_id']);
			
				$stmt->execute();
				
				if (mysqli_stmt_affected_rows($stmt) > 0)
				{
					// insert review
					// date param counts as string
					$insertReviewQuery = "INSERT INTO review VALUES (null, ?, ?, null, ?, ?, ?, ?, ?, ?, ?, ?)";
					
					if ($input['rid'] != "0")
						$insertReviewQuery = "INSERT INTO review VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					
					if ($stmt = $conn->prepare($insertReviewQuery))
					{
						if ($input['rid'] != "0")
							$stmt->bind_param("iisssiiiiiii", $input['rid'], $input['booking_id'], $input['comment'], $input['videoUrl'], $input['reviewDate'], $input['priceQ'], $input['position'], $input['precision'], 
								$input['com'], $input['clean'], $input['sLevel'], $input['mood']);
						else
							$stmt->bind_param("issiiiiiii", $input['booking_id'], $input['comment'], $input['reviewDate'], $input['priceQ'], $input['position'], $input['precision'], 
								$input['com'], $input['clean'], $input['sLevel'], $input['mood']);
						
						$stmt->execute();
											
						if (mysqli_stmt_affected_rows($stmt) > 0)
							$json->addChild('status', 'success');
						else
							$json->addChild('status', 'failed - review not added');
					}
					else
					{
						$json->addChild('status', 'failed - erorr add review');
					}
				}
				else
				{
					$json->addChild('status', 'failed - update booking reviewed');
				}
			}
			else
			{
				$json->addChild('status', 'failed - error update booking');
			}
		}
		else
		{
			$json->addChild('status', 'failed - empty params');
		}
		
	}
	else
	{
		$json->addChild('status', 'failed - missing params');
	}
	
	$conn->close();
	
	echo(json_encode($json));
?>