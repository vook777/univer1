<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Teacher</title>
</head>
<body><br>
<a href="${prefix}/univer/teacher/teacherIndex.jsp">Back</a><br><br>
Please enter teacher ID:<br>
<br>
<form action="${pageContext.request.contextPath}/deleteTeacher">
	<input type="text" name="teacherId">
	<input type="submit">
</form>
</body>
</html>