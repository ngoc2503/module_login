<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="backend.UserDAO" %>
<%@page import="backend.AuthSession" %>
<%@page import="backend.HtmlEscape" %>
<%--  
<%

response.setHeader("Cache-Control", "no-store");
if(!AuthSession.validate(request, response))
{
    return;
}

String userid = (String)session.getAttribute("userid");

if(userid == null)
{
    response.sendRedirect("/error.html");
    return;
}

//Prevent CSRF by requring OTP validation each time page is displayed. 
String anticsrf = (String)session.getAttribute("anticsrf_success");
if(anticsrf == null)
{//token not present redirect back to OTP page for validation again
    session.removeAttribute("userid");
    session.setAttribute("userid2fa", userid);
    userid = null; 
    RequestDispatcher rd = request.getRequestDispatcher("otp.jsp");
    rd.forward(request, response);
    return; 
}
else
{//token present
    //remvoe the token so that subsequent request will require OTP validation
    session.removeAttribute("anticsrf_success");
}

String username = UserDAO.getUserName(userid, request.getRemoteAddr());
username = HtmlEscape.escapeHTML(username);

%> 
     --%>
 <%
 String userid = (String) session.getAttribute("userid");

 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="styles/main.css">
<title>Success Page</title>
</head>
<body>

<div class="mainbody">

<p>
Welcome 
 <% 
if(userid != null)
{ 
    out.print(userid); 
}
else
{
    out.print("Unknown"); 
}

%> 
<br>
</p>
<p>
<a href="/Security_Project/notice.jsp">View notices</a>
</p>

<p>
<a href="/Security_Project/post.jsp">Write notices</a>
</p>

<p>
<a href="/Security_Project/logout.jsp">Logout</a>
</p>


<%@include file="templates/footer.html" %>


</div>


</body>
</html>
