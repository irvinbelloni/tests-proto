<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div>
	<div class="home-block">
		<c:set var="plural" value=" " />
		<c:set var="list" value="true" />
		<c:if test="${fn:length(tests) gt 1}">
			<c:set var="plural" value="s" />
		</c:if>
		<c:if test="${fn:length(tests) eq 0}">
			<c:set var="list" value="false" />
		</c:if>
		<h2>
			<c:if test="${list eq true}">${fn:length(tests)} <spring:message code="text.admin.home.tests.count" arguments="${plural}"/></c:if>
			<c:if test="${list eq false}"><spring:message code="text.admin.home.tests.count.none" /></c:if>
		</h2>
		<div>
			<a href="<c:url value="/admin/test/home"/>"><spring:message code="link.label.admin.home.tests.see" /></a>
			
			<c:if test="${list eq true}">
				<h3><spring:message code="text.admin.home.latest.tests" /></h3>
				<c:forEach items="${tests}" var="test" begin="0" end="4">
					<c:url value="/admin/test/detail" var="detailUrl">
						<c:param name="id" value="${test.id}"/>
					</c:url>
					<a href="${detailUrl}">${test.intitule}</a>
				</c:forEach>
			</c:if>
		</div>
	</div>
	
	<div class="home-block">
		<c:set var="plural" value=" " />
		<c:set var="list" value="true" />
		<c:if test="${fn:length(candidates) gt 1}">
			<c:set var="plural" value="s" />
		</c:if>
		<c:if test="${fn:length(candidates) eq 0}">
			<c:set var="list" value="false" />
		</c:if>
		<h2>
			<c:if test="${list eq true}">${fn:length(candidates)} <spring:message code="text.admin.home.users.count" arguments="${plural}"/></c:if>
			<c:if test="${list eq false}"><spring:message code="text.admin.home.users.count.none" /></c:if>
		</h2>
		<div>
			<a href="<c:url value="/admin/candidates"/>"><spring:message code="link.label.admin.home.users.see" /></a>
			
			<c:if test="${list eq true}">
				<h3><spring:message code="text.admin.home.latest.users" /></h3>
				<c:forEach items="${candidates}" var="candidate" begin="0" end="4">
					<a href="/">${candidate.prenom} ${candidate.nom}</a>
				</c:forEach>
			</c:if>
		</div>
	</div>
	
	<div class="home-block">
		<c:set var="plural" value=" " />
		<c:if test="${fn:length(administrators) gt 1}">
			<c:set var="plural" value="s" />
		</c:if>
		<h2>${fn:length(administrators)} <spring:message code="text.admin.home.admins.count" arguments="${plural}"/></h2>
		<div>
			<a href="<c:url value="/admin/administrators"/>"><spring:message code="link.label.admin.home.administrators.see" /></a>
		</div>
	</div>
	
	<div class="clear-both"></div>
</div>