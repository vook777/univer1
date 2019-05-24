<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	Auditorium ID: <input type="text" name="auditoriumId">
	Course ID: <input type="text" name="courseId">
	<br><br>
	Group ID: <input type="text" name="groupId">
	Teacher ID: <input type="text" name="teacherId">
	Time: <input type="text" name="time">
	<br><br>
	<input type="submit">
</form>
</body>
</html>
