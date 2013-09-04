<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<article>
	<h2>Test Java/J2EE Interm&eacute;diaire &nbsp;&nbsp;&nbsp; <span><spring:message code="text.test.question" /></span> 5 <span>/ 15</span></h2>
	<div id="question">			
		<p>
			Qu'affiche le code java suivant?
			
			<script type="syntaxhighlighter" class="brush:java">
				<![CDATA[void foo(String input) {System.out.println ("AA");}]]>
			</script>
		</p>
	</div>
	<div id="answer">
		<form>		
			<input type="radio" name="question5" value="answer1" /> "AA"<br/>
			<input type="radio" name="question5" value="answer2" /> "BB"<br/>
			<input type="radio" name="question5" value="answer3" /> "CC"<br/>
			<input type="radio" name="question5" value="answer4" /> "DD"<br/>
			<input type="radio" name="question5" value="answer5" /> Aucun des choix ci-dessus
			
			<div class="submit">	
				<a href="" class="previous"><spring:message code="link.label.test.stop" /></a>				
				<input type="submit" value="<spring:message code="link.label.test.question.next" />" />
				<a href="" class="previous" style="float: right"><spring:message code="link.label.test.question.previous" /></a>
				
				<div class="clearer"></div>
			</div>
		</form>
	</div>
</article>