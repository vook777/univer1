<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Home page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:set var="prefix" value="${pageContext.request.contextPath}"/>
</head>
<body>
	<a href="${prefix}/auditoriums">Auditoriums</a>
	<a href="${prefix}/courses">Courses</a>
	<a href="${prefix}/faculties">Faculties</a>
	<a href="${prefix}/groups">Groups</a>
	<a href="${prefix}/lectures">Lectures</a>
	<a href="${prefix}/students">Students</a>
	<a href="${prefix}/teachers">Teachers</a>
</body>
</html>
