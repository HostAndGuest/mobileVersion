<?php
if(isset($_FILES['file']))
{ 
   // print_r($_FILES['file']);
     $dossier = '../PHPstormProjects/Host_n_Guest/web/images/uploads/';
     $type = str_replace ("image/","",$_FILES['file']['type']);
     $fichier = basename($_FILES['file']['tmp_name']).".".$type ;
     //echo $type;
     if(move_uploaded_file($_FILES['file']['tmp_name'], $dossier . $fichier)) //Si la fonction renvoie TRUE, c'est que ça a fonctionné...
     {
          echo $fichier;
     }
     else //Sinon (la fonction renvoie FALSE).
     {
          echo 'Echec de l\'upload !';
     }
}
?>