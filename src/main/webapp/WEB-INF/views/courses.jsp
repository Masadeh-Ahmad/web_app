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
      <td>${course.getId()}</td>
      <td>${course.getCourseName()}</td>
      <td>${course.getInstructor()}</td>
      <td>${course.getDescription()}</td>
      <%if(session.getAttribute("role").equals("ADMIN")){%>
      <td> <button onclick="window.location.href='${pageContext.request.contextPath}/courses?action=edit&id=${course.getId()}'">Edit</button>
        <form action="${pageContext.request.contextPath}/courses?action=delete&id=${course.getId()}" method="post">
          <button type="submit" onclick="return confirm('Are you sure you want to delete this course?')">Delete</button>
        </form></td><%}%>
      <%if(session.getAttribute("role").equals("STUDENT")){%>
      <td><button onclick="window.location.href='${pageContext.request.contextPath}/courses?action=enroll&courseId=${course.getId()}'">Enroll</button></td><%}%>
    </tr>
  </c:forEach>
</table>
<br>
<%if(session.getAttribute("role").equals("ADMIN")){%>
<button onclick="window.location.href='${pageContext.request.contextPath}/courses?action=add'">Add Course</button><%}%>
</body>
</html>