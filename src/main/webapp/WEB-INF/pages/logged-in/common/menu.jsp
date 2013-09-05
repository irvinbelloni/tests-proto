<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<menu>
	<jsp:include page="user-info.jsp"/>
	
	<ul>
		<li><a href="/"><spring:message code="menu.label.tests" /></a></li>
		<li><a href="/"><spring:message code="menu.label.users" /></a></li>
		<li><a href="/"><spring:message code="menu.label.administrators" /></a></li>
	</ul>
</menu>