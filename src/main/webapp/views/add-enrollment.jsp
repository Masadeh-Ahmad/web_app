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
  <label for="student_id">Student ID:</label>
  <input type="number" id="student_id" name="student_id" required><br><br>

  <label for="course_id">Course ID:</label>
  <input type="number" id="course_id" name="course_id" required><br><br>

  <label for="mark">Mark:</label>
  <input type="number" step="0.01" id="mark" name="mark" required><br><br>

  <input type="submit" value="Add Enrollment">
</form>
<br />
<a href="${pageContext.request.contextPath}/enrollment?action=list">Back to List</a>
</body>
</html>