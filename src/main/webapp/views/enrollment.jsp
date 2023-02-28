<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
        <th>Student ID</th>
        <th>Student Name</th>
        <th>Course ID</th>
        <th>Course Name</th>
        <th>Instructor</th>
        <th>Mark</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="enrollment" items="${enrollments}">
        <tr>
            <td>${enrollment.id}</td>
            <td>${enrollment.student.id}</td>
            <td>${enrollment.student.username}</td>
            <td>${enrollment.course.id}</td>
            <td>${enrollment.course.courseName}</td>
            <td>${enrollment.course.instructor}</td>
            <td>${enrollment.mark}</td>
            <td>
                <a href="${pageContext.request.contextPath}/enrollment?action=showEditForm&enrollment_id=${enrollment.id}">Edit</a>
                <a href="${pageContext.request.contextPath}/enrollment?action=delete&enrollment_id=${enrollment.id}" onclick="return confirm('Are you sure you want to delete this enrollment?')">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br />
<a href="${pageContext.request.contextPath}/enrollment?action=showAddForm">Add Enrollment</a>
</body>
</html>