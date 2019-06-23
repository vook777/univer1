<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>All Students</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:set var="prefix" value="${pageContext.request.contextPath}"/>
</head>
<body>
<a href="${prefix}/student/studentIndex.jsp">Back</a><br>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Last Name</th>
        <th>Card Number</th>
        <th>Group</th>
    </tr>
    <c:forEach var="student" items="${requestScope.students}">
    <tr>
        <td style="text-align:center">
            <c:out value="${student.id}" />
        </td>
        <td style="text-align:center">
            <c:out value="${student.firstName}" />
        </td>
        <td style="text-align:center">
            <c:out value="${student.lastName}" />
        </td>
        <td style="text-align:center">
            <c:out value="${student.studentCardNumber}" />
        </td>
        <td style="text-align:center">
            <c:out value="${student.group.name}" />
        </td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
