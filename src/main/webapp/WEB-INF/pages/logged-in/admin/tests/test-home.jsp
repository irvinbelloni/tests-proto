<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>

<div class="side-form">

	<h2><spring:message code="text.admin.tests.page.ajout" /></h2>
	
	<c:url value="/admin/test/createUpdate" var="createUpdateUrl"/>
	<form:form  action="${createUpdateUrl}" commandName="testSheet" method="POST">

		<form:hidden path="id" id="testSheetId"/>
		<div>
			<form:label path="intitule">
				<spring:message code="text.admin.tests.page.intitule" />
			</form:label>
			<form:input id="testSheetIntitule" path="intitule" size="10" maxlength="255" />
			<form:errors path="intitule" cssClass="error" />
		</div>	

		<div>	
			<form:label path="duree">
				<spring:message code="text.admin.tests.page.duree" />
			</form:label>
			<form:input id="testSheetDuree" path="duree" size="3" maxlength="3" />
			<form:errors path="duree" cssClass="error" />
		</div>

		<div class="last-element">		
			<form:label path="type">
				<spring:message code="text.admin.tests.page.type" />
			</form:label>
			<form:input id="testSheetType" path="type" size="10" maxlength="25" />
			<form:errors path="type" cssClass="error" />
		</div>	

		<input type="submit" value="<spring:message code="form.test.add"/>" class="submit-button small" />
	</form:form>
</div>

<div class="left-list">
	<h2>
		<spring:message code="text.admin.tests.page.liste" />
	</h2>
	
	<c:if test="${fn:length(testLists) eq 0}">
		<spring:message code="text.admin.tests.page.liste.nulle" />
	</c:if>
	
	<c:forEach var="currentTest" items="${testLists}">
		<div class="list-item">
			<p class="actions">
				<a href="#" class="actions-down"></a>
				<span class="sub-actions">
					
					<c:url value="/admin/test/delete" var="deleteUrl">
						<c:param name="id" value="${currentTest.id}"/>
					</c:url>
					<a href="#" onclick="deleteTestSheet ( '${currentTest.id}' , '${currentTest.intitule}' , '${deleteUrl}' ) ; return false;" class="action delete">
					<spring:message code="link.label.common.delete"/></a> 
					
					<a href="#" onclick="editTestSheet ( '${currentTest.id}' , '${currentTest.intitule}' , '${currentTest.duree}' , '${currentTest.type}' ) ; return false;" class="action edit">
					<spring:message code="link.label.common.edit" /></a>
					   
					<c:url value="/admin/test/detail" var="detailUrl">
						<c:param name="id" value="${currentTest.id}"/>
					</c:url>
					<a href="${detailUrl}" class="action detail">
					<spring:message code="link.label.test.detail"/></a>
					
					<c:url value="/admin/test/print" var="printUrl">
						<c:param name="id" value="${currentTest.id}"/>
					</c:url>
					<a href="${printUrl}" class="action print">
					<spring:message code="link.label.common.print"/></a> 
					
				</span>
			</p>
			<p class="main">${currentTest.type}</p>
			<p class="detail">
				<span class="label"><spring:message code="text.admin.tests.page.intitule" /> : </span>${currentTest.intitule} <br/> 
				<span class="label"><spring:message code="text.admin.tests.page.duree" /> : </span>${currentTest.duree} <br />
				<span class="label"><spring:message code="text.admin.tests.page.questions" /></span>${currentTest.questionSize}<br />
			</p>
			<div class="clear-left"></div>
		</div>
	</c:forEach>
</div>

<div class="clear-both"></div>

<div id="dialog-confirm" title="<spring:message code="dialog.title.delete.tests"/>">
	<p></p>
</div>

<script>
$(document).ready(function() {
	textAddTest = "<spring:message code="text.side.form.title.tests.add"/>";
	textEditTest = "<spring:message code="text.side.form.title.tests.edit"/>";
	dialogTextDeleteTest = "<spring:message code="dialog.text.delete.tests"/>";
	
});
</script>	
