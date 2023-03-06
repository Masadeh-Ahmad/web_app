<html>
<head>
  <title>Add Course</title>
</head>
<body>
<h1>Add Course</h1>
<form action="${pageContext.request.contextPath}/courses?action=add" method="post">
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
      <td><textarea type="" name="description"></textarea></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="Save"></td>
    </tr>
  </table>
</form>
<br>
<button onclick="window.location.href='${pageContext.request.contextPath}/courses'">Back to List</button>
</body>
</html>