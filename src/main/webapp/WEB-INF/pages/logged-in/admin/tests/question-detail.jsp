<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>

<div id="add" class="propositionReponse">
	
	<form:form modelAttribute="propositionReponseCreation">
		<form:hidden path="id" />

		<form:label path="valeur">
			<spring:message code="text.admin.propositionReponse.page.valeur" />
		</form:label>
		<form:input path="valeur" size="25" maxlength="255" />
		<form:errors path="valeur" cssClass="error" />

		<form:label path="valeur">
			<spring:message code="text.admin.propositionReponse.page.valeur" />
		</form:label>
		<form:select path="propositionCorrecte">
			<form:option value="true">
				<spring:message code="text.admin.propositionReponse.page.propositionCorrecte" />
			</form:option>
			<form:option value="false">
				<spring:message code="text.admin.propositionReponse.page.propositionCorrecte" />
			</form:option>
		</form:select>
		<form:errors path="propositionCorrecte" cssClass="error" />

		<input type="submit" value="Ajouter une Valeur" />
		<br />
	</form:form>
	
</div>

<div class="list">
		
</div>

<div class="clearer"></div>