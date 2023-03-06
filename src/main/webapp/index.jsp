<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1>index
</h1>
<% if( session.getAttribute("role") != null)  {%> Hello <% session.getAttribute("username"); %>             <a href=${pageContext.request.contextPath}/logout>Logout</a>
<br/>
<br/>
<br/>

<div style="align-content: center"><a href=${pageContext.request.contextPath}/users>Users</a>
    <a href=${pageContext.request.contextPath}/courses>Courses</a>
    <a href=${pageContext.request.contextPath}/enrollment>Enrollments</a></div>

<%} else {%>
<a href=${pageContext.request.contextPath}/login>Login</a>
<%}%>
</body>
</html>