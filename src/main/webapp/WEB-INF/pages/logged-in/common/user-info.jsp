<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<p class="user">
	<a href="<c:url value="/j_spring_security_logout" />"><spring:message code="link.label.log.out" /></a>&nbsp;&nbsp;&nbsp;
	<span><sec:authentication property="principal.prenom" /> <sec:authentication property="principal.nom" /></span>
	<c:set var="admin"><sec:authentication property="principal.admin" /></c:set>
	<c:set var="consultant"><sec:authentication property="principal.consultant" /></c:set>
	<c:choose>
		<c:when test="${consultant}">(consultant)</c:when>
		<c:when test="${admin}">(administrateur)</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>	
</p>