<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Faculty</title>
</head>
<body><br>
<a href="${prefix}/univer/faculty/facultyIndex.jsp">Back</a><br><br>
Please enter faculty ID:<br>
<br>
<form action="${pageContext.request.contextPath}/deleteFaculty">
	<input type="text" name="facultyId">
	<input type="submit">
</form>
</body>
</html>