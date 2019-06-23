<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Save Teacher</title>
</head>
<body><br>
<a href="${prefix}/univer/teacher/teacherIndex.jsp">Back</a><br><br>
Please enter teacher details:<br>
<br>
<form action="${pageContext.request.contextPath}/saveTeacher">
	ID: <input type="text" name="id">
	Name: <input type="text" name="firstName">
	Last Name: <input type="text" name="lastName">
	Faculty: 
		<select name="facultyId">
			<c:forEach var="faculty" items="${requestScope.faculties}">
				<option value="${faculty.id}">${faculty.name}</option>
			</c:forEach>
		</select>
	<br><br>
	<input type="submit">
</form>
</body>
</html>
