<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<% boolean isLogged = request.getSession().getAttribute("role") != null; %>
<h1>index
</h1>
<% if(isLogged)  {%> Hello ${pageContext.request.getSession(false).getAttribute("username")}
<br/>
<a href=${pageContext.request.contextPath}/logout>Logout</a>
<%} else {%>
<a href=${pageContext.request.contextPath}/login>Login</a>
<%}%>
</body>
</html>