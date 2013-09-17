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
		Mettre les autres consignes ici.
		
		<a href="#" class="button"><spring:message code="link.label.start.test" /></a>
		<br/><br/><br/>
	</p>
</div>