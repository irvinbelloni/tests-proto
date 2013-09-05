<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<header>
	<h1><spring:message code="text.header.name" /></h1>
	<a href="/${pageContext.request.contextPath}"><img src="${pageContext.request.contextPath}/static/images/logo.png" alt="<spring:message code="link.back.to.home" />" /></a>
</header>