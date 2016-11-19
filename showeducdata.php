<?php
	require("config.inc.php");	
	if(!empty($_POST))
	{ 
	    
		$query = " SELECT * FROM education WHERE id = :id";
		$query_params = array(':id' => $_POST['id']);
		try
		{
	     	$stmt = $db->prepare($query);
		    $result = $stmt->execute($query_params);
		}
		catch (PDOException $ex) {
			$response["success"] = 0;
	        $response["message"] = "Database Error!!!. Please Try Again!" ;
	        die(json_encode($response));
		}
		
		$row = $stmt->fetch();
		if($row){
			$response["success"] = 1;
	        $response["message"] = "Found";
	        $response["currentlevel"] = $row["currentlevel"];
			$response["middleschoolname"] = $row["middleschoolname"];
			$response["middleschoolpercentage"] = $row["middleschoolpercentage"];
			$response["tenthschoolname"] = $row["tenthschoolname"];
			$response["tenthschoolpercentage"] = $row["tenthschoolpercentage"];
			$response["twelfthschoolname"] = $row["twelfthschoolname"];
			$response["twelfthschoolpercentage"] = $row["twelfthschoolpercentage"];
			$response["graduatingstream"] = $row["graduatingstream"];
			$response["graduatingcollege"] = $row["graduatingcollege"];
			$response["graduatingboard"] = $row["graduatingboard"];
			$response["graduatingpercentage"] = $row["graduatingpercentage"];
			$response["postgraduatingstream"] = $row["postgraduatingstream"];
			$response["postgraduatingcollege"] = $row["postgraduatingcollege"];
			$response["postgraduatingboard"] = $row["postgraduatingboard"];
			$response["postgraduatingpercentage"] = $row["postgraduatingpercentage"];
			
			echo json_encode($response);
		}
		
	}
	else
	{
		?>
		<h1>Register</h1>
		<form action = "showeducdata.php" method = "post">
			id :      <input type = "text" name = "id" ><br/>	<br/>
			<hr>
			<input type="submit" value = "Add entry"/>
		</form>		
		<?php
	}
?>

