<?php
	require("config.inc.php");	
	if(!empty($_POST))
	{ 
	    
		$query = " SELECT 1 FROM criminalrecords WHERE id = :id";
		$query_params = array(':id' => $_POST['id']);
		try
		{
	     	$stmt = $db->prepare($query);
		    $result = $stmt->execute($query_params);
		}
		catch (PDOException $ex) {
			$response["success"] = 0;
	        $response["message"] = "Database Error!!!. Please Try Again!";
	        die(json_encode($response));
		}
		$row = $stmt->fetch();
		if($row){
			$response["success"] = 0;
	        $response["message"] = "criminal details for this Id has already been entered.";
	        die(json_encode($response));
		}
		$query = "INSERT INTO criminalrecords 
		( 
		id, 
		caseid,
		caseyear,
		crime
		) 
		VALUES 
		( 
		:id,
		:caseid,
		:caseyear,
		:crime
		) 
		";
		 $query_params = array(
	        ':id' => $_POST['id'],
	        ':caseid' => $_POST['caseid'],
			':caseyear' => $_POST['caseyear'],
	        ':crime' => $_POST['crime']
	    );	 
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
		<form action = "criminalrecords.php" method = "post">
			id :      <input type = "text" name = "id" ><br/>
			caseid :    <input type = "text" name = "caseid" ><br/>
			caseyear :    <input type = "text" name = "caseyear" ><br/>
			crime :    <input type = "text" name = "crime" ><br/>
			
			<br/>
			<hr>
			<input type="submit" value = "Add entry"/>
		</form>		
		<?php
	}
?>

