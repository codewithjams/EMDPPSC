<?php
	require("config.inc.php");	
	if(!empty($_POST))
	{ 
	    
		$query = " SELECT 1 FROM relatives WHERE id = :id";
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
	        $response["message"] = "relative details for this Id has already been entered.";
	        die(json_encode($response));
		}
		$query = "INSERT INTO relatives 
		( 
		id, 
		father,
		mother,
		brother,
		sister
		) 
		VALUES 
		( 
		:id,
		:father,
		:mother,
		:brother,
		:sister
		) 
		";
		 $query_params = array(
	        ':id' => $_POST['id'],
	        ':father' => $_POST['father'],
			':mother' => $_POST['mother'],
	        ':brother' => $_POST['brother'],
			':sister' => $_POST['sister']
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
		<form action = "relatives.php" method = "post">
			id :      <input type = "text" name = "id" ><br/>
			father :    <input type = "text" name = "father" ><br/>
			mother :    <input type = "text" name = "mother" ><br/>
			brother :    <input type = "text" name = "brother" ><br/>
			sister :    <input type = "text" name = "sister" ><br/>
			<br/>
			<hr>
			<input type="submit" value = "Add entry"/>
		</form>		
		<?php
	}
?>

