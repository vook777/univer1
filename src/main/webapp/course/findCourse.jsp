<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Find Course</title>
</head>
<body><br>
<a href="${prefix}/univer/course/courseIndex.jsp">Back</a><br><br>
Please enter course ID:<br>
<br>
<form action="${pageContext.request.contextPath}/findCourse">
	<input type="text" name="courseId">
	<input type="submit">
</form>
</body>
</html>
