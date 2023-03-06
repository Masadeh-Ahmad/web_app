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
<form action="${pageContext.request.contextPath}/users?action=edit&id=${user.getId()}" method="post">
  <div>
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" value="${student.getUsername}" required>
  </div>
  <%if(session.getAttribute("role").equals("STUDENT")){%>
  <div>
    <label >Old Password:</label>
    <input type="password"  name="oldPassword"  required>
  </div><%}%>
  <div>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password"  required>
  </div>
  <%if(session.getAttribute("role").equals("ADMIN")){%>
  <div>
    <label>Role:
    <select name="role">
      <option value="STUDENT">Student</option>
      <option value="ADMIN">Admin</option>
    </select></label>
  </div><%}%>
  <div>
    <button type="submit">Submit</button>
    <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/users'">Cancel</button>
  </div>
</form>
</body>
</html>