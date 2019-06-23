<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>All Lectures</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:set var="prefix" value="${pageContext.request.contextPath}"/>
</head>
<body>
<a href="${prefix}/lecture/lectureIndex.jsp">Back</a><br>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Course</th>
        <th>Auditorium</th>
        <th>Teacher</th>
        <th>Group</th>
        <th>Time</th>
    </tr>
    <c:forEach var="lecture" items="${requestScope.lectures}">
    <tr>
        <td style="text-align:center">
            <c:out value="${lecture.id}" />
        </td>
        <td style="text-align:center">
            <c:out value="${lecture.course.name}" />
        </td>
        <td style="text-align:center">
            <c:out value="${lecture.auditorium.name}" />
        </td>
        <td style="text-align:center">
            <c:out value="${lecture.teacher.firstName}" />
            <c:out value="${lecture.teacher.lastName}" />
        </td>
        <td style="text-align:center">
            <c:out value="${lecture.group.name}" />
        </td>
        <td style="text-align:center">
            <c:out value="${lecture.time}" />
        </td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
