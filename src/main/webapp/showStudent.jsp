<%@page import="com.foxminded.univer.models.Student"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
	<%
	    Student s = (Student) request.getAttribute("student");
	%>
	<c:out value=s.id/>
	
	
	<c:set var="ssss" value="${student.id}" />
	<c:out value=ssss.id/>
	<h1>Hello ${ssss.id}!!!</h2>
</body>
</html>