<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav>
	<jsp:include page="user-info.jsp"/>
	
	<ul>
		<%-- Home tab --%>
		<c:set var="selectedClass" value="" />
		<c:if test="${selectedTab eq 'home'}"><c:set var="selectedClass" value="selected-tab" /></c:if>
		<li class="${selectedClass}"><a href="<c:url value="/admin/home"/>"><spring:message code="menu.label.home" /></a></li>
		
		<%-- Tests tab --%>
		<c:set var="selectedClass" value="" />
		<c:if test="${selectedTab eq 'test'}"><c:set var="selectedClass" value="selected-tab" /></c:if>
		<li class="${selectedClass}"><a href="<c:url value="/admin/test/home"/>"><spring:message code="menu.label.tests" /></a></li>
		
		<%-- Candidates tab --%>
		<c:set var="selectedClass" value="" />
		<c:if test="${selectedTab eq 'candidate'}"><c:set var="selectedClass" value="selected-tab" /></c:if>
		<li class="${selectedClass}"><a href="<c:url value="/admin/candidates"/>"><spring:message code="menu.label.users" /></a></li>
		
		<%-- Administrators tab --%>
		<c:set var="selectedClass" value="" />
		<c:if test="${selectedTab eq 'administrator'}"><c:set var="selectedClass" value="selected-tab" /></c:if>
		<li class="${selectedClass}"><a href="<c:url value="/admin/administrators"/>"><spring:message code="menu.label.administrators" /></a></li>
	</ul>
</nav>