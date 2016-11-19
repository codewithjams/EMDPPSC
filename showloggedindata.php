<?php
	require("config.inc.php");	
	if(!empty($_POST))
	{ 
	    
		$query = " SELECT * FROM citizens WHERE id = :id";
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
	        $response["name"] = $row["name"];
			$response["gender"] = $row["gender"];
			$response["level"] = $row["level"];
			$response["dob"] = $row["dob"];
			$response["mobile"] = $row["mobile"];
			$response["email"] = $row["email"];
			$response["address"] = $row["address"];
			$response["pancard"] = $row["pancard"];
			$response["aadhaarcard"] = $row["aadhaarcard"];
			$response["voterid"] = $row["voterid"];
			$response["dl"] = $row["dl"];
			$response["education"] = $row["education"];
			$response["criminalrecords"] = $row["criminalrecords"];
			$response["employment"] = $row["employment"];
			$response["relatives"] = $row["relatives"];
			$response["propertiesowned"] = $row["propertiesowned"];
			$response["passport"] = $row["passport"];
			$response["signature"] = $row["signature"];
			$response["biometricinfo"] = $row["biometricinfo"];
			$response["password"] = $row["password"];
			
			echo json_encode($response);
		}
		
	}
	else
	{
		?>
		<h1>Register</h1>
		<form action = "showloggedindata.php" method = "post">
			id :      <input type = "text" name = "id" ><br/>	<br/>
			<hr>
			<input type="submit" value = "Add entry"/>
		</form>		
		<?php
	}
?>

