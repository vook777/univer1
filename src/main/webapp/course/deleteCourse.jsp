<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Course</title>
</head>
<body><br>
<a href="${prefix}/univer/course/courseIndex.jsp">Back</a><br><br>
<br>
<form action="${pageContext.request.contextPath}/deleteCourse">
	Select Course: 
		<select name="courseId">
			<c:forEach var="course" items="${requestScope.courses}">
				<option value="${course.id}">${course.name}</option>
			</c:forEach>
		</select> <br><br>
	<input type="submit">
</form>
</body>
</html>