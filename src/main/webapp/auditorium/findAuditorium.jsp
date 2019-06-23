<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Find Auditorium</title>
</head>
<body><br>
<a href="${prefix}/univer/auditorium/auditoriumIndex.jsp">Back</a><br><br>
Please enter auditorium ID:<br>
<br>
<form action="${pageContext.request.contextPath}/findAuditorium">
	<input type="text" name="auditoriumId">
	<input type="submit">
</form>
</body>
</html>
