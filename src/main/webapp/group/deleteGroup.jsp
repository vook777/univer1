<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Group</title>
</head>
<body><br>
<a href="${prefix}/univer/group/groupIndex.jsp">Back</a><br><br>
Please enter group ID:<br>
<br>
<form action="${pageContext.request.contextPath}/deleteGroup">
	<input type="text" name="groupId">
	<input type="submit">
</form>
</body>
</html>