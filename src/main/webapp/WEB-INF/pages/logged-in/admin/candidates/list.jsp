<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
	<div class="side-form">
		<h2><spring:message code="text.side.form.title.candidate.add" /></h2>
		
		<c:url value="/admin/profile/add-or-edit" var="addOrEditUrl">
			<c:param name="origin" value="candidates"/>
		</c:url>
		<form:form method="post" action="${addOrEditUrl}" commandName="profilForm" id="profil-form">
			<form:hidden path="id" />
			<form:hidden path="mode" />
		
			<div>
				<label for="prenom"><spring:message code="form.user.firstname"/>*</label>
				<form:input path="prenom" />
				<form:errors path="prenom" class="error" />
			</div>
			
			<div>
				<label><spring:message code="form.user.name"/>*</label>
				<form:input path="nom" />
				<form:errors path="nom" class="error" />
			</div>
						
			<div>
				<label><spring:message code="form.user.email"/>*</label>
				<form:input path="email" />
				<form:errors path="email" class="error" />
			</div>
			
			<div>
				<label><spring:message code="form.user.login"/>*</label>
				<form:input path="login" />
				<form:errors path="login" class="error" />
			</div>
			
			<div>
				<label><spring:message code="form.user.password"/>*</label>
				<form:input path="pass" />
				<form:errors path="pass" class="error" />
			</div>
						
			<div class="last-element">
				<form:checkbox path="active"/><span class="radio"><spring:message code="form.user.active"/></span>
				<div class="clear-both"></div>
			</div>
						
			<a class="back-to-add-form" href="#" onclick="backToAddProfil(); return false;"><spring:message code="link.label.cancel.quick.update" /></a>
			<input type="submit" class="submit-button small" id="submit-button" value="<spring:message code="form.user.add"/>" />
		</form:form>
	</div>
</sec:authorize>

<div class="left-list" <sec:authorize access="hasRole('ROLE_CONSULTANT')">style="margin-right:0"</sec:authorize>>
	<h2>
		<c:if test="${fn:length(candidates) gt 0}">
			<span>
				<spring:message code="text.sort.by"/>:&nbsp;&nbsp;
				<c:set var="sortOn" value="" />
				<c:set var="dir" value="asc" />
				<c:if test="${sortingInfo.sortingField eq 'prenom'}">
					<c:set var="sortOn" value="sort-on" />
					<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
						<c:set var="dir" value="desc" />
					</c:if>
				</c:if>		
				<c:url value="/admin/candidates" var="sortUrl">
					<c:param name="sort" value="prenom"/>
					<c:param name="direction" value="${dir}"/>
				</c:url>	
				<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.firstname"/></a>&nbsp;&nbsp;-&nbsp;
				
				<c:set var="sortOn" value="" />
				<c:set var="dir" value="asc" />
				<c:if test="${sortingInfo.sortingField eq 'nom'}">
					<c:set var="sortOn" value="sort-on" />
					<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
						<c:set var="dir" value="desc" />
					</c:if>
				</c:if>
				<c:url value="/admin/candidates" var="sortUrl">
					<c:param name="sort" value="nom"/>
					<c:param name="direction" value="${dir}"/>
				</c:url>
				<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.name"/></a>&nbsp;&nbsp;-&nbsp;
				
				<c:set var="sortOn" value="" />
				<c:set var="dir" value="asc" />
				<c:if test="${sortingInfo.sortingField eq 'id'}">
					<c:set var="sortOn" value="sort-on" />
					<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
						<c:set var="dir" value="desc" />
					</c:if>
				</c:if>
				<c:url value="/admin/candidates" var="sortUrl">
					<c:param name="sort" value="id"/>
					<c:param name="direction" value="${dir}"/>
				</c:url>
				<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.creation"/></a>
				
				<sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
					&nbsp;&nbsp;-&nbsp;
					<c:set var="sortOn" value="" />
					<c:set var="dir" value="asc" />
					<c:if test="${sortingInfo.sortingField eq 'dateActivation'}">
						<c:set var="sortOn" value="sort-on" />
						<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
							<c:set var="dir" value="desc" />
						</c:if>
					</c:if>
					<c:url value="/admin/candidates" var="sortUrl">
						<c:param name="sort" value="dateActivation"/>
						<c:param name="direction" value="${dir}"/>
					</c:url>
					<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.activation.date"/></a>
				</sec:authorize>
				
			</span>
		</c:if>
	</h2>
	
	<c:if test="${fn:length(candidates) eq 0}">
		<p class="empty-list">
			<spring:message code="text.empty.candidates"/>
		</p>
	</c:if>
	
	<c:set var="count" value="0"/>
	<c:forEach items="${candidates}" var="candidate">	
		<div class="list-item">
			<p class="actions" style="z-index:<c:out value="${100 - count}"/>">
				<a href="#" class="actions-down"></a>
				<span class="sub-actions">
					<sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
						<c:url value="/admin/profile/delete" var="deleteUrl">
							<c:param name="profile" value="${candidate.id}"/>
							<c:param name="origin" value="candidates"/>
						</c:url>
						<a href="#" onclick="deleteProfil(${candidate.id}, '${candidate.prenom}', '${candidate.nom}', '${deleteUrl}', '<spring:message code="dialog.title.delete.candidate"/>'); return false;" class="action delete"><spring:message code="link.label.profil.delete"/></a>
						<a href="#" onclick="editProfil(${candidate.id}, '${candidate.prenom}', '${candidate.nom}', '${candidate.email}', '${candidate.login}', '${candidate.pass}', '${candidate.dateActivation}', false); return false;" class="action edit">
							<spring:message code="link.label.profil.edit" />
						</a>
						<c:url value="/admin/profile/activate" var="activateUrl">
							<c:param name="profile" value="${candidate.id}"/>
							<c:param name="origin" value="candidates"/>
						</c:url>
						<a href="${activateUrl}" class="action activate">
							<c:if test="${candidate.enabled}"><spring:message code="link.label.candidate.desactivate" /></c:if>
							<c:if test="${!candidate.enabled}"><spring:message code="link.label.candidate.activate" /></c:if>
						</a>
					</sec:authorize>
					<c:url value="/admin/candidate" var="detailUrl">
						<c:param name="candidate" value="${candidate.id}"/>
					</c:url>
					<a href="${detailUrl}" class="action detail"><spring:message code="link.label.candidate.detail"/></a>
				</span>		
			</p>
			<p class="main">
				<a href="${detailUrl}">
					<c:if test="${sortingInfo.sortingField eq 'nom'}">${candidate.nom} ${candidate.prenom}</c:if>
					<c:if test="${sortingInfo.sortingField ne 'nom'}">${candidate.prenom} ${candidate.nom}</c:if>
				</a>
				<br/>
				<span>${candidate.email}</span>
			</p>
			<p class="detail">
				<sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
					<span class="label"><spring:message code="text.list.login.pass"/>:</span> ${candidate.login} / ${candidate.pass}<br/>
					<span class="label"><spring:message code="text.list.active"/>:</span> 
					<c:if test="${candidate.enabled}"><spring:message code="text.yes" /></c:if>
					<c:if test="${!candidate.enabled}"><spring:message code="text.no" /></c:if>
				</sec:authorize>				
			</p>
			<div class="clear-left"></div>		
		</div>
		<c:set var="count" value="${count + 1}"/>
	</c:forEach>
</div>

<div class="clear-both"></div>

<script>
$(document).ready(function() {
	textAddProfil = "<spring:message code="text.side.form.title.candidate.add"/>";
	textEditProfil = "<spring:message code="text.side.form.title.candidate.edit"/>";
	dialogTextDeleteProfil = "<spring:message code="dialog.text.delete.candidate"/>";
	
	if ($("#mode").val() == "edit") {
		$("#submit-button").val("Modifier");
		$(".side-form h2").html(textEditProfil);	
		$(".back-to-add-form").show();
	}
	
	<jsp:include page="../notify-action.jsp"/>	
});
</script>