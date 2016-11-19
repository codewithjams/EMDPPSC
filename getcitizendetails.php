<?php

/*
Our "config.inc.php" file connects to database every time we include or require
it within a php script.  Since we want this script to add a new user to our db,
we will be talking with our database, and therefore,
let's require the connection to happen:
*/
require("config.inc.php");

//initial query
$query = "Select * FROM citizens where id = 'Manhattan'";
$query_params = array();
//$query_params = array(':id'=>$_POST['id']);
//execute query
try {
    $stmt   = $db->prepare($query);
    $result = $stmt->execute($query_params);
}
catch (PDOException $ex) {
    $response["success"] = 0;
    $response["message"] = "Database Error!";
    die(json_encode($response));
}

// Finally, we can retrieve all of the found rows into an array using fetchAll 
$row = $stmt->fetch();


if ($row) {
    $response["success"] = 1;
    $response["message"] = "Record Exists";
    $response["id"] = $row["id"];
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
	
	$query = "Select * from education"
	
	
	echo json_encode($response);
	
	
	}
 
} else {
    $response["success"] = 0;
    $response["message"] = "No Post Available!";
    die(json_encode($response));
}

?>

