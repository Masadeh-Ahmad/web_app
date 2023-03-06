<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Add Enrollment</title>
</head>
<body>
<h1>Add Enrollment</h1>
<form action="${pageContext.request.contextPath}/enrollment?action=add" method="post">
  <label>Student ID:</label>
  <input type="number"  name="userId" required><br><br>

  <label>Course ID:</label>
  <input type="number"  name="courseId" required><br><br>

  <label>Mark:</label>
  <input type="number" step="0.01"  name="mark" required><br><br>

  <input type="submit" value="Add Enrollment">
</form>
<br />
<button onclick="window.location.href='${pageContext.request.contextPath}/enrollment'">Back to List</button>
</body>
</html>