<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Group Index</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="prefix" value="${pageContext.request.contextPath}" />
</head>
<body>
	<a href="${prefix}/">Back</a>
	<br><br>
	<a href="${prefix}/saveGroupForm">Save</a>
	<a href="${prefix}/deleteGroupForm">Delete</a>
	<a href="${prefix}/group/findGroup.jsp">Find</a>
	<a href="${prefix}/showAllGroups">Show All</a>
</body>
</html>
