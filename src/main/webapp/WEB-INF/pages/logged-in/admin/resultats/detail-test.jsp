<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="q" uri="/WEB-INF/questionTaglib.tld"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="time" uri="/WEB-INF/timeTaglib.tld"%>

	<div class="control">
		<div class="container" id="main-actions">
			<c:url value="/admin/print/evaluation" var="exportUrl">
				<c:param name="id" value="${result.id}"/>
		</c:url>
		<a href="${exportUrl}" target="_blank" class="action export no-float">
			<spring:message code="link.label.common.export.pdf"/>
		</a>
	</div>
	
	<div class="container" id="result-menu">
		<h2><spring:message code="text.admin.navigation"/></h2>
		<c:set var="count" value="1"/>
		<a href="#" onclick="goToByScroll('test-result', 10); return false;"><spring:message code="text.admin.result.page.summary" /></a><br/>
		<c:forEach items="${result.responses}" var="response">
			<a href="#" onclick="goToByScroll('question-${count}', 10); return false;">
				<c:if test="${response.question.intitule ne ''}">
					Q.${count} - ${response.question.intitule}
				</c:if>
				<c:if test="${response.question.intitule eq ''}">
					<spring:message code="text.test.question" /> ${count}
				</c:if>
			</a><br/>
			<c:set var="count" value="${count + 1}"/>
		</c:forEach>
	</div> 
</div>

<div class="central-left">
	<div class="container" id="test-result">
		<h2><spring:message code="text.admin.result.page.summary" /></h2>
		<span>Type de test: </span>${result.test.type}<br/><br/>
		<span>Test passé le </span><fmt:formatDate value="${result.startTime}" pattern="dd/MM/yyyy"/> <span>&agrave;</span> <fmt:formatDate value="${result.startTime}" pattern="HH:mm"/><br/>
		<span>Temps :</span> <time:format input="${result.duration}" /> <span>(max. <time:format input="${result.test.duree * 60}" />)</span><br/><br/>
		
		<span>Score: </span> ${result.nbGoodAnswers}/${result.test.questionSize}
	</div>
	
	<c:set var="count" value="1"/>
	<c:forEach items="${result.responses}" var="response">
		<div class="container" id="question-${count}" style="margin-top:10px">
			<c:set var="correctClass" value="incorrect"/>
			<c:if test="${response.correct}">
				<c:set var="correctClass" value="correct"/>
			</c:if>
			<h2 class="${correctClass}" style="margin-bottom:10px;">
				<spring:message code="text.test.question" /> ${count}
				<c:if test="${response.question.intitule ne ''}">
					- ${response.question.intitule}
				</c:if>
			</h2>
			
			<span><spring:message code="text.admin.questions.page.niveau" />: </span><b>${response.question.niveau}</b><br/><br/>
			
			<q:format input="${response.question.contenu}" mode="display" />
			
			<table class="result-propositions">
				<c:set var="countProp" value="1"/>
				<c:forEach items="${response.question.propositionsReponses}" var="proposition">
					<tr>
						<td class="count">${countProp}</td>
						<td class="user-pick">
							<c:forEach items="${response.reponsesChoisies}" var="userPick">
								<c:if test="${userPick.id eq proposition.id}">
									<img src="<c:url value="/static/images/user-icon.png" />" alt="Choix de l'utilisateur" />
								</c:if>
							</c:forEach>								
						</td>
						<td class="propCorrect">
							<c:if test="${proposition.propositionCorrecte}">									
								<img src="<c:url value="/static/images/correct-icon.png" />" alt="Proposition correcte" />
							</c:if>
							<c:if test="${!proposition.propositionCorrecte}">									
								<img src="<c:url value="/static/images/uncorrect-icon.png" />" alt="Proposition incorrecte" />
							</c:if>
						</td>
						<td class="answer">
							<q:format input="${proposition.valeur}" mode="display" />
						</td>
					</tr>		
					<c:set var="countProp" value="${countProp + 1}"/>			
				</c:forEach>				
			</table>				
		</div>
		<c:set var="count" value="${count + 1}"/>
	</c:forEach>
</div>