<?php
require("config.inc.php");
if(!empty($_POST))
{
	$requestedField = $_POST["requestedField"];
	$loggedId = $_POST["loggedId"];
	$requestedId = $_POST["requestedId"];
	$query = "Select * FROM notifications WHERE  loggedId = '$loggedId' and requestedId='$requestedId' and status='Allowed'";
	$query_params = array();
	try {
		$stmt   = $db->prepare($query);
		$result = $stmt->execute($query_params);
	}
	catch (PDOException $ex) {
		$response["success"] = 0;
		$response["message"] = "Database Error!";
		$response["data"]   = "";
		die(json_encode($response));
	}
	$row = $stmt->fetch();
	if ($row) {
		$targetId   = $row["targetId"];
	} 
	else {
		$response["success"] = 0;
		$response["message"] = "No Match Found!";
		$response["data"]   = "";
		die(json_encode($response));
	}
	$query = "select $requestedField from citizens where id = '$targetId'";
	$query_params = array();
	try {
		$stmt   = $db->prepare($query);
		$result = $stmt->execute($query_params);
	}
	catch (PDOException $ex) {
		
		$response["success"] = 0;
		$response["message"] = "Database Error!";
		$response["data"]   = "";
		die(json_encode($response));
	}
	$row = $stmt->fetch();
	if ($row) {
		$response["success"] = 1;
		$response["message"] = "Match Found!";
		$response["data"]   = $row["$requestedField"];
		echo json_encode($response);
	} 
	else {
		$response["success"] = 0;
		$response["message"] = "No Match Found!";
		$response["data"]   = "";
		die(json_encode($response));
	}
	
}
else{
	?>
		<h1>Search</h1>
		<form action = "provideaccess.php" method = "post">
			loggedId :      <input type = "text" name = "loggedId" ><br/>
			requestedId :      <input type = "text" name = "requestedId" ><br/>
			requestedField :      <input type = "text" name = "requestedField" ><br/>
			<br/>
			<hr>
			<input type="submit" value = "search"/>
		</form>
	<?php
}
?>

