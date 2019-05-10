<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:set var="nameOfVariable" value="valueOfVariable" />
<c:out value="${nameOfVariable}">No name</c:out>
<br />
<c:if test="${nameOfVariable==null}">
Empty
</c:if>
<br />
<c:choose>
	<%--if else--%>
	<c:when test="${nameOfVariable==null}">
		Empty
	</c:when>
	<c:when test="${nameOfVariable=='valueOfVariable'}">
		valueOfVariable
	</c:when>
	<c:otherwise>
		Something else...
	</c:otherwise>
</c:choose>
<br />
<br />
 