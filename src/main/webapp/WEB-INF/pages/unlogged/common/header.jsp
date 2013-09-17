<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<header class="unlogged">
	<a href="<c:url value="/" />"><img src="${pageContext.request.contextPath}/static/images/logo.png" alt="<spring:message code="link.back.to.home" />" /></a>
</header>