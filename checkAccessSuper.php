<?php
require("config.inc.php");
if(!empty($_POST))
{
	/////////////////RUNNING LOGGED ONE'S//////////////
	$loggedId = $_POST["loggedId"];
	$finalrequestedId = $_POST["requestedId"];
	$requestedField = $_POST["requestedField"];
	$query = "Select * FROM citizens WHERE id = '$loggedId'";
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
		$response["data"] = "";
		die(json_encode($response));
	}
	$row = $stmt->fetch();
	if($row)
	{
		$loggedLevel = $row["level"];
	}
	$loggedLevel = $row["level"];
	
	
}
else{
	?>
		<h1>Search</h1>
		<form action = "checkAccessSuper.php" method = "post">
			Logged ID :      <input type = "text" name = "loggedId" ><br/>
			
			Requested Field :    <input type = "text" name = "requestedField" ><br/>
			Target ID :    <input type = "text" name = "targetId" ><br/>
			Status :    <input type = "text" name = "statsu" ><br/>
			<br/>
			<hr>
			<input type="submit" value = "search"/>
		</form>
	<?php
}
?>

