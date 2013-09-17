<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="side-form">
	<h2><spring:message code="text.side.form.title.administrator.add" /></h2>
	
	<c:url value="/admin/profile/add-or-edit" var="addOrEditUrl">
		<c:param name="origin" value="administrators"/>
	</c:url>
	<form:form method="post" action="${addOrEditUrl}" commandName="profil" id="profil-form">
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
			<label><spring:message code="form.user.email"/>*</label>
			<form:input path="email" />
			<form:errors path="email" class="error" />
		</div>
		
		<div>
			<label><spring:message code="form.user.login"/>*</label>
			<form:input path="login" />
			<form:errors path="login" class="error" />
		</div>
		
		<div>
			<label><spring:message code="form.user.password"/>*</label>
			<form:input path="pass" />
			<form:errors path="pass" class="error" />
		</div>
					
		<div class="last-element">	
			<label><spring:message code="form.user.active"/></label>
			<form:radiobutton path="active" value="1"/><span class="radio"><spring:message code="text.yes" /></span> 
			<form:radiobutton path="active" value="0"/><span class="radio"><spring:message code="text.no" /></span>
			<div class="clear-both"></div>
		</div>
					
		<a class="back-to-add-form" href="#" onclick="backToAddProfil(); return false;"><spring:message code="link.label.cancel.quick.update" /></a>
		<input type="submit" class="submit-button small" id="submit-button" value="<spring:message code="form.user.add"/>" />
	</form:form>
</div>

<div class="left-list">
	<h2>
		<span>
			<spring:message code="text.sort.by"/>:&nbsp;&nbsp;
			<c:set var="sortOn" value="" />
			<c:set var="dir" value="asc" />
			<c:if test="${sortingInfo.sortingField eq 'prenom'}">
				<c:set var="sortOn" value="sort-on" />
				<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
					<c:set var="dir" value="desc" />
				</c:if>
			</c:if>		
			<c:url value="/admin/administrators" var="sortUrl">
				<c:param name="sort" value="prenom"/>
				<c:param name="direction" value="${dir}"/>
			</c:url>	
			<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.firstname"/></a>&nbsp;&nbsp;-&nbsp;
			
			<c:set var="sortOn" value="" />
			<c:set var="dir" value="asc" />
			<c:if test="${sortingInfo.sortingField eq 'nom'}">
				<c:set var="sortOn" value="sort-on" />
				<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
					<c:set var="dir" value="desc" />
				</c:if>
			</c:if>
			<c:url value="/admin/administrators" var="sortUrl">
				<c:param name="sort" value="nom"/>
				<c:param name="direction" value="${dir}"/>
			</c:url>
			<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.name"/></a>&nbsp;&nbsp;-&nbsp;
			
			<c:set var="sortOn" value="" />
			<c:set var="dir" value="asc" />
			<c:if test="${sortingInfo.sortingField eq 'id'}">
				<c:set var="sortOn" value="sort-on" />
				<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
					<c:set var="dir" value="desc" />
				</c:if>
			</c:if>
			<c:url value="/admin/administrators" var="sortUrl">
				<c:param name="sort" value="id"/>
				<c:param name="direction" value="${dir}"/>
			</c:url>
			<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.creation"/></a>&nbsp;&nbsp;-&nbsp;
			
			<c:set var="sortOn" value="" />
			<c:set var="dir" value="asc" />
			<c:if test="${sortingInfo.sortingField eq 'dateActivation'}">
				<c:set var="sortOn" value="sort-on" />
				<c:if test="${sortingInfo.sortingDirection eq 'asc'}">
					<c:set var="dir" value="desc" />
				</c:if>
			</c:if>
			<c:url value="/admin/administrators" var="sortUrl">
				<c:param name="sort" value="dateActivation"/>
				<c:param name="direction" value="${dir}"/>
			</c:url>
			<a class="${sortOn}" href="${sortUrl}"><spring:message code="text.sort.by.activation.date"/></a>
			
		</span>
	</h2>
	
	<c:forEach items="${administrators}" var="administrator">	
		<div class="list-item">
			<p class="actions">
				<a href="#" class="actions-down"></a>
				<span class="sub-actions">
					<c:url value="/admin/profile/delete" var="deleteUrl">
						<c:param name="profile" value="${administrator.id}"/>
						<c:param name="origin" value="administrators"/>
					</c:url>
					<a href="#" onclick="deleteProfil(${administrator.id}, '${administrator.prenom}', '${administrator.nom}', '${deleteUrl}', '<spring:message code="dialog.title.delete.administrator"/>'); return false;" class="action delete"><spring:message code="link.label.profil.delete"/></a>
					<a href="#" onclick="editProfil(${administrator.id}, '${administrator.prenom}', '${administrator.nom}', '${administrator.email}', '${administrator.login}', '${administrator.pass}', '${candidate.dateActivation}', '${candidate.dateActivation}'); return false;" class="action edit">
						<spring:message code="link.label.profil.edit" />
					</a>
					<c:url value="/admin/profile/activate" var="activateUrl">
						<c:param name="profile" value="${administrator.id}"/>
						<c:param name="origin" value="administrators"/>
					</c:url>
					<a href="${activateUrl}" class="action activate">
						<c:if test="${administrator.enabled}"><spring:message code="link.label.administrator.desactivate" /></c:if>
						<c:if test="${!administrator.enabled}"><spring:message code="link.label.administrator.activate" /></c:if>
					</a>
					<c:url value="/admin/administrator" var="detailUrl">
						<c:param name="administrator" value="${administrator.id}"/>
					</c:url>
					<!-- a href="${detailUrl}" class="action detail"><spring:message code="link.label.administrator.detail"/></a -->
				</span>		
			</p>
			<p class="main">
				<!-- a href="${detailUrl}" -->
					<c:if test="${sortingInfo.sortingField eq 'nom'}">${administrator.nom} ${administrator.prenom}</c:if>
					<c:if test="${sortingInfo.sortingField ne 'nom'}">${administrator.prenom} ${administrator.nom}</c:if>
				<!-- /a -->
				<br/>
				<span>${administrator.email}</span>
			</p>
			<p class="detail">
				<span class="label"><spring:message code="text.list.login"/>:</span> ${administrator.login}<br/>
				<span class="label"><spring:message code="text.list.active"/>:</span> 
				<c:if test="${administrator.enabled}"><spring:message code="text.yes" /></c:if>
				<c:if test="${!administrator.enabled}"><spring:message code="text.no" /></c:if>
			</p>
			<div class="clear-left"></div>		
		</div>
	</c:forEach>
</div>

<div class="clear-both"></div>

<script>
$(document).ready(function() {
	textAddProfil = "<spring:message code="text.side.form.title.administrator.add"/>";
	textEditProfil = "<spring:message code="text.side.form.title.administrator.edit"/>";
	dialogTextDeleteProfil = "<spring:message code="dialog.text.delete.administrator"/>";
	
	if ($("#mode").val() == "edit") {
		$("#submit-button").val("Modifier");
		$(".side-form h2").html(textEditProfil);	
		$(".back-to-add-form").show();
	}
	
	<jsp:include page="../notify-action.jsp"/>
});
</script>