<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>All Groups</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:set var="prefix" value="${pageContext.request.contextPath}"/>
</head>
<body>
<a href="${prefix}/group/groupIndex.jsp">Back</a><br>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Faculty ID</th>
    </tr>
    <c:forEach var="group" items="${requestScope.groups}">
    <tr>
        <td style="text-align:center">
            <c:out value="${group.id}" />
        </td>
        <td style="text-align:center">
            <c:out value="${group.name}" />
        </td>
        <td style="text-align:center">
            <c:out value="${group.facultyId}" />
        </td>
    </tr>
    </c:forEach>
</table>
</body>
</html>