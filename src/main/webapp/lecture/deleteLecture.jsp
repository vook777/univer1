<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Lecture</title>
</head>
<body><br>
<a href="${prefix}/univer/lecture/lectureIndex.jsp">Back</a><br><br>
<br>
<form action="${pageContext.request.contextPath}/deleteLecture">
	Select Lecture: 
		<select name="lectureId">
			<c:forEach var="lecture" items="${requestScope.lectures}">
				<option value="${lecture.id}">${lecture.course.name} ${lecture.time}</option>
			</c:forEach>
		</select> <br><br>
	<input type="submit">
</form>
</body>
</html>