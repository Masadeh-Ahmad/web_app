<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="com.example.web_app.model.Enrollment" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Enrollment</title>
</head>
<body>
<h1>Edit Enrollment</h1>
<form action="${pageContext.request.contextPath}/enrollment?action=edit" method="POST">
  <input type="hidden" name="id" value="${enrollment.getId()}">
  <label>Student ID:</label>
  <input type="number" name="user_id" value="${enrollment.getUserId()}">
  <br>
  <label> Course ID:</label>
  <input type="number" name="course_id" value="${enrollment.getCourseId()}">
  <br>
  <label>Mark:</label>
  <input type="number" step="0.01" name="mark" value="${enrollment.getMark()}">
  <br>
  <button type="submit">Update Enrollment</button>
</form>
<br />
<button onclick="window.location.href='${pageContext.request.contextPath}/enrollment'">Back to List</button>
</body>
</html>