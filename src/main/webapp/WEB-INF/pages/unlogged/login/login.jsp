<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form class="login" name='f' action="<c:url value='j_spring_security_check' />" method='POST'>
	<c:if test="${not empty error}">
		<p class="errorblock">
			<spring:message code="form.error.bad.credentials" />
		</p>
	</c:if>
	<table>
		<tr>
			<td style="width: 120px"><spring:message code="form.login.login" /></td>
			<td><input type='text' class="text" name='j_username' value=''></td>
		</tr>
		<tr>
			<td><spring:message code="form.login.password" /></td>
			<td><input type='password' class="text" name='j_password' /></td>
		</tr>
		<tr>
			<td colspan='2'><input name="submit" type="submit" class="submit-button" value="<spring:message code="form.login.connection" />" style="margin: 20px 15px 0 30px" /></td>
		</tr>
	</table>
</form>