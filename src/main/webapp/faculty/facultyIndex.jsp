<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Faculty Index</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="prefix" value="${pageContext.request.contextPath}" />
</head>
<body>
	<a href="${prefix}/">Back</a>
	<br><br>
	<a href="${prefix}/faculty/saveFaculty.jsp">Save</a>
	<a href="${prefix}/deleteFacultyForm">Delete</a>
	<a href="${prefix}/faculty/findFaculty.jsp">Find</a>
	<a href="${prefix}/showAllFaculties">Show All</a>
</body>
</html>
