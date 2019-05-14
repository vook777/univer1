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
        <th>Capacity</th>
    </tr>
    <c:forEach var="auditorium" items="${requestScope.auditoriums}">
    <tr>
        <td style="text-align:center">
            <c:out value="${auditorium.id}" />
        </td>
        <td style="text-align:center">
            <c:out value="${auditorium.name}" />
        </td>
        <td style="text-align:center">
            <c:out value="${auditorium.capacity}" />
        </td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
