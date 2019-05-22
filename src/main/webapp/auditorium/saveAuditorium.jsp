<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Save Auditorium</title>
</head>
<body><br>
<a href="${prefix}/univer/auditorium/auditoriumIndex.jsp">Back</a><br><br>
Please enter auditorium details:<br>
<br>
<form action="${pageContext.request.contextPath}/saveAuditorium">
	ID: <input type="text" name="id">
	Name: <input type="text" name="name">
	Capacity: <input type="text" name="capacity">
	<br><br>
	<input type="submit">
</form>
</body>
</html>
