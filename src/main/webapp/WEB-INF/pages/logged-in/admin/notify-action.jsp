<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${lastAction ne null}">	
	var n = noty({text: "${lastAction}", type: "success"});
</c:if>

<c:if test="${errorAction ne null}">	
	var n = noty({text: "${errorAction}", type: "error"});
</c:if>

<c:if test="${warningAction ne null}">	
	var n = noty({text: "${warningAction}", type: "warning"});
</c:if>