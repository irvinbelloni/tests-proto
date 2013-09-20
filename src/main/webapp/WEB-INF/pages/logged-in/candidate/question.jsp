<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="wide-block">
	<h1>	
		<p><spring:message code="text.test.remaining.time"/>: <span class="normal" id="remaining-time"></span></p>
		<span>${evaluation.test.intitule}</span>&nbsp;&nbsp;<span class="normal"><spring:message code="text.test.question"/> ${questionCount}</span> / ${evaluation.test.questionSize}
	</h1>
	
	<div class="question">
		${question.contenu}
	</div>
	
	<c:url value="/tests/validate-question" var="submitUrl"/>
	<form:form commandName="questionForm" action="${submitUrl}" method="post" class="question-form">
		<form:hidden path="evaluationId"/>
		<form:hidden path="questionIndex"/>
		<form:hidden path="questionId"/>
		<div class="answer">	
			<table>
				<c:set var="trClass" value="odd"/>
				<c:set var="i" value="1"/>
				<c:forEach items="${question.propositionsReponses}" var="reponse">
					<tr class="${trClass}">
						<c:set var="checked" value="" />
						<c:forEach items="${questionForm.propositions}" var="checkedId">
							<c:if test="${checkedId eq reponse.id}">
								<c:set var="checked" value="checked" />
							</c:if>
						</c:forEach>
						<td class="checkbox"><input id="propositions${i}" name="propositions" type="checkbox" value="${reponse.id}" ${checked}/></td>
						<td><label for="propositions${i}">${reponse.valeur}</label></td>
					</tr>
					<c:choose>
					    <c:when test="${trClass eq 'odd'}">
					        <c:set var="trClass" value="even"/>
					    </c:when>
					    <c:otherwise>
					        <c:set var="trClass" value="odd"/>
					    </c:otherwise>
					</c:choose>
					<c:set var="i" value="${i + 1}"/>
				</c:forEach>
			</table>
		</div>
		
		<div class="navigation">
			<c:if test="${questionCount gt 1}">
				<c:url value="/tests/question" var="previousQuestionUrl">
					<c:param name="test" value="${evaluation.id}"/>
					<c:param name="question" value="${questionCount - 1}"/>
				</c:url>
				<a href="${previousQuestionUrl}" class="previous"><spring:message code="link.label.test.question.previous"/></a>
			</c:if>
			<c:choose>
				<c:when test="${questionCount eq evaluation.test.questionSize}">
					<input type="submit" class="submit-button" value="<spring:message code="link.label.test.finish"/>"/>
				</c:when>
				<c:otherwise>
					<input type="submit" class="submit-button" value="<spring:message code="link.label.test.question.next"/>"/>
				</c:otherwise>
			</c:choose>		
			
			<div class="question-navigation">
				<c:set var="i" value="1"/>
				<c:forEach items="${evaluation.test.questions}" var="questionLoop">
					<c:set var="buttonClass" value="very-small-grey"/>
					
					<c:forEach items="${evaluation.responses}" var="response">
						<c:if test="${response.question.id eq questionLoop.id}">
							<c:if test="${fn:length(response.reponsesChoisies) gt 0}">
								<c:set var="buttonClass" value="very-small"/>
							</c:if>
						</c:if>
					</c:forEach>
					<c:url value="/tests/question" var="questionUrl">
						<c:param name="test" value="${evaluation.id}"/>
						<c:param name="question" value="${i}"/>
					</c:url>
					<a href="${questionUrl}" class="${buttonClass}">${i}</a>
					<c:set var="i" value="${i + 1}"/>
				</c:forEach>
			</div>
			
			<div class="clear-both"></div>
		</div>
	</form:form>
</div>

<div id="log"></div>

<script>
$(document).ready(function() {
	textTestTimeOver = "<spring:message code="text.detail.test.time.over"/>";
	textTestTimeOverPrecision = "<spring:message code="text.detail.test.time.over.precision"/>";
	remainingTime = ${remainingTime};
	
	displayRemainingTime(${evaluation.id});
	timeoutRT = setInterval('displayRemainingTime(${evaluation.id})', 1000 );
});
</script>