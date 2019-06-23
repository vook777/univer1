<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Save Faculty</title>
</head>
<body><br>
<a href="${prefix}/univer/faculty/facultyIndex.jsp">Back</a><br><br>
Please enter faculty details:<br>
<br>
<form action="${pageContext.request.contextPath}/saveFaculty">
	ID: <input type="text" name="id">
	Name: <input type="text" name="name">
	<br><br>
	<input type="submit">
</form>
</body>
</html>
