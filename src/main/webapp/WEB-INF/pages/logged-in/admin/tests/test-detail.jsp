<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>

<div class="side-form">

	<h2>
		<spring:message code="text.admin.questions.page.ajout" />
	</h2>
	<form:form action="${pageContext.request.contextPath}/admin/question/createUpdate"
		commandName="question" method="POST" >
		
        <form:hidden path="id"/>
        
        <form:label path="intitule">
             <spring:message code="text.admin.questions.page.intitule" />
        </form:label>
        <form:input path="intitule" size="10" maxlength="25"/>
        <form:errors path="intitule" cssClass="error"/><br/>
        
        <form:label path="niveau">
             <spring:message code="text.admin.questions.page.niveau" />
        </form:label>     
        <form:input path="niveau" size="3" maxlength="3"/>
        <form:errors path="niveau" cssClass="error"/><br/>
        
        <form:label path="contenu">
             <spring:message code="text.admin.questions.page.contenu" />
        </form:label>     
        <form:input path="contenu" size="10" maxlength="25"/>
        <form:errors path="contenu" cssClass="error"/><br/>
        
        <input type="submit" value="<spring:message code="form.test.add"/>" class="submit-button small" />
	</form:form>
</div>

<div class="list">
	<h2>
		<spring:message code="text.admin.tests.page.liste" />
	</h2>
	
	<p>
		<spring:message code="text.admin.tests.page.duree" />${testSheet.duree}
		<spring:message code="text.admin.tests.page.type" />${testSheet.type}
	</p>
	
	<h3>
		<c:if test="${fn:length(questions) gt 0}">
			<spring:message code="text.admin.questions.page.liste" arguments="${testSheet.intitule}" />
		</c:if>	
		<c:if test="${fn:length(questions) eq 0}">
			<spring:message code="text.admin.questions.page.liste.nulle" arguments="${testSheet.intitule}" />
		</c:if>
	</h3>
	
	<c:forEach var="currentQuestion" items="${questions}">
		<div class="list-item">
			<p class="actions">
				<a href="${pageContext.request.contextPath}/admin/question/delete?id=${currentQuestion.id}"
					class="action delete"></a>
				<a onClick="editQUestion ( '${currentQuestion.id}' , '${currentQuestion.intitule}' , '${currentQuestion.contenu}' , '' ) ; return false ; "
					class="action edit"></a>
			</p>
			<p class="main">${currentQuestion.niveau}</p>
			<p class="detail">
				<span class="label"><spring:message
						code="text.admin.questions.page.intitule" /></span>${currentQuestion.intitule} <br />
				<span class="label"><spring:message
						code="text.admin.questions.page.contenu" /></span>${currentQuestion.contenu} <br />
				<span class="label"><spring:message
						code="text.admin.questions.page.reponses" /></span>${currentQuestion.propositionSize}
				<br />
			</p>
			<div class="clear-left"></div>
		</div>
	</c:forEach>
</div>

<div class="clearer"></div>
