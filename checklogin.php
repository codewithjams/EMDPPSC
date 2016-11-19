<?php
require("config.inc.php");
$query = "Select * FROM citizens WHERE id = :id and password = :password";
$query_params = array(':id'=>$_POST['id'],':password'=>$_POST['password']);
try {
    $stmt   = $db->prepare($query);
    $result = $stmt->execute($query_params);
}
catch (PDOException $ex) {
    $response["success"] = 0;
    $response["message"] = "Database Error!";
    die(json_encode($response));
}
$rows = $stmt->fetchAll();
if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Post Available!";
    $response["posts"]   = array();
    foreach ($rows as $row) {
        $post = array();
        $post["id"]  = $row["id"];
        $post["password"] = $row["password"];
        array_push($response["posts"], $post);
    }
    echo json_encode($response);
}
else {
	$response["success"] = 0;
	$response["message"] = "No Post Available!";
	die(json_encode($response));
}
?>

