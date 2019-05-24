<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Save Group</title>
</head>
<body><br>
<a href="${prefix}/univer/group/groupIndex.jsp">Back</a><br><br>
Please enter group details:<br>
<br>
<form action="${pageContext.request.contextPath}/saveGroup">
	ID: <input type="text" name="id">
	Name: <input type="text" name="name">
	Faculty ID: <input type="text" name="facultyId">
	<br><br>
	<input type="submit">
</form>
</body>
</html>
