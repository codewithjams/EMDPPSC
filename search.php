<?php
require("config.inc.php");
if(!empty($_POST))
{
	$table_name = "citizens";
	$keyword = $_POST['keyword'];
	$type = $_POST['type'];
	if($type=="city")
		$type="address";
	if($type=="company")
		$type="employment";
	$query = "Select * FROM $table_name WHERE $type LIKE '%$keyword%'";
	//$query_params = array(':keyword'=>$_POST['keyword']);
	$query_params = array();

	try {
		$stmt   = $db->prepare($query);
		$result = $stmt->execute($query_params);
	}
	catch (PDOException $ex) {
		$response["success"] = 0;
		$response["message"] = "Database Error!";
		$response["posts"]   = array();
		die(json_encode($response));
	}
	$rows = $stmt->fetchAll();
	if ($rows) {
		$response["success"] = 1;
		$response["message"] = "Match Found!";
		$response["posts"]   = array();
		foreach ($rows as $row) {
			$post = array();
			$post["id"]  = $row["id"];
			$post["name"] = $row["name"];
			$post["address"] = $row["address"];
			$post["gender"] = $row["gender"];
			
			array_push($response["posts"], $post);
		}
		
		echo json_encode($response);
	} 
	else {
		$response["success"] = 0;
		$response["message"] = "No Match Found!";
		$response["posts"]   = array();
		die(json_encode($response));
	}
}
else{
	?>
		<h1>Search</h1>
		<form action = "search.php" method = "post">
			type :      <input type = "text" name = "type" ><br/>
			keyword :    <input type = "text" name = "keyword" ><br/>
			<br/>
			<hr>
			<input type="submit" value = "search"/>
		</form>
	<?php
}
?>

