<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="q" uri="/WEB-INF/questionTaglib.tld"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="time" uri="/WEB-INF/timeTaglib.tld"%>

<div class="wide-block">
	<h1>
		<a href="<c:url value="/admin/resultats"/>"><spring:message code="link.label.back.to.result.list"/></a>
		<span>${result.test.intitule}</span> - passé par <span>${result.profil.prenom} ${result.profil.nom}</span> 
	</h1>
	
	<jsp:include page="detail-test.jsp"></jsp:include>
</div>
<script>
$(document).ready(function () { 
	$("#result-menu").sticky({topSpacing:0});	
	$("#result-menu").css("width", $("#main-actions").css("width") );
});

</script>