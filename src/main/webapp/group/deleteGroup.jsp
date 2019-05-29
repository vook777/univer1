<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Group</title>
</head>
<body><br>
<a href="${prefix}/univer/group/groupIndex.jsp">Back</a><br><br>
<br>
<form action="${pageContext.request.contextPath}/deleteGroup">
	Select Group: 
		<select name="groupId">
			<c:forEach var="group" items="${requestScope.groups}">
				<option value="${group.id}">${group.name}</option>
			</c:forEach>
		</select> <br><br>
	<input type="submit">
</form>
</body>
</html>