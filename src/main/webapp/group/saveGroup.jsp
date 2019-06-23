<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>Save Group</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="prefix" value="${pageContext.request.contextPath}" />
</head>
</head>
<body>
	<br>
	<a href="${prefix}/group/groupIndex.jsp">Back</a>
	<br>
	<br> Please enter group details:
	<br>
	<br>
	<form action="${pageContext.request.contextPath}/saveGroup">
		ID: <input type="text" name="id"> 
		Name: <input type="text" name="name"> 
		Faculty: 
		<select name="facultyId">
			<c:forEach var="faculty" items="${requestScope.faculties}">
				<option value="${faculty.id}">${faculty.name}</option>
			</c:forEach>
		</select> <br>
		<br> <input type="submit">
	</form>
	<br>
</body>
</html>
