<%--
  Created by IntelliJ IDEA.
  User: Tomcat
  Date: 3/6/2023
  Time: 6:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${course.getCourseName()} Marks</title>
</head>
<body>
<h2>${course.getCourseName()} Marks</h2>
<br>
<table>
  <tr>
    <td>Average:</td>
    <td><input type="text" name="id" value="${marks.getAvg()}" readonly></td>
  </tr>
  <tr>
    <td>Median:</td>
    <td><input type="text" name="courseName" value="${marks.getMedian()}" readonly></td>
  </tr>
  <tr>
    <td>Max:</td>
    <td><input type="text" name="instructor" value="${marks.getMax()}" readonly></td>
  </tr>
  <tr>
    <td>Min:</td>
    <td><input type="text" name="instructor" value="${marks.getMin()}" readonly></td>
  </tr>

</table>
<br />
<button onclick="window.location.href='${pageContext.request.contextPath}/enrollment'">Back to List</button>
</body>
</html>
