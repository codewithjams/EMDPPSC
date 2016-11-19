<?php
require("config.inc.php");
if(!empty($_POST))
{
	/////////////////RUNNING LOGGED ONE'S//////////////
	$id = $_POST["loggedId"];
	$query = "Select * FROM notifications WHERE requestedId = '$id' or loggedId = '$id'";	//changed loggedId to id
	$query_params = array();
	try 
	{
		$stmt   = $db->prepare($query);
		$result = $stmt->execute($query_params);
	}
	catch (PDOException $ex) 
	{
		$response["success"] = 0;
		$response["message"] = "Database Error!";
		$response["notifs"] = array();
		die(json_encode($response));
	}
	$rows = $stmt->fetchAll();
	if($rows)
	{
			$response["success"] = 1;
			$response["message"] = "You have notification(s)";
			$response["notifs"] = array();
			
			foreach ($rows as $row)						///////////CHANGED, ADDED IF ELSE IF's//////////
			{		
					$notif = array();
					$notif["loggedId"]=$row["loggedId"];
					$notif["requestedId"]=$row["requestedId"];
					$notif["requestedField"]=$row["requestedField"];
					$notif["status"]=$row["status"];
					array_push($response["notifs"],$notif);
				
				
			}
		
			echo json_encode($response);
			
	}
	else
	{
		$response["success"] = 0;
		$response["message"] = "No Notifications";
		$response["notifs"] = array();
		echo json_encode($response);
	}	
}
else{
	?>
		<h1>Search</h1>
		<form action = "checkNotifications.php" method = "post">
			Logged ID :      <input type = "text" name = "loggedId" ><br/>
			<br/>
			<br/>
			<hr>
			<input type="submit" value = "search"/>
		</form>
	<?php
}
?>

