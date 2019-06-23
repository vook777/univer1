<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>All Teachers</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:set var="prefix" value="${pageContext.request.contextPath}"/>
</head>
<body>
<a href="${prefix}/teacher/teacherIndex.jsp">Back</a><br>
<table border="1">
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Faculty ID</th>
    </tr>
    <c:forEach var="teacher" items="${requestScope.teachers}">
    <tr>
        <td style="text-align:center">
            <c:out value="${teacher.id}" />
        </td>
        <td style="text-align:center">
            <c:out value="${teacher.firstName}" />
        </td>
        <td style="text-align:center">
            <c:out value="${teacher.lastName}" />
        </td>
        <td style="text-align:center">
            <c:out value="${teacher.faculty.name}" />
        </td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
