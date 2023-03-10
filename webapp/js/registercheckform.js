function registercheckform() {
	var firstName = document.forms["myform"]["firstname"].value;
	var lastName = document.getElementById("lastName").value;
	var userName = document.getElementById("userName").value;
	var password = document.getElementById("password").value;
	var rePassword = document.getElementById("rePassword").value;
	
	// check first name
	if(firstName.trim().length == 0) {
		document.forms["myform"]["errFirstName"].innerHTML = "* Please enter First name!";
		return false;
	}
	document.getElementById("errFirstName").innerHTML = "ok";
	
	// check lastname
	if(lastName.trim().length == 0) {
		document.getElementById("errLastName").innerHTML = "* Please enter Last name!";
		return false;
	}
	document.getElementById("errLastName").innerHTML = "";
	
	// check user name
	if(userName.trim().length == 0) {
		document.getElementById("errUserName").innerHTML = "* Please enter user name!";
		return false;
	}
	document.getElementById("errUserName").innerHTML = "";
	
	// check password
	if(password.length == 0) {
		document.getElementById("errPassword").innerHTML = "* Please enter password!";
		return false;
	}
	document.getElementById("errPassword").innerHTML = "";
	
	// check repeat password
	if(password != rePassword) {
		document.getElementById("errRePassword").innerHTML = "* Password incorect";
		return false;
	}
	document.getElementById("errRePassword").innerHTML = "";
	
}