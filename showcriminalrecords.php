<?php
	require("config.inc.php");	
	if(!empty($_POST))
	{ 
	    
		$query = " SELECT * FROM criminalrecords WHERE id = :id";
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
	        $response["message"] = "Found";
			if(!(empty($_POST['caseid']))){//||(empty($_POST['caseid']))||(empty($_POST['caseid']))){
	        $response["success"] = 1;
			$response["caseid"] = $row["caseid"];
			$response["caseyear"] = $row["caseyear"];
			$response["crime"] = $row["crime"];
			}
			else{
				$response["success"] = 0;
				$response["message"] = "No criminal record found associated with this ID" ;
			}
			echo json_encode($response);
		}
		else{
			$response["success"] = 0;
	        $response["message"] = "No criminal record found associated with this ID" ;
	        echo json_encode($response);
		}
	}
	else
	{
		?>
		<h1>Register</h1>
		<form action = "showcriminalrecords.php" method = "post">
			id :      <input type = "text" name = "id" ><br/>	<br/>
			<hr>
			<input type="submit" value = "Add entry"/>
		</form>		
		<?php
	}
?>

