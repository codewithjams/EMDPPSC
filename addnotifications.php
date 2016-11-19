<?php
	require("config.inc.php");	
	if(!empty($_POST))
	{ 
	    $loggedId = $_POST["loggedId"];
		$requestedId = $_POST["requestedId"];
		$requestedField = $_POST["requestedField"];
		$targetId = $_POST["targetId"];
		$status = "RequestPending";
		$query = "INSERT INTO notifications (loggedId,requestedId,requestedField,targetId,status)
		VALUES 
		('$loggedId','$requestedId','$requestedField','$targetId','$status')";
		 $query_params = array();
	         
		try {
	        $stmt   = $db->prepare($query);
	        $result = $stmt->execute($query_params);
	    }
	    catch (PDOException $ex) {
		$response["success"] = 0;
	    $response["message"] = "Database Error2. Please Try Again!";
	    die(json_encode($response));
		}
		$response["success"] = 1;
	    $response["message"] = "entry Successfully Added!";
	    echo json_encode($response);
	}
	else
	{
		?>
		<h1>Register</h1>
		<form action = "addnotifications.php" method = "post">
			loggedId :      <input type = "text" name = "loggedId" ><br/>
			requestedId :    <input type = "text" name = "requestedId" ><br/>
			requestedField :  <input type = "text" name = "requestedField" ><br/>
			targetId :  <input type = "text" name = "targetId" ><br/>
			<br/>
			<hr>
			<input type="submit" value = "Add entry"/>
		</form>		
		<?php
	}
?>

