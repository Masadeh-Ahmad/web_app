<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Enrollment</title>
</head>
<body>
<h1>Edit Enrollment</h1>
<form action="${pageContext.request.contextPath}/enrollment/edit" method="POST">
  <input type="hidden" name="enrollment_id" value="${enrollment.enrollmentId}">
  <label for="student_id">Student ID:</label>
  <input type="number" name="student_id" value="${enrollment.studentId}">
  <br>
  <label for="course_id">Course ID:</label>
  <input type="number" name="course_id" value="${enrollment.courseId}">
  <br>
  <label for="mark">Mark:</label>
  <input type="number" step="0.01" name="mark" value="${enrollment.mark}">
  <br>
  <button type="submit">Update Enrollment</button>
</form>
</body>
</html>