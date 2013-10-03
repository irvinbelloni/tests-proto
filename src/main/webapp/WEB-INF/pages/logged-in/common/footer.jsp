<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<footer>
	<sec:authorize access="hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_CONSULTANT')">
		<a href="<c:url value="/admin/test/home" />">Tests</a>&nbsp;&nbsp;
		<a href="<c:url value="/admin/candidates" />">Candidats</a>&nbsp;&nbsp;
		<a href="<c:url value="/admin/resultats" />">R&eacute;sultats</a>
		
		<sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
			&nbsp;&nbsp;		
			<a href="<c:url value="/admin/administrators" />">Administrateurs</a>
		</sec:authorize>
		<br/>
		<br/>	
	</sec:authorize>
	&copy; OSSIA CONSEIL 2013<br/>
	<sec:authorize access="hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_CONSULTANT')">
		Icons courtesy of <a href="http://icons8.com/license/">Icons8</a>
	</sec:authorize>
 </footer>