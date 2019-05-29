<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Teacher</title>
</head>
<body><br>
<a href="${prefix}/univer/teacher/teacherIndex.jsp">Back</a><br><br>
<br>
<form action="${pageContext.request.contextPath}/deleteTeacher">
	Select Teacher: 
		<select name="teacherId">
			<c:forEach var="teacher" items="${requestScope.teachers}">
				<option value="${teacher.id}">${teacher.firstName} ${teacher.lastName}</option>
			</c:forEach>
		</select> <br><br>
	<input type="submit">
</form>
</body>
</html>