<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="esc" uri="/WEB-INF/escapeJsTaglib.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="q" uri="/WEB-INF/questionTaglib.tld"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>

<div class="wide-block" id="question-detail">
	<h1>
		<a href="<c:url value="/admin/test/home"/>"><spring:message code="link.label.back.to.test.list"/></a>
		<a href="<c:url value="/admin/test/detail?id=${question.test.id}"/>"><spring:message code="link.label.back.to.test.detail"/></a>
		<span>${question.test.intitule}</span> &gt;	<span>${question.intitule}</span>
	</h1>
	
	<div class="control">
		<c:if test="${question.test.status eq 'DRAFT'}">
			<div class="container">
				<a href="#" onclick="addProposition(); return false;" class="action add no-float">
					<spring:message code="link.label.question.detail.add.proposition"/>
				</a>
			</div>
			
			<div class="container" style="margin-top: 10px">
				<a href="#" onClick="editQuestionDetail ( ${question.id} , '<esc:escapeJs input="${question.intitule}"/>' , '${question.niveau}' , '<esc:escapeJs input="${question.contenu}" lines="true" />'); return false ; " class="action edit no-float">
					<spring:message code="link.label.question.edit" />
				</a>
			</div>
			
			<div class="container" style="margin-top: 10px">
				<c:url value="/admin/question/delete" var="deleteUrl">
					<c:param name="test" value="${question.test.id}"/>
					<c:param name="question" value="${question.id}"/>
				</c:url>
				<a href="#" onclick="deleteQuestion ('<esc:escapeJs input="${question.intitule}"/>' , '${deleteUrl}' ) ; return false;" class="action delete no-float">
					<spring:message code="link.label.candidate.detail.delete"/>
				</a>
			</div>
		</c:if>
		<c:if test="${question.test.status ne 'DRAFT'}">
			<div class="container">
				<c:url value="/admin/test/duplicate" var="duplicateUrl">
					<c:param name="test" value="${question.test.id}"/>
				</c:url>
				<a href="#" onclick="duplicateTest('${question.test.intitule}', '${duplicateUrl}'); return false;" class="action duplicate no-float">
					<spring:message code="link.label.test.duplicate" />
				</a>
				
				<c:if test="${question.test.status eq 'VALIDATED'}">
					<c:url value="/admin/test/archive" var="archiveUrl">
						<c:param name="test" value="${question.test.id}"/>
						<c:param name="origin" value="detail" />
					</c:url>
					<a href="#" onclick="archiveTest('${question.test.intitule}', '${archiveUrl}'); return false;" class="action archive no-float">
						<spring:message code="link.label.test.archive" />
					</a>
				</c:if>
			</div>
		</c:if>
	</div>
	
	<div class="central-left">
		<div id="question-id" class="container">
			<h2><spring:message code="text.admin.question.page.identity" /></h2>
			${question.intitule}<br/>
			<span>Niveau</span> ${question.niveau}<br/>
			<span>Nb propositions</span> ${fn:length(question.propositionsReponses)}<br/><br/>
			<span>Contenu</span><br/><q:format input="${question.contenu}" mode="display" />
			
			<div class="clear-both"></div>
		</div>
		
		<div id="question-edit" class="container side-form-detail center-form-detail">
			<h2><spring:message code="text.admin.questions.page.edit" /></h2>
	
			<c:url value="/admin/question/createUpdate" var="createUpdateUrl">
				<c:param name="origin" value="detail"/>
			</c:url>
			<form:form action="${createUpdateUrl}" commandName="questionForm" method="POST" >
		        <form:hidden id="questionId" path="id"/>
		        <form:hidden path="testId" value="${testSheet.id}" />
				
				<div>		
			        <form:label path="intitule"><spring:message code="text.admin.questions.page.intitule" />*</form:label>
			        <form:input id="questionIntitule" path="intitule" size="10" maxlength="100"/>
			        <form:errors path="intitule" cssClass="error"/>
		        </div>
		        
		        <div>
			        <form:label path="niveau"><spring:message code="text.admin.questions.page.niveau" />*</form:label> 
			        <form:select path="niveau" id="questionNiveau">
			        	<c:forEach items="${questionForm.levels}" var="level">
							<form:option value="${level}" label="${level}" />
			        	</c:forEach>
			        </form:select>
		        </div>
		        
		        <div class="last-element">
			        <form:label path="contenu"><spring:message code="text.admin.questions.page.contenu" />*</form:label>
			        <a href="#" class="help"><span><spring:message code="text.help.question.input" /></span></a>    
			        <form:textarea id="questionContenu" path="contenu" cols="20" />
			        <form:errors path="contenu" cssClass="error"/><br/>
		        </div>
		        
		        <a class="back-to-add-form" href="#" onclick="cancelEditQuestionDetail(); return false;"><spring:message code="link.label.cancel.question.edit" /></a>
		        <input type="submit" id="question-submit-button" value="<spring:message code="form.question.edit"/>" class="submit-button small" />
			</form:form>
			<div class="clear-both"></div>
		</div>
		
		<div id="propositions" class="container" style="margin-top: 10px">
			<h2><spring:message code="text.admin.question.page.propositions" /></h2>
			
			<c:set var="count" value="1"/>
			<c:forEach items="${question.propositionsReponses}" var="proposition">
				<div class="detail-list-item">
					<c:if test="${question.test.status eq 'DRAFT'}">
						<p class="actions" style="z-index:<c:out value="${100 - count}"/>">
							<a href="#" class="actions-down"></a>
							<span class="sub-actions">
								
								<c:url value="/admin/proposition/delete" var="deleteUrl">
									<c:param name="proposition" value="${proposition.id}"/>
									<c:param name="question" value="${question.id}"/>
								</c:url>
								<a href="#" onclick="deletePropositionReponse ('${deleteUrl}') ; return false ;" class="action delete">
								<spring:message code="link.label.common.delete"/></a> 
								
								<a href="#" onClick="editProposition (${proposition.id} , ${proposition.propositionCorrecte} , '<esc:escapeJs input="${proposition.valeur}"/>'); return false ; " class="action edit">
								<spring:message code="link.label.proposition.edit" /></a>						
							</span>
						</p>
					</c:if>
					
					<span class="question-count">${count}</span>
										
					<c:if test="${proposition.propositionCorrecte}"><span class="correct"><spring:message code="text.admin.propositionReponses.page.proposition.correcte.affichage"/></span></c:if>
					<c:if test="${!proposition.propositionCorrecte}"><span class="incorrect"><spring:message code="text.admin.propositionReponses.page.proposition.incorrecte.affichage"/></span></c:if>
					<br/><br/>
					<p style="margin-left: 40px">
						<span class="important"><q:format input="${proposition.valeur}" mode="display" /></span><br/> 
						
					</p>
					
				</div>	
				<c:set var="count" value="${count + 1}"/>			
			</c:forEach>
		</div>
		
		<div id="proposition-edit" class="container side-form-detail center-form-detail" style="margin-top: 10px">
			<h2><spring:message code="text.admin.questions.page.add.proposition" /></h2>
	
			<c:url value="/admin/proposition/createUpdate" var="createUpdateUrl"/>
			<form:form action="${createUpdateUrl}" commandName="propositionForm" method="POST" >
		        <form:hidden id="propositionId" path="id"/>
		        <form:hidden path="questionId" value="${question.id}" />
				
				<div>
					<form:label path="propositionCorrecte"><spring:message code="text.admin.propositionReponses.page.proposition" /></form:label>
					<span class="radio"><spring:message code="text.admin.propositionReponses.page.proposition.incorrecte" /></span>
					<form:radiobutton id="propositionCorrecte" path="propositionCorrecte" value="false" />
					<span class="radio"><spring:message code="text.admin.propositionReponses.page.proposition.correcte" /></span>
			        <form:radiobutton id="propositionCorrecte" path="propositionCorrecte"  value="true" />
					<div class="clear-both"></div>
				</div>
				
				<div class="last-element">		
			        <form:label path="valeur"><spring:message code="text.admin.propositionReponses.page.valeur" />*</form:label>
			        <a href="#" class="help"><span><spring:message code="text.help.question.input" /></span></a> 
			        <form:textarea id="propositionValeur" path="valeur" cols="20" />
			        <form:errors path="valeur" cssClass="error"/>
		        </div>
		        
		        <a class="back-to-add-form" href="#" onclick="cancelProposition(); return false;"><spring:message code="link.label.cancel.question.add" /></a>
		        <input type="submit" id="proposition-submit-button" value="<spring:message code="form.question.add"/>" class="submit-button small" />
			</form:form>
			<div class="clear-both"></div>
		</div>
	</div>
	
	<div class="clear-both"></div>
</div>

<script>
$(document).ready(function() {
	textAddPropositionReponse = "<spring:message code="text.admin.questions.page.add.proposition"/>";
	textEditPropositionReponse = "<spring:message code="text.admin.questions.page.edit.proposition"/>";
	dialogTextDeletePropositionReponse = "<spring:message code="dialog.text.delete.propositionReponses"/>";
	dialogTextDeleteQuestion = "<spring:message code="dialog.text.delete.questions"/>";
	dialogTextDuplicateTest = "<spring:message code="dialog.text.duplicate.test"/>";
	dialogTextArchiveTest = "<spring:message code="dialog.text.archive.test"/>";
	
	<c:if test="${displayEditQuestionForm}">
	$("#question-id").slideUp(function() {
		$(".back-to-add-form").show();
		$("#question-edit").slideDown(function() {
			adjustFooterHeight();
		});
	});	
	</c:if>
	<c:if test="${displayPropositionForm}">
		$("#propositions").slideUp(function() {
			$(".back-to-add-form").show();
			$("#proposition-edit").slideDown(function() {
				adjustFooterHeight();
			});
		});	
	</c:if>
	<jsp:include page="../notify-action.jsp"/>
});
</script>	
