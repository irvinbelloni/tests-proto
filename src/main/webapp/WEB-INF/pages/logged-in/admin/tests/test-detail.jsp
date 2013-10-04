<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="esc" uri="/WEB-INF/escapeJsTaglib.tld"%>
<%@ taglib prefix="q" uri="/WEB-INF/questionTaglib.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>

<div class="wide-block" id="test-detail">
	<h1>
		<a href="<c:url value="/admin/test/home"/>"><spring:message code="link.label.back.to.test.list"/></a>
		<span>${testSheet.intitule}</span> 
	</h1>
	
	<div class="identity">
		<div class="container" id="identity">
			<h2><spring:message code="content.title.test.detail.identity"/></h2>
			${testSheet.intitule}<br/>
			<span>Type</span> ${testSheet.type}<br/>
			<span>Durée</span> ${testSheet.duree} <spring:message code="text.admin.tests.page.minutes"/>
			<br/><br/>
			<span>Nb questions</span> ${testSheet.questionSize}
			<br/><br/>
			<spring:message code="text.test.status.${testSheet.status}" />
			<div class="clear-both"></div>
		</div>
		
		<sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
			<div id="detail-edit" class="container side-form-detail">
				<h2><spring:message code="text.side.form.title.tests.edit" /></h2>
				
				<c:url value="/admin/test/createUpdate" var="editUrl">
					<c:param name="origin" value="detail" />
				</c:url>				
				<form:form method="post" action="${editUrl}" commandName="testSheet" id="test-form">
					<form:hidden path="id" />
					<form:hidden path="status"/>
				
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
		</sec:authorize>	
	</div>
	
	<div class="control">
		<sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
			<c:if test="${testSheet.status eq 'VALIDATED'}">
				<div class="container" id="main-actions">
					<a href="#" onclick="editTestDetail(); return false;" class="action edit no-float">
						<spring:message code="link.label.test.detail.edit" />
					</a>
				</div>
				
				<div class="container" style="margin-top: 10px">
					<c:url value="/admin/test/duplicate" var="duplicateUrl">
						<c:param name="test" value="${testSheet.id}"/>
					</c:url>
					<a href="#" onclick="duplicateTest('${testSheet.intitule}', '${duplicateUrl}'); return false;" class="action duplicate no-float">
						<spring:message code="link.label.test.duplicate" />
					</a>
					
					<c:url value="/admin/test/archive" var="archiveUrl">
						<c:param name="test" value="${testSheet.id}"/>
						<c:param name="origin" value="detail" />
					</c:url>
					<a href="#" onclick="archiveTest('${testSheet.intitule}', '${archiveUrl}'); return false;" class="action archive no-float">
						<spring:message code="link.label.test.archive" />
					</a>
				</div>			
			</c:if>	
			
			<c:if test="${testSheet.status eq 'ARCHIVED'}">
				<div class="container">
					<c:url value="/admin/test/duplicate" var="duplicateUrl">
						<c:param name="test" value="${testSheet.id}"/>
					</c:url>
					<a href="#" onclick="duplicateTest('${testSheet.intitule}', '${duplicateUrl}'); return false;" class="action duplicate no-float">
						<spring:message code="link.label.test.duplicate" />
					</a>
				</div>			
			</c:if>		
			
			<c:if test="${testSheet.status eq 'DRAFT'}">
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
					<c:if test="${testSheet.validable}">
						<c:url value="/admin/test/validate" var="validateUrl">
							<c:param name="test" value="${testSheet.id}"/>
							<c:param name="origin" value="detail"/>
						</c:url>
						<a href="#" onclick="validateTestSheet ('${currentTest.intitule}' , '${validateUrl}' ) ; return false;" class="action validate no-float">
						<spring:message code="link.label.common.validate"/></a>
					</c:if>
					
					<c:if test="${!testSheet.validable}">
						<a href="#" onclick="displayWarningNotValidableTest(); return false;" class="action deactivated validate no-float">
						<spring:message code="link.label.common.validate"/></a>
					</c:if>
				</div>
			
				<div class="container" style="margin-top: 10px">
					<c:url value="/admin/test/delete" var="deleteUrl">
						<c:param name="id" value="${testSheet.id}"/>
					</c:url>
					<a href="#" onclick="deleteTestSheet ( '${testSheet.id}' , '${testSheet.intitule}' , '${deleteUrl}' ) ; return false;" class="action delete no-float">
						<spring:message code="link.label.candidate.detail.delete"/>
					</a>
				</div>
			</c:if>
		</sec:authorize>
		
		<div class="container" id="result-menu" <sec:authorize access="hasRole('ROLE_CONSULTANT')">style="margin-top:0"</sec:authorize>>
			<h2><spring:message code="text.admin.navigation"/></h2>
			<c:set var="count" value="1"/>
			<c:forEach items="${testSheet.questions}" var="question">
				<a href="#" onclick="goToByScroll('question-${count}', 0); return false;">
					<c:if test="${question.intitule ne ''}">
						Q.${count} - ${question.intitule}
					</c:if>
					<c:if test="${question.intitule eq ''}">
						<spring:message code="text.test.question" /> ${count}
					</c:if>
				</a><br/>
				<c:set var="count" value="${count + 1}"/>
			</c:forEach>
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
		        
		        <a class="back-to-add-form" href="#" onclick="$('#question-edit').slideUp(); return false;"><spring:message code="link.label.cancel.question.add" /></a>
		        <input type="submit" id="question-submit-button" value="<spring:message code="form.question.add"/>" class="submit-button small" />
			</form:form>
			<div class="clear-both"></div>
		</div>
	
		<div class="container" id="questions">
			<h2><spring:message code="content.title.test.detail.questions"/></h2>
			<c:set var="count" value="1"/>
			<c:forEach items="${testSheet.questions}" var="question">
				<div class="detail-list-item" id="question-${count}">
					<p class="actions" style="z-index:<c:out value="${100 - count}"/>">
						<a href="#" class="actions-down"></a>
						<span class="sub-actions">
							
							<c:if test="${testSheet.status eq 'DRAFT'}">
								<c:url value="/admin/question/delete" var="deleteUrl">
									<c:param name="question" value="${question.id}"/>
									<c:param name="test" value="${testSheet.id}"/>
								</c:url>
								<a href="#" onclick="deleteQuestion ('${question.intitule}' , '${deleteUrl}') ; return false ;" class="action delete">
								<spring:message code="link.label.common.delete"/></a> 
								
								<a href="#" onClick="editQuestion ( ${question.id} , '<esc:escapeJs input="${question.intitule}"/>' , '${question.niveau}' , '<esc:escapeJs input="${question.contenu}" lines="true" />'); return false ; " class="action edit">
								<spring:message code="link.label.question.edit" /></a>
							</c:if>
							   
							<c:url value="/admin/question/detail" var="detailUrl">
								<c:param name="id" value="${question.id}"/>
							</c:url>
							<a href="${detailUrl}" class="action detail">
							<spring:message code="link.label.question.detail"/></a>							
						</span>
					</p>
					
					<span class="question-count">${count}</span>
					
					<p style="margin-left: 40px">
						<span class="important"><a class="detail-link" href="${detailUrl}">${question.intitule}</a></span> (niveau: <span class="important">${question.niveau}</span>)<br/> 
						<c:set var="plural" value=" " />
						<c:set var="list" value="true" />
						<c:if test="${fn:length(question.propositionsReponses) gt 1}">
							<c:set var="plural" value="s" />
						</c:if>
						<span class="important">${fn:length(question.propositionsReponses)}</span> <spring:message code="text.admin.questions.page.reponses" arguments="${plural}"/>
						<br/><br/>
						<q:format input="${question.contenu}" mode="display" />
					</p>
					
				</div>	
				<c:set var="count" value="${count + 1}"/>			
			</c:forEach>
		</div>		
	</div>
	
	<div class="clear-both"></div>
</div>

<sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
	<div class="historic">
		<c:forEach items="${testSheet.historique}" var="trace">
			<p><span><fmt:formatDate value="${trace.timestamp}" pattern="dd/MM/yyyy HH:mm"/> - </span><spring:message code="text.histo.${trace.action}"/> ${trace.admin.prenom} ${trace.admin.nom}</p>
		</c:forEach>		
	</div>
</sec:authorize>

<div id="dialog-confirm" title="<spring:message code="dialog.title.delete.questions"/>">
	<p></p>
</div>

<script>
$(document).ready(function() {
	textAddQuestion = "<spring:message code="text.side.form.title.questions.add"/>";
	textEditQuestion = "<spring:message code="text.side.form.title.questions.edit"/>";
	dialogTextDeleteQuestion = "<spring:message code="dialog.text.delete.questions"/>";
	dialogTextDeleteTest = "<spring:message code="dialog.text.delete.tests"/>";
	dialogTextValidateTest = "<spring:message code="dialog.text.validate.test"/>";
	textTestWarningNotValidable = "<spring:message code="text.test.warning.not.validable"/>";
	dialogTextDuplicateTest = "<spring:message code="dialog.text.duplicate.test"/>";
	dialogTextArchiveTest = "<spring:message code="dialog.text.archive.test"/>";
	
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
	
	$("#result-menu").sticky({topSpacing:0});
	$("#result-menu").css("width", $("#main-actions").css("width") );
});
</script>	
