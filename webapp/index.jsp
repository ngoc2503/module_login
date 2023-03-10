<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
response.setHeader("Cache-Control", "no-store"); 
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="styles/main.css">
<title>Login Page</title>
</head>
<body>



<div class="webformdiv">

<div class="formheader">
<h3>Application Sign In</h3>
</div>


<form method="POST" autocomplete="off" accept-charset="utf-8" 
action="/Security_Project/login">
<ul class="form-ul" >
<li>
<label>Username</label>
<input type="text" autofocus required name="userid">
</li>
<li>
<label>Password</label>
<input type="password" required name="password">
</li>
<li>
<input type="submit" value="Login" name = "login">

</li>
<li>
<% String errorLogin = (String) request.getAttribute("errLogin");%>
<p id = "err">
<% if(errorLogin != null) {
	out.println(errorLogin);
} %>
</p>

</li>
<li>
<p>If you have not account yet. Please click 
<a style = "color:blue; font-weight:bold;" href = "/Security_Project/register.jsp">HERE</a>
 to 
<a style = "color:blue; font-weight:bold;" href = "/Security_Project/register.jsp">Register!</a></p>
</li>
</ul>
</form>
</div>
<%@include file="templates/footer.html" %>
</body>
</html>