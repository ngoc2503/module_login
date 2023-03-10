<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
response.setHeader("Cache-Control", "no-store"); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Label Page</title>
<link rel="stylesheet" type="text/css" href="styles/main.css">
</head>
<body>


<div class="webformdiv">

<div class="formheader">
<h3>Assign security level</h3>
</div>


<form method="POST" autocomplete="off" accept-charset="utf-8" 
action="/Security_Project/LabelController">
<ul class="form-ul" >
<li>
<label>Username</label>
<input type="text" autofocus required name="userid">
</li>
<li>

<label>Level</label>
<select name = "stt" class = "lbLevel">
<option value="Top Secret">Top Secret</option>
<option value="Secret">Secret</option>
<option value="Confidential">Confidential</option>
<option value="Unclassified" selected>Unclassified</option>

</select>
</li>

<li id = "err">
<% String laberr = (String) request.getAttribute("laberr");
if(laberr != null) {
	out.println(laberr);
}
%>
</li>

<li>
<input type="submit" value="Apply" name = "login">

</li>
</ul>
</form>
</div>
<%@include file="templates/footer.html" %>
</body>
</html>