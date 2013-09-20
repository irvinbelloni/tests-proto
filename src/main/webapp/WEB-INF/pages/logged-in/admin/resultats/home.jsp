<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
	
<div class="side-form">

	<%-- TODO TDS - A utiliser au niveau de la page d'accueil or smthg 
	<form:form action="" commandName="assignForm" method="POST" >
		<form:select path="testId" >
			<form:option value="0" label="--- Select ---" />
			
			<c:forEach items="${testLists}" var="test">
				<form:option value="${test.id}" label="${test.intitule}" />
			</c:forEach>
			
		</form:select>
		
		<form:select path="candidateId" >
			<form:option value="0" label="--- Select ---" />
			
			<c:forEach items="${candidates}" var="candidate">
				<form:option value="${candidate.id}" label="${candidate.nom} - ${candidate.nom}" />
			</c:forEach>
		</form:select>
		
		<input type="submit" value="<spring:message code="form.evaluation.add"/>" class="submit-button small" />
	</form:form>
	--%>
</div>

<div class="left-list">

	<h2><spring:message code="text.admin.evaluations.page.encours" arguments="${testSheet.intitule}" /></h2>
	<c:forEach var="resultat" items="${resultats}">
		<div class="list-item">
			<p class="actions">
				<a href="#" class="actions-down"></a>
				<span class="sub-actions">
					<c:url value="/admin/resultat/print" var="printUrl">
						<c:param name="id" value="${resultat.id}"/>
					</c:url>
					<a href="${printUrl}" class="action print">
					<spring:message code="link.label.common.print"/></a> 
					
				</span>
			</p>
			<p class="main">${resultat.test.intitule}</p>
			<p class="detail">
				<span class="label">${resultat.profil.nom} - ${currentQuestion.contenu} </span>
				<span class="label">${resultat.start_time} </span>
				<span class="label">${resultat.end_time} </span>
			</p>
			<div class="clear-left"></div>
		</div>
	</c:forEach>
	
	<h2><spring:message code="text.admin.evaluations.page.termines" arguments="${testSheet.intitule}" /></h2>
	<c:forEach var="resultat" items="${resultats}">
		<div class="list-item">
			<p class="actions">
				<a href="#" class="actions-down"></a>
				<span class="sub-actions">
					<c:url value="/admin/resultat/print" var="printUrl">
						<c:param name="id" value="${resultat.id}"/>
					</c:url>
					<a href="${printUrl}" class="action print">
					<spring:message code="link.label.common.print"/></a> 
					
				</span>
			</p>
			<p class="main">${resultat.test.intitule}</p>
			<p class="detail">
				<span class="label">${resultat.profil.nom} - ${currentQuestion.contenu} </span>
				<span class="label">${resultat.start_time} </span>
				<span class="label">${resultat.end_time} </span>
			</p>
			<div class="clear-left"></div>
		</div>
	</c:forEach>
</div>

<div class="clear-both"></div>
