<?php
	require("config.inc.php");	
	if(!empty($_POST))
	{ 
	    if(empty($_POST['id']) || 
		   empty($_POST['name']) ||
		   empty($_POST['gender']) ||
		   empty($_POST['level']) ||
		   empty($_POST['dob']) ||
		   empty($_POST['mobile']) ||
		   empty($_POST['email']) ||
		   empty($_POST['address']) ||
		   empty($_POST['pancard']) ||
		   empty($_POST['aadhaarcard']) ||
		   empty($_POST['voterid']) ||
		   empty($_POST['dl']) ||
		   empty($_POST['education']) ||
		   empty($_POST['criminalrecords']) ||
		   empty($_POST['employment']) ||
		   empty($_POST['relatives']) ||
		   empty($_POST['propertiesowned']) ||
		   empty($_POST['passport']) ||
		   empty($_POST['signature']) ||
		   empty($_POST['biometricinfo']) ||
		   empty($_POST['password'])
			
		  ) 
		{
			$response["success"] = 0;
			$response["message"] = "Please dont leave any field(s) blank....if there is any filed that doesn't have a value, put 0 there.";
			die(json_encode($response));
		}
		$query = " SELECT 1 FROM citizens WHERE id = :id";
		$query_params = array(':id' => $_POST['id']);
		try{
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
	        $response["message"] = "Error : ID already exists!";
	        die(json_encode($response));
		}
		$query = "INSERT INTO citizens 
		( 
		id, 
		name,
		gender,
		level,
		dob,
		mobile,
		email,
		address,
		pancard,
		aadhaarcard,
		voterid,
		dl,
		education,
		criminalrecords,
		employment,
		relatives,
		propertiesowned,
		passport,
		signature,
		biometricinfo,
		password
		) 
		VALUES 
		( 
		:id, 
		:name,
		:gender,
		:level,
		:dob,
		:mobile,
		:email,
		:address,
		:pancard,
		:aadhaarcard,
		:voterid,
		:dl,
		:education,
		:criminalrecords,
		:employment,
		:relatives,
		:propertiesowned,
		:passport,
		:signature,
		:biometricinfo,
		:password
		) 
		";
		 $query_params = array(
	        ':id' => $_POST['id'],
	        ':name' => $_POST['name'],
	        ':gender' => $_POST['gender'],
	        ':level' => $_POST['level'],
	        ':dob' => $_POST['dob'],
	        ':mobile' => $_POST['mobile'],
	        ':email' => $_POST['email'],
	        ':address' => $_POST['address'],
	        ':pancard' => $_POST['pancard'],
	        ':aadhaarcard' => $_POST['aadhaarcard'],
	        ':voterid' => $_POST['voterid'],
	        ':dl' => $_POST['dl'],
	        ':education' => $_POST['education'],
	        ':criminalrecords' => $_POST['criminalrecords'],
	        ':employment' => $_POST['employment'],
	        ':relatives' => $_POST['relatives'],
	        ':propertiesowned' => $_POST['propertiesowned'],
	        ':passport' => $_POST['passport'],
	        ':signature' => $_POST['signature'],
	        ':biometricinfo' => $_POST['biometricinfo'],
	        ':password' => $_POST['password']
	        
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
		<form action = "addentry.php" method = "post">
			id :      <input type = "text" name = "id" ><br/>
			name :    <input type = "text" name = "name" ><br/>
			gender :  <input type = "text" name = "gender" ><br/>
			level :  <input type = "text" name = "level" ><br/>
			dob :  <input type = "text" name = "dob" ><br/>
			mobile :  <input type = "text" name = "mobile" ><br/>
			email :  <input type = "text" name = "email" ><br/>
			address :  <input type = "text" name = "address" ><br/>
			pancard :  <input type = "text" name = "pancard" ><br/>
			aadhaarcard :  <input type = "text" name = "aadhaarcard" ><br/>
			voterid :  <input type = "text" name = "voterid" ><br/>
			dl :  <input type = "text" name = "dl" ><br/>
			education :  <input type = "text" name = "education" ><br/>
			criminalrecords :  <input type = "text" name = "criminalrecords" ><br/>
			employment :  <input type = "text" name = "employment" ><br/>
			relatives :  <input type = "text" name = "relatives" ><br/>
			propertiesowned :  <input type = "text" name = "propertiesowned" ><br/>
			passport :  <input type = "text" name = "passport" ><br/>
			signature :  <input type = "text" name = "signature" ><br/>
			biometricinfo :  <input type = "text" name = "biometricinfo" ><br/>
			password :  <input type = "text" name = "password" ><br/>
			<br/>
			<hr>
			
			
			
			<input type="submit" value = "Add entry"/>
		</form>		
		<?php
	}
?>

