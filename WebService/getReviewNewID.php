<?php
	require_once('connect.php');

	$json = new SimpleXMLElement('<xml/>');
	
		
	$getLastIDQuery = "SELECT id FROM `review`order by id DESC LIMIT 1";
					
	if ($stmt = $conn->prepare($getLastIDQuery))
	{
		$stmt->execute();
			
		$stmt->bind_result($lastID);
		$stmt->store_result();
		$stmt->fetch();
					
		if (!empty($lastID))
			$json->addChild('futureID', $lastID + 1);
		else
			$json->addChild('futureID', 1);
	}
	else
	{
		$json->addChild('futureID', 1);
	}
	
	$conn->close();
	
	echo(json_encode($json));
?>