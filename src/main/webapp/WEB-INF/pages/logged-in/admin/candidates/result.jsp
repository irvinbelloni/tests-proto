<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="wide-block">
	<h1>
		<a href="<c:url value="/admin/candidates"/>"><spring:message code="link.label.back.to.candidate.list"/></a>
		<a href="<c:url value="/admin/candidate?candidate=${profil.id}"/>"><spring:message code="link.label.back.to.candidate.detail"/></a>
		<span>${profil.prenom} ${profil.nom}</span> > r&eacute;sultat test <span>${result.test.intitule}</span>
	</h1>
	
	<jsp:include page="../resultats/detail-test.jsp"></jsp:include>
</div>
<script>
$(document).ready(function () { 
	$("#result-menu").sticky({topSpacing:0});	
	$("#result-menu").css("width", $("#main-actions").css("width") );
});

</script>