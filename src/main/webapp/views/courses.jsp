<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Courses</title>
</head>
<body>
<h1>Courses</h1>
<table border="1">
  <tr>
    <th>ID</th>
    <th>Course Name</th>
    <th>Instructor</th>
    <th>Description</th>
    <th>Action</th>
  </tr>
  <c:forEach items="${courses}" var="course">
    <tr>
      <td>${course.id}</td>
      <td>${course.courseName}</td>
      <td>${course.instructor}</td>
      <td>${course.description}</td>
      <%if(request.getSession().getAttribute("role").equals("ADMIN")){%>
      <td><a href="edit-course?id=${course.id}">Edit</a> | <a href="delete-course?id=${course.id}">Delete</a></td><%}%>
      <%if(request.getSession().getAttribute("role").equals("STUDENT")){%>
      <td><a href="${pageContext.request.contextPath}/courses?action=enroll&courseId=${course.id}">Enroll</a></td><%}%>


    </tr>
  </c:forEach>
</table>
<br>
<a href="add-course">Add Course</a>
</body>
</html>