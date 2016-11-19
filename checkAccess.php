<?php
require("config.inc.php");
if(!empty($_POST))
{
	/////////////////RUNNING LOGGED ONE'S//////////////
	$loggedId = $_POST["loggedId"];
	$requestedId = $_POST["requestedId"];
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
	//print_r($row);
	$loggedLevel = $row["level"];
	//echo "<br>";
	
	
	
	/////////////////////////RUNNING REQUESTED ONE'S//////////////	
	
	$query = "Select * FROM citizens WHERE id = '$requestedId'";
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
		$requestedLevel = $row["level"];
	}
	//print_r($row);
	//echo "<br>";
	
	if($loggedLevel==1)
	{
		if($requestedField=="level")
		{
			$response["success"]="1";
			$response["message"]="You can access the field: $requestedField"; 
			$response["data"]=$row["$requestedField"];
			echo json_encode($response);
		}
		else
		{
			$response["success"]="0";
			$response["message"]="Sorry, You do not have access privilage for the field: $requestedField"; 
			$response["data"]="";
			echo json_encode($response);
		}
	}
	else if($loggedLevel==2)
	{
		if(
			$requestedField=="level"
			||$requestedField=="dob"
			||$requestedField=="mobile"
			||$requestedField=="email"
			||$requestedField=="address"
			||$requestedField=="relatives"
		   )
		{
			$response["success"]="1";
			$response["message"]="You can access the field: $requestedField"; 
			$response["data"]=$row["$requestedField"];
			echo json_encode($response);
		}
		else
		{
			$response["success"]="0";
			$response["message"]="Sorry, You do not have access privilage for the field: $requestedField"; 
			$response["data"]="";
			echo json_encode($response);
		}
	}
	else if($loggedLevel==3)
	{
		if($requestedField=="level"
			||$requestedField=="dob"
			||$requestedField=="mobile"
			||$requestedField=="email"
			||$requestedField=="address"
			||$requestedField=="relatives"
			||$requestedField=="pancard"
			||$requestedField=="aadhaarcard"
			||$requestedField=="voterid"
			||$requestedField=="dl"
			||$requestedField=="education"
			||$requestedField=="criminalrecords"
			||$requestedField=="employment"
			||$requestedField=="propertiesowned"
		   )
		{
			$response["success"]="0";
			$response["message"]="You can access the field: $requestedField"; 
			$response["data"]=$row["$requestedField"];
			echo json_encode($response);
		}
		else
		{
			$response["success"]="1";
			$response["message"]="Sorry, You do not have access privilage for the field: $requestedField"; 
			$response["data"]="";
			echo json_encode($response);
		}
	}
	else if($loggedLevel==4)
	{
		if($requestedField=="level"
			||$requestedField=="dob"
			||$requestedField=="mobile"
			||$requestedField=="email"
			||$requestedField=="address"
			||$requestedField=="relatives"
			||$requestedField=="pancard"
			||$requestedField=="aadhaarcard"
			||$requestedField=="voterid"
			||$requestedField=="dl"
			||$requestedField=="education"
			||$requestedField=="criminalrecords"
			||$requestedField=="employment"
			||$requestedField=="propertiesowned"
			||$requestedField=="passport"
			||$requestedField=="signature"
			||$requestedField=="biometricinfo"
			||$requestedField=="password"
		   )
		{
			$response["success"]="1";
			$response["message"]="You can access the field: $requestedField"; 
			$response["data"]=$row["$requestedField"];
			echo json_encode($response);
		}
		else
		{
			$response["success"]="0";
			$response["message"]="Sorry, You do not have access privilage for the field: $requestedField"; 
			$response["data"]="";
			echo json_encode($response);
		}
	}
	
	
	/*
	if ($row) 
	{
		$response["success"] = 1;
		$response["message"] = "All Ok";
		$response["data"]   = $row[$requestedField];
		echo json_encode($response);
	} 
	else 
	{
		$response["success"] = 0;
		$response["message"] = "No Match Found!";
		$response["data"]   = "";
		die(json_encode($response));
	}
	*/
}
else{
	?>
		<h1>Search</h1>
		<form action = "checkAccess.php" method = "post">
			Logged ID :      <input type = "text" name = "loggedId" ><br/>
			Requested ID :    <input type = "text" name = "requestedId" ><br/>
			Requested Field :    <input type = "text" name = "requestedField" ><br/>
			<br/>
			<hr>
			<input type="submit" value = "search"/>
		</form>
	<?php
}
?>

