/**
 * 
 */
function validateform() {
	console.log("validate is active.")
	// Lấy dữ liệu từ form
	var userName = document.getElementById("username").value; // username
	var passWord = document.getElementById("password").value;	// password
	
	// Kiểm tra dữ liệu nhập từ form
	if(userName.trim().length == 0 ) {
		document.getElementById("usernameError").innerHTML = "* Please enter username! "
		return false;
	} else {
		document.getElementById("usernameError").innerHTML = ""
	}
	if(passWord == "") {
		document.getElementById("passwordError").innerHTML = "* Please enter password!"
		return false;
	}
	document.getElementById("passwordError").innerHTML = ""
}
