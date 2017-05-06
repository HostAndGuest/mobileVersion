<?php
	// only errors
	// maybe log instead (view http://stackoverflow.com/questions/1645661/turn-off-warnings-and-errors-on-php-mysql)
	error_reporting(E_ERROR);
	
	// http does not allow unlinking therefore using path on server
	if (unlink("C:/wamp64/www/host_and_guest_webservices/files/" . $_POST['videoName'] . ".mp4"))
	{
		echo '1';
	}
	else
	{
		echo '0';
	}
?>