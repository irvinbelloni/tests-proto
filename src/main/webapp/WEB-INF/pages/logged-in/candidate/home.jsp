<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="wide-block">
	
	<c:if test="${fn:length(candidate.assignedTests) eq 0}">
		<p class="test-home"><spring:message code="text.no.test.assigned"/></p>
	</c:if>
	<c:forEach items="${candidate.assignedTests}" var="eval">
		<p class="test-home">
			<c:url value="/tests/setup" var="setupUrl">
				<c:param name="test" value="${eval.id}"/>
			</c:url>
			<a href="${setupUrl}" class="button small"><spring:message code="link.label.pass.test"/></a>
			${eval.test.intitule}<br/>
			<span><spring:message code="text.test.length" />: ${eval.test.duree}mn</span>
		</p>
	</c:forEach>
	<div class="clear-both"></div>
</div>