<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="esc" uri="/WEB-INF/escapeJsTaglib.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>

<div class="wide-block" id="test-detail">
	<h1>
		<a href="<c:url value="/admin/test/home"/>"><spring:message code="link.label.back.to.test.list"/></a>
		<span>${testSheet.intitule}</span> (<spring:message code="content.title.test.detail"/> ${testSheet.type}) 
	</h1>
	
	<div class="identity">
		<div class="container" id="identity">
			<h2><spring:message code="content.title.test.detail.identity"/></h2>
			${testSheet.intitule}<br/>
			<span>Type</span> ${testSheet.type}<br/>
			<span>Durée</span> ${testSheet.duree} <spring:message code="text.admin.tests.page.minutes"/>
			<br/><br/>
			<span>Nb questions</span> ${testSheet.questionSize}
			<div class="clear-both"></div>
		</div>
		
		<div id="detail-edit" class="container side-form-detail">
			<h2><spring:message code="text.side.form.title.tests.edit" /></h2>
			
			<c:url value="/admin/test/createUpdate" var="editUrl">
				<c:param name="origin" value="detail" />
			</c:url>				
			<form:form method="post" action="${editUrl}" commandName="testSheet" id="test-form">
				<form:hidden path="id" />
			
				<div>
					<label for="intitule"><spring:message code="form.test.intitule"/>*</label>
					<form:input path="intitule" />
					<form:errors path="intitule" class="error" />
				</div>
				
				<div>
					<label for="type"><spring:message code="form.test.type"/>*</label>
					<form:input path="type" />
					<form:errors path="type" class="error" />
				</div>
							
				<div class="last-element">	
					<label for="duree"><spring:message code="form.test.duree"/>*</label>
					<form:input path="duree" />
					<form:errors path="duree" class="error" />
				</div>
			
				<a class="back-to-add-form" href="#" onclick="backToDetailIdentity(); return false;"><spring:message code="link.label.cancel.quick.update" /></a>
				<input type="submit" class="submit-button small" id="submit-button" value="<spring:message code="form.user.edit"/>" />
			</form:form>
			<div class="clear-both"></div>
		</div>		
	</div>
	
	<div class="control">
		<div class="container">
			<a href="#" onclick="addQuestion(); return false;" class="action add no-float">
				<spring:message code="link.label.test.detail.add.question"/>
			</a>
		</div>
		
		<div class="container" style="margin-top: 10px">
			<a href="#" onclick="editTestDetail(); return false;" class="action edit no-float">
				<spring:message code="link.label.test.detail.edit" />
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
	
	<div class="central">
		<div id="question-edit" class="container side-form-detail center-form-detail">
			<h2><spring:message code="text.admin.questions.page.ajout" /></h2>
	
			<c:url value="/admin/question/createUpdate" var="createUpdateUrl">
				<c:param name="origin" value="test"/>
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
			        <form:input id="questionNiveau" path="niveau" maxlength="100"/>
			        <form:errors path="niveau" cssClass="error"/>
		        </div>
		        
		        <div class="last-element">
			        <form:label path="contenu"><spring:message code="text.admin.questions.page.contenu" />*</form:label>     
			        <form:textarea id="questionContenu" path="contenu" cols="20" />
			        <form:errors path="contenu" cssClass="error"/><br/>
		        </div>
		        
		        <a class="back-to-add-form" href="#" onclick="$('#question-edit').slideUp(); return false;"><spring:message code="link.label.cancel.question.add" /></a>
		        <input type="submit" id="question-submit-button" value="<spring:message code="form.question.add"/>" class="submit-button small" />
			</form:form>
			<div class="clear-both"></div>
		</div>
	
		<div class="container" id="questions">
			<h2><spring:message code="content.title.test.detail.questions"/></h2>
			<c:set var="count" value="1"/>
			<c:forEach items="${testSheet.questions}" var="question">
				<div class="detail-list-item">
					<p class="actions">
						<a href="#" class="actions-down"></a>
						<span class="sub-actions">
							
							<c:url value="/admin/question/delete" var="deleteUrl">
								<c:param name="id" value="${question.id}"/>
							</c:url>
							<a href="#" onclick="deleteQuestion (${question.id} , '${question.intitule}' , '${deleteUrl}') ; return false ;" class="action delete">
							<spring:message code="link.label.common.delete"/></a> 
							
							<a href="#" onClick="editQuestion ( ${question.id} , '<esc:escapeJs input="${question.intitule}"/>' , '${question.niveau}' , '<esc:escapeJs input="${question.contenu}" lines="true" />'); return false ; " class="action edit">
							<spring:message code="link.label.question.edit" /></a>
							   
							<c:url value="/admin/question/detail" var="detailUrl">
								<c:param name="id" value="${currentTest.id}"/>
							</c:url>
							<a href="${detailUrl}" class="action detail">
							<spring:message code="link.label.question.detail"/></a>							
						</span>
					</p>
					
					<span class="question-count">${count}<br/><br/><br/><br/></span>
					
					<span class="important"><a class="detail-link" href="${detailUrl}">${question.intitule}</a></span> (niveau: <span class="important">${question.niveau}</span>)<br/> 
					<c:set var="plural" value=" " />
					<c:set var="list" value="true" />
					<c:if test="${fn:length(question.propositionsReponses) gt 1}">
						<c:set var="plural" value="s" />
					</c:if>
					<span class="important">${fn:length(question.propositionsReponses)}</span> <spring:message code="text.admin.questions.page.reponses" arguments="${plural}"/>
					<br/><br/>
					${question.contenu}
					
				</div>	
				<c:set var="count" value="${count + 1}"/>			
			</c:forEach>
		</div>
		
		
		
		
	</div>
	
	<div class="clear-both"></div>
</div>


<div class="historic">
	<c:forEach items="${testSheet.historique}" var="trace">
		<p><span><fmt:formatDate value="${trace.timestamp}" pattern="dd/MM/yyyy HH:mm"/> - </span><spring:message code="text.histo.${trace.action}"/> ${trace.admin.prenom} ${trace.admin.nom}</p>
	</c:forEach>
	
</div>


<div id="dialog-confirm" title="<spring:message code="dialog.title.delete.questions"/>">
	<p></p>
</div>

<script>
$(document).ready(function() {
	textAddQuestion = "<spring:message code="text.side.form.title.questions.add"/>";
	textEditQuestion = "<spring:message code="text.side.form.title.questions.edit"/>";
	dialogTextDeleteQuestion = "<spring:message code="dialog.text.delete.questions"/>";
	
	<c:if test="${displayEditForm}">
		$("#identity").slideUp(function() {
			$(".back-to-add-form").show();
			$("#detail-edit").slideDown(function() {
				adjustFooterHeight();
			});
		});	
	</c:if>
	
	
	<c:if test="${displayAddQuestionForm}">
		$("#question-edit").slideDown();
	</c:if>
	<jsp:include page="../notify-action.jsp"/>
});
</script>	
