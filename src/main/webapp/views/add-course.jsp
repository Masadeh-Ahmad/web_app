<html>
<head>
  <title>Add Course</title>
</head>
<body>
<h1>Add Course</h1>
<form action="save-course" method="post">
  <table>
    <tr>
      <td>Course Name:</td>
      <td><input type="text" name="courseName"></td>
    </tr>
    <tr>
      <td>Instructor:</td>
      <td><input type="text" name="instructor"></td>
    </tr>
    <tr>
      <td>Description:</td>
      <td><input type="text" name="description"></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="Save"></td>
    </tr>
  </table>
</form>
<br>
<a href="courses">Back to Courses</a>
</body>
</html>