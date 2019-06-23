<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Student Index</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="prefix" value="${pageContext.request.contextPath}" />
</head>
<body>
	<a href="${prefix}/">Back</a>
	<br><br>
	<a href="${prefix}/saveStudentForm">Save</a>
	<a href="${prefix}/deleteStudentForm">Delete</a>
	<a href="${prefix}/student/findStudent.jsp">Find</a>
	<a href="${prefix}/showAllStudents">Show All</a>
</body>
</html>
