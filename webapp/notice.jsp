<%@page import="options.LabelDAO"%>
<%@page import="backend.Database"%>
<%@page import="backend.News" %>
<%@page import="backend.User" %>
<%@page import="java.sql.Date"%>
<%@page import="java.util.ArrayList"%>
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
<title>Notice page</title>
</head>
<body>

<div class="mainbody">
<h3>Notices</h3>
<table border="1px solid black" class = "tbnotice">
<tr>
<th>ID</th>
<th>Content</th>
<th>Authour</th>
<th>Date</th>
<th>Label</th>
</tr>

<%// get list notices
String userid = (String)session.getAttribute("userid");

if(userid == null) {
	response.sendRedirect("error.html");
	return;
}
User user = Database.getUser(userid);
if(user == null) {
	response.sendRedirect("error.html");
	return;
}
int lvel = user.getLabel();
ArrayList<News> lv = Database.getListNew(lvel);
%>

<% if(lv != null) {%>

<% for(int i = 0; i < lv.size(); i++) {%>
<tr>
<td><%out.println(""+lv.get(i).getId()); %></td>
<td><%out.println(lv.get(i).getText()); %></td>
<td><%out.println(lv.get(i).getUserid()); %></td>
<td><%out.println("" +lv.get(i).getDate()); %></td>
<td><%out.println(LabelDAO.parStringLabel(lv.get(i).getLabel())); %></td>
</tr>

<%} %>
<%} %>
</table>
<p>Back to <a href = "/Security_Project/success.jsp">Home</a>
</p>
<%@include file="templates/footer.html" %>
</div>
</body>
</html>