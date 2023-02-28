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
<a href="${pageContext.request.contextPath}/students?action=new">Add Student</a>
<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>Username</th>
    <th>Password</th>
    <th>Actions</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${students}" var="student">
    <tr>
      <td>${student.id}</td>
      <td>${student.username}</td>
      <td>${student.password}</td>
      <td>
        <a href="${pageContext.request.contextPath}/students?action=edit&id=${student.id}">Edit</a>
        <a href="${pageContext.request.contextPath}/students?action=delete&id=${student.id}">Delete</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>