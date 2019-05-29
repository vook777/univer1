<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Save Student</title>
</head>
<body><br>
<a href="${prefix}/univer/student/studentIndex.jsp">Back</a><br><br>
Please enter student details:<br>
<br>
<form action="${pageContext.request.contextPath}/saveStudent">
	ID: <input type="text" name="id">
	Name: <input type="text" name="firstName">
	Last Name: <input type="text" name="lastName">
	<br><br>
	Group: 
		<select name="groupId">
			<c:forEach var="group" items="${requestScope.groups}">
				<option value="${group.id}">${group.name}</option>
			</c:forEach>
		</select>
	Card Number: <input type="text" name="studentCardNumber">
	<br><br>
	<input type="submit">
</form>
</body>
</html>
