<html>
<head>
  <title>Edit Course</title>
</head>
<body>
<h1>Edit Course</h1>
<form action="${pageContext.request.contextPath}/courses?action=edit" method="post">
  <table>
    <tr>
      <td>ID:</td>
      <td><input type="hidden" name="id" value="${course.getId()}"></td>
    </tr>
    <tr>
      <td>Course Name:</td>
      <td><input type="text" name="courseName" value="${course.getCourseName()}"></td>
    </tr>
    <tr>
      <td>Instructor:</td>
      <td><input type="text" name="instructor" value="${course.getInstructor()}"></td>
    </tr>
    <tr>
      <td>Description:</td>
      <td><textarea type="text" name="description" >${course.getDescription()}</textarea></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="Update"></td>
    </tr>
  </table>
</form>

<br>
<button onclick="window.location.href='${pageContext.request.contextPath}/courses'">Back to List</button>
</body>
</html>