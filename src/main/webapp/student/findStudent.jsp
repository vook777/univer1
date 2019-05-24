<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Find Student</title>
</head>
<body><br>
<a href="${prefix}/univer/student/studentIndex.jsp">Back</a><br><br>
Please enter student ID:<br>
<br>
<form action="${pageContext.request.contextPath}/findStudent">
	<input type="text" name="studentId">
	<input type="submit">
</form>
</body>
</html>
