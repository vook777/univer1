<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Save Course</title>
</head>
<body><br>
<a href="${prefix}/univer/course/courseIndex.jsp">Back</a><br><br>
Please enter course details:<br>
<br>
<form action="${pageContext.request.contextPath}/saveCourse">
	ID: <input type="text" name="id">
	Name: <input type="text" name="name">
	Number of weeks: <input type="text" name="numberOfWeeks">
	Description: <input type="text" name="description">
	<br><br>
	<input type="submit">
</form>
</body>
</html>
