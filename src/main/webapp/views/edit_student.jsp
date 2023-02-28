<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Student</title>
</head>
<body>
<h1>Edit Student</h1>
<form action="${pageContext.request.contextPath}/students?action=edit&id=${student.id}" method="post">
  <div>
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" value="${student.username}" required>
  </div>
  <div>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" value="${student.password}" required>
  </div>
  <div>
    <button type="submit">Submit</button>
    <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/students?action=list'">Cancel</button>
  </div>
</form>
</body>
</html>