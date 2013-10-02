<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="time" uri="/WEB-INF/timeTaglib.tld"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
	
<div class="side-form">
	<h2><spring:message code="text.side.form.title.results.filter" /></h2>
	
	<c:url value="/admin/resultats/filter" var="filterUrl" />
	<form:form method="post" action="${filterUrl}" commandName="resultFilteringForm" id="filter-form">	
		<div>
			<label for="testName"><spring:message code="form.filter.test.name"/></label>
			<form:select path="testName" class="filter-input">
				<form:option value=""><spring:message code="form.filter.test.name.all"/></form:option>
				<form:options items="${tests}" itemValue="id" itemLabel="intitule" />
			</form:select>
		</div>
		
		<div>
			<label for="testType"><spring:message code="form.filter.test.type"/></label>
			<form:select path="testType" class="filter-input">
				<form:option value=""><spring:message code="form.filter.test.type.all"/></form:option>
				<form:options items="${testTypes}" />
			</form:select>
		</div>
					
		<div>
			<label for="candidateName"><spring:message code="form.filter.candidate"/></label>
			<form:select path="candidateName" class="filter-input">
				<form:option value=""><spring:message code="form.filter.candidate.all"/></form:option>
				<form:options items="${candidates}" itemValue="id" itemLabel="nomComplet" />
			</form:select>
		</div>
		
		<div class="last-element">
			<label style="width:100%; margin-bottom:-10px;"><spring:message code="form.filter.test.passing.date"/></label>
			<label for="passingDateFrom">Du</label>
			<form:input path="passingDateFrom" class="datepicker filter-input"/>
			<label for="passingDateTo">Au</label>
			<form:input path="passingDateTo" class="datepicker filter-input"/>
		</div>			
					
		<a href="#" onclick="emptyFilters(); return false;" class="previous small"><spring:message code="form.filter.clean"/></a>
		<input type="submit" class="submit-button small" id="submit-button" value="<spring:message code="form.filter"/>" />
	</form:form>
</div>

<div class="left-list" id="result-list">
	<h2 style="margin-top:10px">
		<c:if test="${fn:length(resultats) gt 0}">
			<span>
				<spring:message code="text.sort.by"/>:&nbsp;&nbsp;
				
				<c:set var="sortOn" value="" />
				<c:set var="dir" value="asc" />
				<c:if test="${sortingInfo.sortingField eq 'test'}">
					<c:set var="sortOn" value="sort-on" />
					<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
						<c:set var="dir" value="desc" />
					</c:if>
				</c:if>
				<c:url value="/admin/resultats" var="sortUrl">
					<c:param name="sort" value="test"/>
					<c:param name="direction" value="${dir}"/>
				</c:url>
				<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.test"/></a>&nbsp;&nbsp;-&nbsp;
				
				<c:set var="sortOn" value="" />
				<c:set var="dir" value="asc" />
				<c:if test="${sortingInfo.sortingField eq 'type'}">
					<c:set var="sortOn" value="sort-on" />
					<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
						<c:set var="dir" value="desc" />
					</c:if>
				</c:if>
				<c:url value="/admin/resultats" var="sortUrl">
					<c:param name="sort" value="type"/>
					<c:param name="direction" value="${dir}"/>
				</c:url>
				<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.type"/></a>&nbsp;&nbsp;-&nbsp;
				
				<c:set var="sortOn" value="" />
				<c:set var="dir" value="asc" />
				<c:if test="${sortingInfo.sortingField eq 'candidat'}">
					<c:set var="sortOn" value="sort-on" />
					<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
						<c:set var="dir" value="desc" />
					</c:if>
				</c:if>		
				<c:url value="/admin/resultats" var="sortUrl">
					<c:param name="sort" value="candidat"/>
					<c:param name="direction" value="${dir}"/>
				</c:url>	
				<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.candidate"/></a>&nbsp;&nbsp;-&nbsp;
				
				<c:set var="sortOn" value="" />
				<c:set var="dir" value="asc" />
				<c:if test="${sortingInfo.sortingField eq 'passage'}">
					<c:set var="sortOn" value="sort-on" />
					<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
						<c:set var="dir" value="desc" />
					</c:if>
				</c:if>
				<c:url value="/admin/resultats" var="sortUrl">
					<c:param name="sort" value="passage"/>
					<c:param name="direction" value="${dir}"/>
				</c:url>
				<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.passage"/></a>			
			</span>
		</c:if>
	</h2>
	<c:set var="count" value="0"/>
	<c:forEach var="resultat" items="${resultats}">
		<c:if test="${resultat.status eq 3}">
			<div class="list-item">
				<p class="actions" style="z-index:<c:out value="${100 - count}"/>">
					<a href="#" class="actions-down"></a>
					<span class="sub-actions">
						<c:url value="/admin/resultat/detail" var="detailUrl">
							<c:param name="evaluation" value="${resultat.id}"/>
						</c:url>
						<a href="${detailUrl}" class="action detail"><spring:message code="link.label.resultat.detail"/></a>
						
						<c:url value="/admin/print/evaluation" var="printUrl">
							<c:param name="id" value="${resultat.id}"/>
						</c:url>
						<a href="${printUrl}" class="action export"><spring:message code="link.label.common.export.pdf"/></a>						
					</span>
				</p>
				<p class="main">
					<a href="${detailUrl}">
						${resultat.test.intitule}<br/>
						<span>pass&eacute; par</span> <span class="submain">${resultat.profil.prenom} ${resultat.profil.nom}</span>
					</a>
				</p>
				<p class="detail">
					<span class="label">Type de test:</span> ${resultat.test.type}<br/>
					<span class="label">Date/Heure de passage: </span> <fmt:formatDate value="${resultat.startTime}" pattern="dd/MM/yyyy à HH:mm"/><br/>
					<span class="label">Temps: </span> <time:format input="${resultat.duration}" /><span class="label"> (max. <time:format input="${resultat.test.duree * 60}" />)</span><br/>
					<span class="label">Score: </span> ${resultat.nbGoodAnswers}/${resultat.test.questionSize}
				</p>
				<div class="clear-left"></div>
			</div>
		</c:if>
		<c:set var="count" value="${count + 1}"/>
	</c:forEach>
</div>

<div class="clear-both"></div>
<script>
$(document).ready(function() {	
	$(".filter-input").change(function() {
		$("#filter-form").submit();
	});
});
</script>
