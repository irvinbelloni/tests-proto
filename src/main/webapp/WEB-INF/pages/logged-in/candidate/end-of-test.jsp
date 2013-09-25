<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:if test="${recentTest ne null}">
	<p class="test-home"><spring:message code="text.recent.test.only" arguments="${recentTest.test.intitule}"/></p>
</c:if>