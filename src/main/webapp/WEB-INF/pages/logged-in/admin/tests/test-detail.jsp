<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>

<div class="side-form">

	<h2><spring:message code="text.admin.questions.page.ajout" /></h2>
	
	<c:url value="admin/question/createUpdate" var="createUpdateUrl"/>
	<form:form action="${pageContext.request.contextPath}/admin/question/createUpdate" commandName="questionForm" method="POST" >
        <form:hidden id="questionId" path="id"/>
        <form:hidden path="testId" value="${testSheet.id}" />
		
		<div>		
	        <form:label path="intitule">
	             <spring:message code="text.admin.questions.page.intitule" />
	        </form:label>
	        <form:input id="questionIntitule" path="intitule" maxlength="25"/>
	        <form:errors path="intitule" cssClass="error"/><br/>
        </div>
        
        <div>
	        <form:label path="niveau">
	             <spring:message code="text.admin.questions.page.niveau" />
	        </form:label>   
	        <form:select path="niveau" items="${questionForm.levels}" />  
	        <form:errors path="niveau" cssClass="error"/><br/>
        </div>
        
        <div class="last-element">
	        <form:label path="contenu">
	             <spring:message code="text.admin.questions.page.contenu" />
	        </form:label>     
	        <form:textarea id="questionContenu" path="contenu" cols="20" />
	        <form:errors path="contenu" cssClass="error"/><br/>
        </div>
        
        <input type="submit" value="<spring:message code="form.question.add"/>" class="submit-button small" />
	</form:form>
</div>

<div class="left-list">
	<h2><spring:message code="text.admin.tests.page.detail" arguments="${testSheet.intitule}" /></h2>
	
	<p><spring:message code="text.admin.tests.page.duree" />${testSheet.duree}</p>
	<p><spring:message code="text.admin.tests.page.type" />${testSheet.type}</p>
	
	<p class="empty-list">
		<c:if test="${fn:length(questions) gt 0}">
			<spring:message code="text.admin.questions.page.liste" />
		</c:if>	
		<c:if test="${fn:length(questions) eq 0}">
			<spring:message code="text.empty.questions" />
		</c:if>
	</p>
	
	<c:forEach var="currentQuestion" items="${questions}">
		<div class="list-item">
			<p class="actions">
				<a href="#" class="actions-down"></a>
				<span class="sub-actions">
				
					<c:url value="/admin/question/delete" var="deleteUrl">
						<c:param name="id" value="${currentQuestion.id}"/>
					</c:url>
					<a href="#" onclick="deleteQuestion ('${currentQuestion.id}' , '${currentQuestion.intitule}' , '${deleteUrl}') ; return false ; " class="action delete">
					<spring:message code="link.label.common.delete"/></a>
					
					<a href="#" onClick="editQuestion ( '${currentQuestion.id}' , '${currentQuestion.intitule}' , '${currentQuestion.niveau}' , '${currentQuestion.contenu}') ; return false ; " class="action edit">
					<spring:message code="link.label.common.edit" /></a>
					
					<c:url value="/admin/question/detail" var="detailUrl">
						<c:param name="id" value="${currentQuestion.id}"/>
					</c:url>
					<a href="${detailUrl}" class="action detail">
					<spring:message code="link.label.question.detail"/></a>
					
				</span>
			</p>
			<p class="main">${currentQuestion.niveau}</p>
			<p class="detail">
				<span class="label"><spring:message code="text.admin.questions.page.intitule" /></span>${currentQuestion.intitule} <br />
				<span class="label"><spring:message code="text.admin.questions.page.contenu" /></span>${currentQuestion.contenu} <br />
				<span class="label"><spring:message code="text.admin.questions.page.reponses" /></span>${currentQuestion.propositionSize}
			</p>
			<div class="clear-left"></div>
		</div>
	</c:forEach>
</div>

<div class="clear-both"></div>

<script>
$(document).ready(function() {
	textAddQuestion = "<spring:message code="text.side.form.title.questions.add"/>";
	textEditQuestion = "<spring:message code="text.side.form.title.questions.edit"/>";
	dialogTextDeleteQuestion = "<spring:message code="dialog.text.delete.questions"/>";
	
});
</script>	
