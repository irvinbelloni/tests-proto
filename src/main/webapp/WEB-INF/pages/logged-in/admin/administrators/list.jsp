<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="side-form">
	<h2><spring:message code="text.side.form.title.administrator.add"/></h2>
	
	<form method="post" action="" enctype="multipart/form-data" id="">
		<label><spring:message code="form.user.firstname"/>*</label>
		<input type="text" id="prenom"/>
		
		<label><spring:message code="form.user.name"/>*</label>
		<input type="text" id="nom"/>
		
		<label><spring:message code="form.user.login"/>*</label>
		<input type="text" id="login"/>
		
		<label><spring:message code="form.user.email"/>*</label>
		<input type="text" id="email"/>
				
		<label><spring:message code="form.user.start"/>*</label>
		<input type="text" id="dateDebut" class="datepicker"/>
		
		<label><spring:message code="form.user.end"/></label>
		<input type="text" id="dateFin" class="datepicker"/>
		
		<input type="submit" class="submit-button small" id="profilSubmit" value="<spring:message code="form.user.add"/>" />
	</form>
</div>
	

<div class="list">
	<h2><spring:message code="text.list.title.administrators"/></h2>
	
	<c:forEach items="${administrators}" var="administrator">	
		<div class="list-item">
			<p class="actions">
				<a href="#" class="action delete"></a>
				<a href="#" onclick="editProfil('admin', '${administrator.prenom}', '${administrator.nom}', '${administrator.email}', '${administrator.login}'); return false;" class="action edit"></a>
			</p>
			<p class="main">${administrator.prenom} ${administrator.nom}</p>
			<p class="detail">
				<span class="label">Email:</span> ${administrator.email}<br/>
				<span class="label">Login:</span> ${administrator.login}<br/>
				<span class="label">Actif:</span> 
				<c:if test="${administrator.enabled}">
					<spring:message code="text.yes" />
					<c:if test="${administrator.dateFin ne null}">
						, <spring:message code="text.up.to" /> <fmt:formatDate value="${administrator.dateFin}" pattern="dd/MM/yyyy" />
					</c:if>
				</c:if>
				<c:if test="${!administrator.enabled}"><spring:message code="text.no" /></c:if>
			</p>
			<div class="clear-left"></div>		
		</div>
	</c:forEach>
</div>

<div class="clearer"></div>

<script>
$(document).ready(function() {
	textAddAdministrator = "<spring:message code="text.side.form.title.administrator.add"/>";
	textEditAdministrator = "<spring:message code="text.side.form.title.administrator.edit"/>";
});
 </script>