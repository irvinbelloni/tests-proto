<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="wide-block">
	<h1>
		<a href="<c:url value="/admin/candidates"/>"><spring:message code="link.label.back.to.candidate.list"/></a>
		<span>${profil.prenom} ${profil.nom}</span> (<spring:message code="content.title.candidate.detail"/>) 
	</h1>
	
	<div class="identity">
		<div class="container" id="identity">
			<h2><spring:message code="content.title.candidate.detail.identity"/></h2>
			<span>Pr�nom</span> ${profil.prenom}<br/>
			<span>Nom</span> ${profil.nom}<br/>
			<span>Adresse email</span> ${profil.email}
			<br/><br/>
			<span>Login</span> ${profil.login}<br/>
			<span>Mot de passe</span> ${profil.pass}
			<br/><br/>
			<span style="width: auto">
				<c:if test="${profil.enabled}"><spring:message code="text.candidat.activated"/></c:if>
				<c:if test="${!profil.enabled}"><spring:message code="text.candidat.deactivated"/></c:if>
			</span>
			<div class="clear-both"></div>
		</div>
		
		<div id="profil-detail-edit" class="container side-form-detail">
			<h2><spring:message code="text.side.form.title.candidate.edit" /></h2>
			
			<c:url value="/admin/profile/add-or-edit" var="addOrEditUrl">
				<c:param name="origin" value="candidate"/>
			</c:url>
			<form:form method="post" action="${addOrEditUrl}" commandName="profil" id="profil-form">
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
					<label><spring:message code="form.user.active"/></label>
					<form:radiobutton path="active" value="1"/><span class="radio"><spring:message code="text.yes" /></span> 
					<form:radiobutton path="active" value="0"/><span class="radio"><spring:message code="text.no" /></span>
					<div class="clear-both"></div>
				</div>
							
				<a class="back-to-add-form" href="#" onclick="backToDetailIdentity(); return false;"><spring:message code="link.label.cancel.quick.update" /></a>
				<input type="submit" class="submit-button small" id="submit-button" value="<spring:message code="form.user.edit"/>" />
			</form:form>
			<div class="clear-both"></div>
		</div>
	</div>
	
	<div class="control">
		<div class="container">
			<c:url value="/admin/profile/delete" var="linkUrl">
				<c:param name="profile" value="${profil.id}"/>
				<c:param name="origin" value="candidate"/>
			</c:url>
			<a href="#" onclick="assignTest(); return false;" class="action link no-float">
				<spring:message code="link.label.candidate.detail.link.test"/>
			</a>
		</div>
		
		<div class="container" style="margin-top: 10px">
			<a href="#" onclick="editProfilDetail(${profil.id}, '${profil.prenom}', '${profil.nom}', '${profil.email}', '${profil.login}', '${profil.pass}', '${profil.dateActivation}', '${profil.dateActivation}'); return false;" class="action edit no-float">
				<spring:message code="link.label.candidate.detail.edit" />
			</a>
			<c:url value="/admin/profile/activate" var="activateUrl">
				<c:param name="profile" value="${profil.id}"/>
				<c:param name="origin" value="candidate"/>
			</c:url>
			<a href="${activateUrl}" class="action activate no-float">
				<c:if test="${profil.enabled}"><spring:message code="link.label.candidate.detail.deactivate" /></c:if>
				<c:if test="${!profil.enabled}"><spring:message code="link.label.candidate.detail.activate" /></c:if>
			</a>
		</div>
		
		<div class="container" style="margin-top: 10px">
			<c:url value="/admin/profile/delete" var="deleteUrl">
				<c:param name="profile" value="${profil.id}"/>
				<c:param name="origin" value="candidate"/>
			</c:url>
			<a href="#" onclick="deleteProfil(${profil.id}, '${profil.prenom}', '${profil.nom}', '${deleteUrl}', '<spring:message code="dialog.title.delete.candidate"/>'); return false;" class="action delete no-float">
				<spring:message code="link.label.candidate.detail.delete"/>
			</a>
		</div>
	</div>
	
	<div class="profil-tests">
		<div class="container" id="assigned-tests">
			<h2><spring:message code="content.title.candidate.detail.assigned.tests"/></h2>
			<c:forEach items="${profil.evaluations}" var="eval">
				<c:if test="${!eval.resultatOK}">
					<p>
						<c:url value="/admin/candidate" var="deleteEvalUrl">
							<c:param name="candidate" value="${profil.id}"/>
							<c:param name="evaluation" value="${eval.id}"/>
						</c:url>
						<span>(<a href="${deleteEvalUrl}">supprimer</a>)</span>
						${eval.test.intitule}
					</p>
				</c:if>
			</c:forEach>
		</div>
		
		<div id="profil-detail-assign-test" class="container side-form-detail center-form-detail">
			<h2><spring:message code="text.side.form.title.candidate.assign.test" /></h2>
			
			<c:url value="/admin/profile/assign-test" var="assignTestUrl"/>
			<form:form method="post" action="${assignTestUrl}" commandName="assignTest" id="assign-test-form">
				<form:hidden path="candidateId" value="${profil.id}" />
				
				<div class="last-element">
					<form:select path="testId">
					    <form:option value="0" label="Choisir le test � assigner" />
					    <form:options items="${tests}" itemValue="id" itemLabel="intitule" />
					</form:select>
				</div>				
											
				<a class="back-to-add-form" href="#" onclick="backToDetailAssignedTests(); return false;"><spring:message code="link.label.cancel.test.assignment" /></a>
				<input type="submit" class="submit-button small" id="submit-button" value="<spring:message code="form.user.assign"/>" />
				<p style="border:0; height: 17px"></p>
			</form:form>
		</div>
		
		<div class="container" style="margin-top: 10px">
			<h2><spring:message code="content.title.candidate.detail.taken.tests"/></h2>
			<c:forEach items="${profil.evaluations}" var="eval">
				<c:if test="${eval.resultatOK}">
					<p>
						${eval.test.intitule}
					</p>
				</c:if>
			</c:forEach>
		</div>
	</div>
	
	<div class="clear-both"></div>
</div>

<div class="historic">
	<c:forEach items="${profil.historique}" var="trace">
		<p><span><fmt:formatDate value="${trace.timestamp}" pattern="dd/MM/yyyy HH:mm"/> - </span><spring:message code="text.histo.${trace.action}"/> ${trace.admin.prenom} ${trace.admin.nom}</p>
	</c:forEach>
	
</div>

<script>
$(document).ready(function() {	
	dialogTextDeleteProfil = "<spring:message code="dialog.text.delete.candidate"/>";
	
	if ($("#mode").val() == "edit") {
		$("#identity").slideUp(function() {
			$("#profil-detail-edit").slideDown(function() {
				adjustFooterHeight();
			});
		});	
	}
	<jsp:include page="../notify-action.jsp"/>	
});
</script>
