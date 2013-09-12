<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>

<div class="side-form">

	<h2>
		<spring:message code="text.admin.tests.page.ajout" />
	</h2>

	<form:form  action="${pageContext.request.contextPath}/admin/test/createUpdate"
		commandName="testSheet" method="POST">

		<form:hidden path="id" id="testSheetId"/>
		<form:label path="intitule">
			<spring:message code="text.admin.tests.page.intitule" />
		</form:label>
		<form:input id="testSheetIntitule" path="intitule" size="10" maxlength="255" />
		<br />
		<form:errors path="intitule" cssClass="error" />

		<form:label path="duree">
			<spring:message code="text.admin.tests.page.duree" />
		</form:label>
		<form:input id="testSheetDuree" path="duree" size="3" maxlength="3" />
		<br />
		<form:errors path="duree" cssClass="error" />

		<form:label path="type">
			<spring:message code="text.admin.tests.page.type" />
		</form:label>
		<form:input id="testSheetType" path="type" size="10" maxlength="25" />
		<br />
		<form:errors path="type" cssClass="error" />

		<input type="submit" value="<spring:message code="form.test.add"/>" class="submit-button small" />
	</form:form>
</div>

<div class="list">
	<h2>
		<spring:message code="text.admin.tests.page.liste" />
	</h2>
	
	<c:if test="${fn:length(testLists) eq 0}">
		<spring:message code="text.admin.tests.page.liste.nulle" />
	</c:if>
	
	<c:forEach var="currentTest" items="${testLists}">
		<div class="list-item">
			<p class="actions">
				<a href="${pageContext.request.contextPath}/admin/test/delete?id=${currentTest.id}"
					class="action delete"/> 
				<a onclick="editTestSheet ( '${currentTest.id}' , '${currentTest.intitule}' , '${currentTest.duree}' ,
				   '${currentTest.type}' , 'Modifier' ) ; return false;" class="action edit"/>
				<a href="${pageContext.request.contextPath}/admin/test/detail?id=${currentTest.id}"
					class="action detail"/> 
				<a href="${pageContext.request.contextPath}/admin/test/print?id=${currentTest.id}"
					class="action delete"/> 
				   
			</p>
			<p class="main">${currentTest.type}</p>
			<p class="detail">
				<span class="label"><spring:message
						code="text.admin.tests.page.intitule" /></span>${currentTest.intitule} <br />
				<span class="label"><spring:message
						code="text.admin.tests.page.duree" /></span>${currentTest.duree} <br />
				<span class="label"><spring:message
						code="text.admin.tests.page.questions" /></span>${currentTest.questionSize}<br />
			</p>
			<div class="clear-left"></div>
		</div>
	</c:forEach>
</div>

<div class="clearer"></div>
