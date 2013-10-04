<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="wide-block">
	<h1>
		<span>${evaluation.test.intitule}</span>
	</h1>
	<p class="test-home setup">
		Vous aller passer le test <b>${evaluation.test.intitule}</b> dans quelques instants, vous disposez de <b>${evaluation.test.duree} minutes</b>
		pour effectuer ce test. Le chronom�tre d�marrera au moment o� vous cliquerez sur le bouton <b>D�marrer le test</b>
		ci-dessous.<br/><br/>
		Pour chaque question, vous pouvez cocher plusieurs r�ponses.
		<br/>
		Vous pourrez revenir sur une question (que vous y ayez r�pondu ou non)  en utilisant la barre de navigation sous les choix de r�ponses.<br/>
		Quand vous r�pondez � une question, le bouton correspondant � celle-ci dans la barre de navigation s'affiche en violet. Vous avez donc la possibilit�
		de rep�rer facilement les questions auxquelles vous n'avez pas encore r�pondues.
		
		<br/><br/>
		Pour valider d�finitivement le test, il faut �tre sur la page de la derni�re question et cliquez sur "Valider le test", une fois cette op�ration effectu�e, vous ne
		pourrez plus revenir en arri�re.
		
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