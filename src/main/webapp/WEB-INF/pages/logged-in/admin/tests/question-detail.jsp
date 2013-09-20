<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>

<div class="side-form">

	<h2><spring:message code="text.admin.propositionReponses.page.ajout" /></h2>
	
	<c:url value="admin/proposition/createUpdate" var="createUpdateUrl"/>
	<form:form action="${pageContext.request.contextPath}/admin/proposition/createUpdate" commandName="proposition" method="POST" >
		
        <form:hidden id="propositionId" path="id"/>
        <form:hidden path="questionId" value="${question.id}"/>
		<div>		
	        <form:label path="valeur">
	             <spring:message code="text.admin.propositionReponses.page.valeur" />
	        </form:label>
	        <form:input id="propositionValeur" path="valeur" maxlength="255"/>
	        <form:errors path="valeur" cssClass="error"/><br/>
        </div>
        
        <div >
	        <form:label path="propositionCorrecte">
	             <spring:message code="text.admin.propositionReponses.page.proposition" />
	        </form:label>
			<span class="radio"><spring:message code="text.admin.propositionReponses.page.proposition.incorrecte" /></span>
			<form:radiobutton id="propositionCorrecte" path="propositionCorrecte" value="false" />
			<span class="radio"><spring:message code="text.admin.propositionReponses.page.proposition.correcte" /></span>
	        <form:radiobutton id="propositionCorrecte" path="propositionCorrecte"  value="true" />
			
	        <form:errors path="propositionCorrecte" cssClass="error"/><br/>
        </div>
        
        <input type="submit" value="<spring:message code="form.proposition.add"/>" class="submit-button small" />
	</form:form>
</div>

<div class="left-list">
	<h2><spring:message code="text.admin.questions.page.detail" arguments="${question.intitule}" /></h2>
	
	<p><spring:message code="text.admin.questions.page.niveau" />${question.niveau}</p>
	<p><spring:message code="text.admin.questions.page.contenu" />${question.contenu}</p>
	
	<p class="empty-list">
		<c:if test="${fn:length(propositions) gt 0}">
			<spring:message code="text.admin.propositionReponses.page.liste" />
		</c:if>	
		<c:if test="${fn:length(propositions) eq 0}">
			<spring:message code="text.empty.propositions" />
		</c:if>
	</p>
	
	<c:forEach var="currentPropositionReponse" items="${propositions}">
		<div class="list-item">
			<p class="actions">
				<a href="#" class="actions-down"></a>
				<span class="sub-actions">
				
					<c:url value="/admin/proposition/delete" var="deleteUrl">
						<c:param name="id" value="${currentPropositionReponse.id}"/>
					</c:url>
					<a href="#" onclick="deletePropositionReponse ('${currentPropositionReponse.id}' , '${currentPropositionReponse.valeur}' , '${deleteUrl}') ; return false ; " class="action delete">
					<spring:message code="link.label.common.delete"/></a>
					
					<a href="#" onClick="editPropositionReponse ( '${currentPropositionReponse.id}' , '${currentPropositionReponse.valeur}' , '${currentPropositionReponse.propositionCorrecte}' ) ; return false ; " class="action edit">
					<spring:message code="link.label.common.edit" /></a>
					
				</span>
			</p>
			<p class="main">
				<c:if test="${currentPropositionReponse.propositionCorrecte}">
					<spring:message code="text.admin.propositionReponses.page.proposition.correcte.affichage"/>
				</c:if>
				<c:if test="${!currentPropositionReponse.propositionCorrecte}">
					<spring:message code="text.admin.propositionReponses.page.proposition.incorrecte.affichage"/>
				</c:if>
			</p>
			<p class="detail">
				<span class="label"><spring:message code="text.admin.propositionReponses.page.valeur" /></span>${currentPropositionReponse.valeur} <br />
			</p>
			<div class="clear-left"></div>
		</div>
	</c:forEach>
</div>

<div class="clear-both"></div>

<script>
$(document).ready(function() {
	textAddPropositionReponse = "<spring:message code="text.side.form.title.propositions.add"/>";
	textEditPropositionReponse = "<spring:message code="text.side.form.title.propositions.edit"/>";
	dialogTextDeletePropositionReponse = "<spring:message code="dialog.text.delete.propositionReponses"/>";
	
});
</script>	
