<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="side-form">
	<h2><spring:message code="text.side.form.title.candidate.add"/></h2>
	
	<form:form method="post" action="${pageContext.request.contextPath}/admin/candidates" commandName="profil">
		<form:hidden path="id" />
		<form:hidden path="mode" />
	
		<div>
			<label for="prenom"><spring:message code="form.user.firstname"/>*</label>
			<form:input path="prenom" />
			<form:errors path="prenom" class="error" />
		</div>
		
		<div>
			<label><spring:message code="form.user.name"/>*</label>
			<form:input path="nom" />
			<form:errors path="nom" class="error" />
		</div>
		
		<div>
			<label><spring:message code="form.user.login"/>*</label>
			<form:input path="login" />
			<form:errors path="login" class="error" />
		</div>
			
		<div>
			<label><spring:message code="form.user.email"/>*</label>
			<form:input path="email" />
			<form:errors path="email" class="error" />
		</div>
		
		<div>
			<label><spring:message code="form.user.password"/>*</label>
			<form:input path="pass" />
			<form:errors path="pass" class="error" />
		</div>
			
		<div>	
			<label><spring:message code="form.user.start"/>*</label>
			<form:input path="dateDebut" class="datepicker"/>
			<form:errors path="dateDebut" class="error" />
		</div>
			
		<div>
			<label><spring:message code="form.user.end"/></label>
			<form:input path="dateFin" class="datepicker"/>
			<form:errors path="dateFin" class="error" />
		</div>
		
		<input type="submit" class="submit-button small" value="<spring:message code="form.user.add"/>" />
	</form:form>
</div>
	

<div class="list">
	<h2><spring:message code="text.list.title.candidates"/></h2>
	
	<c:forEach items="${candidates}" var="candidate">	
		<div class="list-item">
			<p class="actions">
				<a href="#" class="action delete"></a>
				<a href="#" onclick="editProfil(${administrator.admin}, ${administrator.id}, '${administrator.prenom}', '${administrator.nom}', '${administrator.email}', '${administrator.login}'); return false;" class="action edit"></a>
			</p>
			<p class="main">${candidate.prenom} ${candidate.nom}</p>
			<p class="detail">
				<span class="label">Email:</span> ${candidate.email}<br/>
				<span class="label">Login:</span> ${candidate.login}<br/>
				<span class="label">Actif:</span> 
				<c:if test="${candidate.enabled}">
					<spring:message code="text.yes" />
					<c:if test="${candidate.dateFin ne null}">
						, <spring:message code="text.up.to" /> <fmt:formatDate value="${candidate.dateFin}" pattern="dd/MM/yyyy" />
					</c:if>
				</c:if>
				<c:if test="${!candidate.enabled}"><spring:message code="text.no" /></c:if>
			</p>
			<div class="clear-left"></div>		
		</div>
	</c:forEach>
</div>

<div class="clearer"></div>

<script>
$(document).ready(function() {
	textEditCandidate = "<spring:message code="text.side.form.title.candidate.edit"/>";
});
</script>