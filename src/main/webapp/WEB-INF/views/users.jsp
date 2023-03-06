<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Student List</title>
</head>
<body>
<h1>Student List</h1>
<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>Username</th>
    <%if(session.getAttribute("role").equals("ADMIN")){%>
    <th>Role</th><%}%>
    <th>Actions</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${users}" var="user">
    <tr>
      <td>${user.getId()}</td>
      <td>${user.getUsername()}</td>
      <%if(session.getAttribute("role").equals("ADMIN")){%>
      <td> ${user.getRole()}</td><%}%>
      <td>
      <button onclick="window.location.href='${pageContext.request.contextPath}/users?action=edit&id=${user.getId()}'">Edit</button>
      <form action="${pageContext.request.contextPath}/users?action=delete&id=${user.getId()}" method="post">
          <button type="submit" onclick="return confirm('Are you sure you want to delete this user?')">Delete</button>
      </form>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<br>
<%if(session.getAttribute("role").equals("ADMIN")){%>
<button onclick="window.location.href='${pageContext.request.contextPath}/users?action=add'">Add Student</button><%}%>
</body>
</html>