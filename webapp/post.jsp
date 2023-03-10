<%@page import="options.LabelDAO"%>
<%@page import="backend.Database"%>
<%@page import="backend.User"%>
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
<title>Post Page</title>
</head>
<body>
<div class="webformdiv">

<div class="formheader">
<h3>Post Message</h3>
</div>


<form method="POST" autocomplete="off" accept-charset="utf-8" 
action="/Security_Project/PostControllerServlet">
<ul class="form-ul" >
<li>
<label>Message</label>
<textarea class = "pgpost" rows="10" cols="35" required name = "mes"></textarea>

</li>
<%String userid =(String) session.getAttribute("userid");
if(userid == null) {
	response.sendRedirect("error.html");
	return;
}
User user = Database.getUser(userid);
if(user == null) {
	response.sendRedirect("error.html");
	return;
}
String sttlevel = LabelDAO.parStringLabel(user.getLabel());
%>
<li>

<label class = "pgpost">Your level: <%=sttlevel %></label>
<label>Message level</label>
<select name = "stt" class = "lbLevel">
<option value="Top Secret">Top Secret</option>
<option value="Secret">Secret</option>
<option value="Confidential">Confidential</option>
<option value="Unclassified" selected>Unclassified</option>

</select>
</li>
<li id = "err">
<%String postErr = (String) request.getAttribute("postErr");
if(postErr != null) {
	out.println(postErr);
}
%>
</li>
<li>
<input type="submit" value="Submit">

</li>
<li>
<p> Back to
<a href = "${pageContext.request.contextPath}/success.jsp">Home</a> </p>
</li>
</ul>
</form>
</div>
<%@include file="templates/footer.html" %>
</body>
</html>