<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Student</title>
</head>
<body><br>
<a href="${prefix}/univer/student/studentIndex.jsp">Back</a><br><br>
<br>
<form action="${pageContext.request.contextPath}/deleteStudent">
		Select Student: 
		<select name="studentId">
			<c:forEach var="student" items="${requestScope.students}">
				<option value="${student.id}">${student.firstName} ${student.lastName} || ${student.id}</option>
			</c:forEach>
		</select> <br><br>
	<input type="submit">
</form>
</body>
</html>