<?php
require("config.inc.php");
if(!empty($_POST))
{
	$loggedId = $_POST["loggedId"];
	$query = "Select * FROM citizens WHERE id = '$loggedId'";
	//$query_params = array(':keyword'=>$_POST['keyword']);
	$query_params = array();

	try {
		$stmt   = $db->prepare($query);
		$result = $stmt->execute($query_params);
	}
	catch (PDOException $ex) {
		$response["success"] = 0;
		$response["message"] = "Database Error!";
		die(json_encode($response));
	}
	$row=$stmt->fetch();
	if($row)
	{
		$loggedLevel=$row["level"];
		$otherField="employment";
		if(!($row["employment"]!="NONE" && $row["employment"]!="n/a" && $row["employment"]!="null"))
		{
			$otherField = "education";
		}
		
		$otherFieldValue = $row["$otherField"];
	}
	
	//echo $loggedLevel;		//THIS PART WORKING PERFECTLY
	
	////////////////////////////////////
	$query = "Select * from citizens where level>'$loggedLevel' and $otherField = '$otherFieldValue'";	//CHECK THIS
	$query_params = array();
	try {
		$stmt   = $db->prepare($query);
		$result = $stmt->execute($query_params);
	}
	////////       HERE
	catch (PDOException $ex) {
		$response["success"] = 0;
		$response["message"] = "Database Error!";
		$response["posts"]   = array();
		die(json_encode($response));
	}
	$rows = $stmt->fetchAll();
	if ($rows) {
		$response["success"] = 1;
		$response["message"] = "Match Found!";
		$response["posts"]   = array();
		foreach ($rows as $row) {
			$post = array();
			$post["id"]  = $row["id"];
			$post["name"] = $row["name"];
			$post["level"] = $row["level"];
			$post["others"] = $row["$otherField"];
			
			array_push($response["posts"], $post);
		}
		
		echo json_encode($response);
	} 
	else {
		$response["success"] = 0;
		$response["message"] = "No Match Found!";
		$response["posts"]   = array();
		die(json_encode($response));
	}
	
}
else{
	?>
		<h1>Search</h1>
		<form action = "presentHigherLevelPeople.php" method = "post">
			id :      <input type = "text" name = "loggedId" ><br/>
			
			<br/>
			<hr>
			<input type="submit" value = "search"/>
		</form>
	<?php
}
?>

