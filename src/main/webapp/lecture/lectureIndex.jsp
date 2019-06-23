<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Lecture Index</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="prefix" value="${pageContext.request.contextPath}" />
</head>
<body>
	<a href="${prefix}/">Back</a>
	<br><br>
	<a href="${prefix}/saveLectureForm">Save</a>
	<a href="${prefix}/deleteLectureForm">Delete</a>
	<a href="${prefix}/lecture/findLecture.jsp">Find</a>
	<a href="${prefix}/showAllLectures">Show All</a>
</body>
</html>
