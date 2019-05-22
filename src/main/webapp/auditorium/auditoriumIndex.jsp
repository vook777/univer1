<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Home page</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="prefix" value="${pageContext.request.contextPath}" />
</head>
<body>
	<a href="${prefix}/">Back</a>
	<br><br>
	<a href="${prefix}/auditorium/saveAuditorium.jsp">Save</a>
	<a href="${prefix}/auditorium/deleteAuditorium.jsp">Delete</a>
	<a href="${prefix}/auditorium/findAuditorium.jsp">Find</a>
	<a href="${prefix}/auditoriumShowAll">Show All</a>
</body>
</html>
