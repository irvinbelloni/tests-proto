<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><tiles:insertAttribute name="title" ignore="true" /></title>
				
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.2.0.3.js"></script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/syntax-highlighter/shCore.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/syntax-highlighter/shBrushJava.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/syntax-highlighter/shBrushSql.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/syntax-highlighter/shBrushXml.js"></script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/ossia.control.js"></script>		
		
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/syntax-highlighter/shCore.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/syntax-highlighter/shCoreEclipse.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/ossia.css" />
	</head>
	<body>
		<tiles:insertAttribute name="header" />
        <tiles:insertAttribute name="body" />
       	<tiles:insertAttribute name="footer" />
	</body>
</html>