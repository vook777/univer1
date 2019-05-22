<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Home page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:set var="prefix" value="${pageContext.request.contextPath}"/>
</head>
<body>
	<a href="${prefix}/auditorium/auditoriumIndex.jsp">Auditoriums</a>
	<a href="${prefix}/course/courseIndex.jsp">Courses</a>
	<a href="${prefix}/faculty/facultyIndex.jsp">Faculties</a>
	<a href="${prefix}/group/groupIndex.jsp">Groups</a>
	<a href="${prefix}/lecture/lectureIndex.jsp">Lectures</a>
	<a href="${prefix}/student/studentIndex.jsp">Students</a>
	<a href="${prefix}/teacher/teacherIndex.jsp">Teachers</a>
</body>
</html>
