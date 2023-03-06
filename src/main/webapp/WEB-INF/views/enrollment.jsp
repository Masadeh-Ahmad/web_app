
<%@ page import="java.util.List" %>
<%@ page import="com.example.web_app.model.Enrollment" %>
<%@ page import="com.example.web_app.dto.EnrollmentDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>List Enrollments</title>
</head>
<body>
<h1>List Enrollments</h1>
<table border="1">
    <tr>
        <th>Enrollment ID</th>
        <th>User ID</th>
        <th>Student Name</th>
        <th>Course ID</th>
        <th>Course Name</th>
        <th>Mark</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="enrollment" items="${enrollments}">
        <tr>
            <td>${enrollment.getId()}</td>
            <td>${enrollment. getUserId()}</td>
            <td>${enrollment.getUsername()}</td>
            <td>${enrollment.getCourseId()}</td>
            <td>${enrollment.getCourseName()}</td>
            <td>${enrollment.getMark() }</td>

            <td>
                <%if( session.getAttribute("role") != null && session.getAttribute("role").equals("ADMIN") ){%>
                <button onclick="window.location.href='${pageContext.request.contextPath}/enrollment?action=edit&id=${enrollment.getId()}'">Edit</button>
                <form action="${pageContext.request.contextPath}/enrollment?action=delete&id=${enrollment.getId()}" method="post">
                    <button type="submit" onclick="return confirm('Are you sure you want to delete this enrollment?')">Delete</button>
                </form>
                <%}%>
                <button onclick="window.location.href='${pageContext.request.contextPath}/courses?action=marks&id=${enrollment.getCourseId()}'">Marks</button>
            </td>

        </tr>
    </c:forEach>
</table>
<br />
<%if( session.getAttribute("role") != null && session.getAttribute("role").equals("ADMIN") ){%>
<button onclick="window.location.href='${pageContext.request.contextPath}/enrollment?action=add'">Add Enrollment</button><%}%>
</body>
</html>