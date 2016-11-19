<?php
	require("config.inc.php");	
	if(!empty($_POST))
	{ 
	    
		$query = $_POST["query"];
		$query_params = array();
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
/*		$row = $stmt->fetch();
		if($row){
			$response["success"] = 1;
	        $response["message"] = "Done";
	        echo json_encode($response);
		}
*/		
	}
	else
	{
		?>
		<h1>Run Query</h1>
		<form action = "runqueries.php" method = "post">
			Query :      <input type = "text" name = "query" size="100"><br/>
			<br/>
			<hr>
			<input type="submit" value = "Add entry"/>
		</form>		
		<?php
	}
?>

