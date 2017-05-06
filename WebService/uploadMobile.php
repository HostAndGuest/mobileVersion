<?php
	if (is_uploaded_file($_FILES['userfile']['tmp_name'])) 
	{
	  echo "File ". $_FILES['userfile']['name'] ." uploaded successfully.\n";
	  move_uploaded_file ($_FILES['userfile'] ['tmp_name'], 'files/' . $_GET['fileName'] . '.mp4');
	}
	else
	{
	  echo "Possible file upload attack: ";
	  echo "filename '". $_FILES['userfile']['tmp_name'] . "'.";
	  print_r($_FILES);
	}
?>