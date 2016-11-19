<?php
	require("config.inc.php");	
	if(!empty($_POST))
	{ 
	    
		$query = " SELECT 1 FROM education WHERE id = :id";
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
	        $response["message"] = "educational details for this Id has already been entered.";
	        die(json_encode($response));
		}
		$query = "INSERT INTO education 
		( 
		id, 
		currentlevel,
		middleschoolname,
		middleschoolpercentage,
		tenthschoolname,
		tenthschoolpercentage,
		twelfthschoolname,
		twelfthschoolpercentage,
		graduatingstream,
		graduatingcollege,
		graduatingboard,
		graduatingpercentage,
		postgraduatingstream,
		postgraduatingcollege,
		postgraduatingboard,
		postgraduatingpercentage
		) 
		VALUES 
		( 
		:id,
		:currentlevel,
		:middleschoolname,
		:middleschoolpercentage,
		:tenthschoolname,
		:tenthschoolpercentage,
		:twelfthschoolname,
		:twelfthschoolpercentage,
		:graduatingstream,
		:graduatingcollege,
		:graduatingboard,
		:graduatingpercentage,
		:postgraduatingstream,
		:postgraduatingcollege,
		:postgraduatingboard,
		:postgraduatingpercentage
		
		) 
		";
		 $query_params = array(
	        ':id' => $_POST['id'],
	        ':currentlevel' => $_POST['currentlevel'],
			':middleschoolname' => $_POST['middleschoolname'],
	        ':middleschoolpercentage' => $_POST['middleschoolpercentage'],
			':tenthschoolname' => $_POST['tenthschoolname'],
			':tenthschoolpercentage' => $_POST['tenthschoolpercentage'],
			':twelfthschoolname' => $_POST['twelfthschoolname'],
			':twelfthschoolpercentage' => $_POST['twelfthschoolpercentage'],
			':graduatingstream' => $_POST['graduatingstream'],
			':graduatingcollege' => $_POST['graduatingcollege'],
			':graduatingboard' => $_POST['graduatingboard'],
			':graduatingpercentage' => $_POST['graduatingpercentage'],
			':postgraduatingstream' => $_POST['postgraduatingstream'],
			':postgraduatingcollege' => $_POST['postgraduatingcollege'],
			':postgraduatingboard' => $_POST['postgraduatingboard'],
			':postgraduatingpercentage' => $_POST['postgraduatingpercentage']
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
		<form action = "education.php" method = "post">
			id :      <input type = "text" name = "id" ><br/>
			currentlevel :    <input type = "text" name = "currentlevel" ><br/>
			middleschoolname :    <input type = "text" name = "middleschoolname" ><br/>
			middleschoolpercentage :    <input type = "text" name = "middleschoolpercentage" ><br/>
			tenthschoolname :    <input type = "text" name = "tenthschoolname" ><br/>
			tenthschoolpercentage:    <input type = "text" name = "tenthschoolpercentage" ><br/>
			twelfthschoolname:    <input type = "text" name = "twelfthschoolname" ><br/>
			twelfthschoolpercentage:    <input type = "text" name = "twelfthschoolpercentage" ><br/>
			graduatingstream:    <input type = "text" name = "graduatingstream" ><br/>
			graduatingcollege:    <input type = "text" name = "graduatingcollege" ><br/>
			graduatingboard:    <input type = "text" name = "graduatingboard" ><br/>
			graduatingpercentage:    <input type = "text" name = "graduatingpercentage" ><br/>
			postgraduatingstream:    <input type = "text" name = "postgraduatingstream" ><br/>
			postgraduatingcollege:    <input type = "text" name = "postgraduatingcollege" ><br/>
			postgraduatingboard:    <input type = "text" name = "postgraduatingboard" ><br/>
			postgraduatingpercentage:    <input type = "text" name = "postgraduatingpercentage" ><br/>
			
			<br/>
			<hr>
			<input type="submit" value = "Add entry"/>
		</form>		
		<?php
	}
?>

