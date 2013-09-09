<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<p class="user">
	<a href="${pageContext.request.contextPath}/logout"><spring:message code="link.label.log.out" /></a>&nbsp;&nbsp;&nbsp;
	<sec:authentication property="principal.prenom" /> <sec:authentication property="principal.nom" />
</p>