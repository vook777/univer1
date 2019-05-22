<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Home page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:set var="prefix" value="${pageContext.request.contextPath}"/>
</head>
<body>
<a href="${prefix}/">Back</a><br>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Weeks</th>
        <th>Description</th>
    </tr>
    <c:forEach var="course" items="${requestScope.courses}">
    <tr>
        <td style="text-align:center">
            <c:out value="${course.id}" />
        </td>
        <td style="text-align:center">
            <c:out value="${course.name}" />
        </td>
        <td style="text-align:center">
            <c:out value="${course.numberOfWeeks}" />
        </td>
        <td style="text-align:center">
            <c:out value="${course.description}" />
        </td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
