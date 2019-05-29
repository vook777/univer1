<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Auditorium</title>
</head>
<body><br>
<a href="${prefix}/univer/auditorium/auditoriumIndex.jsp">Back</a><br><br>
<br>
<form action="${pageContext.request.contextPath}/deleteAuditorium">
	Select Auditorium: 
		<select name="auditoriumId">
			<c:forEach var="auditorium" items="${requestScope.auditoriums}">
				<option value="${auditorium.id}">${auditorium.name}</option>
			</c:forEach>
		</select> <br><br>
	<input type="submit">
</form>
</body>
</html>