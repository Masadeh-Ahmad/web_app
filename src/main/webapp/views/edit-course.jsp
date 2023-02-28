<html>
<head>
  <title>Edit Course</title>
</head>
<body>
<h1>Edit Course</h1>
<form action="update-course" method="post">
  <table>
    <tr>
      <td>ID:</td>
      <td><input type="text" name="id" value="${course.id}" readonly></td>
    </tr>
    <tr>
      <td>Course Name:</td>
      <td><input type="text" name="courseName" value="${course.courseName}"></td>
    </tr>
    <tr>
      <td>Instructor:</td>
      <td><input type="text" name="instructor" value="${course.instructor}"></td>
    </tr>
    <tr>
      <td>Description:</td>
      <td><input type="text" name="description" value="${course.description}"></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="Update"></td>
    </tr>
  </table>
</form>
<br>
<a href="courses">Back to Courses</a>
</body>
</html>