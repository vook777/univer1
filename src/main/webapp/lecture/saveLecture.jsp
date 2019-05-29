<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Save Lecture</title>
</head>
<body><br>
<a href="${prefix}/univer/lecture/lectureIndex.jsp">Back</a><br><br>
Please enter lecture details:<br>
<br>
<form action="${pageContext.request.contextPath}/saveLecture">
	ID: <input type="text" name="id">	
	Auditorium: 
		<select name="auditoriumId">
			<c:forEach var="auditorium" items="${requestScope.auditoriums}">
				<option value="${auditorium.id}">${auditorium.name}</option>
			</c:forEach>
		</select>	
	Course: 
		<select name="courseId">
			<c:forEach var="course" items="${requestScope.courses}">
				<option value="${course.id}">${course.name}</option>
			</c:forEach>
		</select> 
	Group: 
		<select name="groupId">
			<c:forEach var="group" items="${requestScope.groups}">
				<option value="${group.id}">${group.name}</option>
			</c:forEach>
		</select> <br><br>
	Teacher: 
		<select name="teacherId">
			<c:forEach var="teacher" items="${requestScope.teachers}">
				<option value="${teacher.id}">${teacher.firstName} ${teacher.lastName}</option>
			</c:forEach>
		</select>
	Time: 
		<select name="time">
			<option value="09:00">09:00</option>
			<option value="10:00">10:00</option>
			<option value="11:00">11:00</option>
			<option value="12:00">12:00</option>
			<option value="13:00">13:00</option>
			<option value="14:00">14:00</option>
			<option value="15:00">15:00</option>
			<option value="16:00">16:00</option>
			<option value="17:00">17:00</option>
		</select>
	<br><br>
	<input type="submit">
</form>
</body>
</html>
