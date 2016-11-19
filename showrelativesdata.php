<?php
	require("config.inc.php");	
	if(!empty($_POST))
	{ 
	    
		$query = " SELECT * FROM relatives WHERE id = :id";
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
	        $response["mother"] = $row["mother"];
			$response["father"] = $row["father"];
			$response["brother"] = $row["brother"];
			$response["sister"] = $row["sister"];
			
			echo json_encode($response);
		}
		
	}
	else
	{
		?>
		<h1>Register</h1>
		<form action = "showrelativesdata.php" method = "post">
			id :      <input type = "text" name = "id" ><br/>	<br/>
			<hr>
			<input type="submit" value = "Add entry"/>
		</form>		
		<?php
	}
?>

