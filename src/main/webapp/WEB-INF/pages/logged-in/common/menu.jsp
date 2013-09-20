<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav>
	<jsp:include page="user-info.jsp"/>
	
	<ul>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
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
			
			<%-- Results tab --%>
			<c:set var="selectedClass" value="" />
			<c:if test="${selectedTab eq 'resultat'}"><c:set var="selectedClass" value="selected-tab" /></c:if>
			<li class="${selectedClass}"><a href="<c:url value="/admin/resultats"/>"><spring:message code="menu.label.resultats" /></a></li>
			
			<%-- Administrators tab --%>
			<c:set var="selectedClass" value="" />
			<c:if test="${selectedTab eq 'administrator'}"><c:set var="selectedClass" value="selected-tab" /></c:if>
			<li class="${selectedClass}"><a href="<c:url value="/admin/administrators"/>"><spring:message code="menu.label.administrators" /></a></li>
		</sec:authorize>
		
		<sec:authorize access="hasRole('ROLE_USER')">
			<%-- Home tab --%>
			<c:set var="selectedClass" value="" />
			<c:if test="${selectedTab eq 'home'}"><c:set var="selectedClass" value="selected-tab" /></c:if>
			<li class="${selectedClass}"><a href="<c:url value="/tests/home"/>"><spring:message code="menu.label.candidate.home" /></a></li>
			
			<%-- Current test tab --%>
			<c:if test="${testEnCours}">
				<c:set var="selectedClass" value="" />
				<c:if test="${selectedTab eq 'current.test'}"><c:set var="selectedClass" value="selected-tab" /></c:if>
				<li class="${selectedClass}"><a href="#"><spring:message code="menu.label.candidate.current.test" /></a></li>	
			</c:if>
		</sec:authorize>
	</ul>
 </nav>
