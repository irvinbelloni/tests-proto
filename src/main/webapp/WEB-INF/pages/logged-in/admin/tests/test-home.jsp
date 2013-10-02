<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>

<div class="side-form">

	<h2><spring:message code="text.admin.tests.page.ajout" /></h2>
	
	<c:url value="/admin/test/createUpdate" var="createUpdateUrl">
		<c:param name="origin" value="home"/>
	</c:url>
	<form:form  action="${createUpdateUrl}" commandName="testSheetForm" method="POST">

		<form:hidden path="id" id="testSheetId"/>
		<form:hidden path="status"/>
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

		<a class="back-to-add-form" href="#" onclick="backToAddTest(); return false;"><spring:message code="link.label.cancel.quick.update" /></a>
		<input type="submit" value="<spring:message code="form.test.add"/>" id="submit-button" class="submit-button small" />
	</form:form>
</div>

<div class="left-list">
	<h2>
		<span>
			<spring:message code="text.sort.by"/>:&nbsp;&nbsp;
			<c:set var="sortOn" value="" />
			<c:set var="dir" value="asc" />
			<c:if test="${sortingInfo.sortingField eq 'intitule'}">
				<c:set var="sortOn" value="sort-on" />
				<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
					<c:set var="dir" value="desc" />
				</c:if>
			</c:if>		
			<c:url value="/admin/test/home" var="sortUrl">
				<c:param name="sort" value="intitule"/>
				<c:param name="direction" value="${dir}"/>
			</c:url>	
			<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.intitule"/></a>&nbsp;&nbsp;-&nbsp;
			
			<c:set var="sortOn" value="" />
			<c:set var="dir" value="asc" />
			<c:if test="${sortingInfo.sortingField eq 'type'}">
				<c:set var="sortOn" value="sort-on" />
				<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
					<c:set var="dir" value="desc" />
				</c:if>
			</c:if>
			<c:url value="/admin/test/home" var="sortUrl">
				<c:param name="sort" value="type"/>
				<c:param name="direction" value="${dir}"/>
			</c:url>
			<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.type"/></a>&nbsp;&nbsp;-&nbsp;
			
			<c:set var="sortOn" value="" />
			<c:set var="dir" value="asc" />
			<c:if test="${sortingInfo.sortingField eq 'duree'}">
				<c:set var="sortOn" value="sort-on" />
				<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
					<c:set var="dir" value="desc" />
				</c:if>
			</c:if>
			<c:url value="/admin/test/home" var="sortUrl">
				<c:param name="sort" value="duree"/>
				<c:param name="direction" value="${dir}"/>
			</c:url>
			<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.duree"/></a>&nbsp;&nbsp;-&nbsp;
			
			<c:set var="sortOn" value="" />
			<c:set var="dir" value="asc" />
			<c:if test="${sortingInfo.sortingField eq 'status'}">
				<c:set var="sortOn" value="sort-on" />
				<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
					<c:set var="dir" value="desc" />
				</c:if>
			</c:if>
			<c:url value="/admin/test/home" var="sortUrl">
				<c:param name="sort" value="status"/>
				<c:param name="direction" value="${dir}"/>
			</c:url>
			<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.status"/></a>		
		</span>
	</h2>
	
	<c:if test="${fn:length(tests) eq 0}">
		<spring:message code="text.admin.tests.page.liste.nulle" />
	</c:if>
	
	<c:set var="count" value="0"/>
	<c:forEach var="currentTest" items="${tests}">
		<div class="list-item">
			<p class="actions" style="z-index:<c:out value="${100 - count}"/>">
				<a href="#" class="actions-down"></a>
				<span class="sub-actions">
					<c:if test="${currentTest.status eq 'DRAFT'}">
						<c:url value="/admin/test/delete" var="deleteUrl">
							<c:param name="id" value="${currentTest.id}"/>
						</c:url>
						<a href="#" onclick="deleteTestSheet ( '${currentTest.id}' , '${currentTest.intitule}' , '${deleteUrl}' ) ; return false;" class="action delete">
						<spring:message code="link.label.common.delete"/></a>
						
						<c:if test="${currentTest.validable}">
							<c:url value="/admin/test/validate" var="validateUrl">
								<c:param name="test" value="${currentTest.id}"/>
								<c:param name="origin" value="home" />
							</c:url>
							<a href="#" onclick="validateTestSheet ('${currentTest.intitule}' , '${validateUrl}' ) ; return false;" class="action validate">
							<spring:message code="link.label.common.validate"/></a>
						</c:if>
						
						<c:if test="${!currentTest.validable}">
							<a href="#" onclick="displayWarningNotValidableTest(); return false;" class="action deactivated validate">
							<spring:message code="link.label.common.validate"/></a>
						</c:if>
					</c:if>
					
					<c:if test="${currentTest.status ne 'ARCHIVED'}">					
						<a href="#" onclick="editTestSheet ( '${currentTest.id}' , '${currentTest.intitule}' , '${currentTest.duree}' , '${currentTest.type}', '${currentTest.status}' ) ; return false;" class="action edit">
						<spring:message code="link.label.common.edit" /></a>
					</c:if>
					   
					<c:if test="${currentTest.status ne 'DRAFT'}"> 
						<c:url value="/admin/test/duplicate" var="duplicateUrl">
							<c:param name="test" value="${currentTest.id}"/>
						</c:url>
						<a href="#" onclick="duplicateTest('${currentTest.intitule}', '${duplicateUrl}'); return false;" class="action duplicate">
							<spring:message code="link.label.test.duplicate" />
						</a>
					</c:if>
					
					<c:if test="${currentTest.status eq 'VALIDATED'}"> 
						<c:url value="/admin/test/archive" var="archiveUrl">
							<c:param name="test" value="${currentTest.id}"/>
							<c:param name="origin" value="home" />
						</c:url>
						<a href="#" onclick="archiveTest('${currentTest.intitule}', '${archiveUrl}'); return false;" class="action archive">
							<spring:message code="link.label.test.archive" />
						</a>
					</c:if>					   
					   
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
			<p class="main">
				<a href="${detailUrl}">${currentTest.intitule}</a>
				<br/><br/>
				<span><spring:message code="text.test.status.${currentTest.status}" /></span>
			</p>
			<p class="detail">
				<span class="label"><spring:message code="text.admin.tests.page.type" />: </span> ${currentTest.type}<br/> 
				<span class="label"><spring:message code="text.admin.tests.page.duree" />: </span>${currentTest.duree} <spring:message code="text.admin.tests.page.minutes" /><br />
				<span class="label"><spring:message code="text.admin.tests.page.questions" />: </span>${currentTest.questionSize}<br />
			</p>
			<div class="clear-left"></div>
		</div>
		<c:set var="count" value="${count + 1}"/>
	</c:forEach>
</div>

<div class="clear-both"></div>

<script>
$(document).ready(function() {
	textAddTest = "<spring:message code="text.side.form.title.tests.add"/>";
	textEditTest = "<spring:message code="text.side.form.title.tests.edit"/>";
	dialogTextDeleteTest = "<spring:message code="dialog.text.delete.tests"/>";
	textTestWarningNotValidable = "<spring:message code="text.test.warning.not.validable"/>";
	dialogTextValidateTest = "<spring:message code="dialog.text.validate.test"/>";
	dialogTextDuplicateTest = "<spring:message code="dialog.text.duplicate.test"/>";
	dialogTextArchiveTest = "<spring:message code="dialog.text.archive.test"/>";
	
	if ($("#testSheetDuree").val() == 0) {
		$("#testSheetDuree").val("");
	}
	if ($("#testSheetId").val() != "" && $("#testSheetId").val() != "0") {
		$("#submit-button").val("Modifier");
		$(".side-form h2").html(textEditTest);	
		$(".back-to-add-form").show();
	}
	
	<jsp:include page="../notify-action.jsp"/>	
});
</script>	
