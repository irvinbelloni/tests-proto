<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="wide-block">
	<h1>
		<span>${evaluation.test.intitule}</span>
	</h1>
	<p class="test-home setup">
		Vous aller passer le test <b>${evaluation.test.intitule}</b> dans quelques instants, vous disposez de <b>${evaluation.test.duree} minutes</b>
		pour effectuer ce test. Le chronomètre démarrera au moment où vous cliquerez sur le bouton <b>Démarrer le test</b>
		ci-dessous.<br/><br/>
		Pour chaque question, vous pouvez cocher plusieurs réponses.
		<br/>
		Vous pourrez revenir sur une question (que vous y ayez répondu ou non)  en utilisant la barre de navigation sous les choix de réponses.<br/>
		Quand vous répondez à une question, le bouton correspondant à celle-ci dans la barre de navigation s'affiche en violet. Vous avez donc la possibilité
		de repérer facilement les questions auxquelles vous n'avez pas encore répondues.
		
		<br/><br/>
		Pour valider définitivement le test, il faut être sur la page de la dernière question et cliquez sur "Valider le test", une fois cette opération effectuée, vous ne
		pourrez plus revenir en arrière.
		
		<c:if test="${evaluation.test.additionalInfo ne ''}">
		<br/><br/>${evaluation.test.additionalInfo}
		</c:if>
		
		<c:url value="/tests/question" var="testUrl">
			<c:param name="test" value="${evaluation.id}"/>
			<c:param name="question" value="1"/>
		</c:url>
		<a href="${testUrl}" class="button"><spring:message code="link.label.start.test" /></a>
		<br/><br/><br/>
	</p>
</div>