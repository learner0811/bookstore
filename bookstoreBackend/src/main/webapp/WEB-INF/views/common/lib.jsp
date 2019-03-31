<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"
	src='<c:url value = "/resources/common/js/jquery-3.3.1.js"/>'></script>
<script type="text/javascript"
	src='<c:url value = "/resources/common/js/bootstrap.min.js"/>'></script>
<!-- Togle menu script -->
<script type="text/javascript">
	$("#menu-toggle").click(function(e) {
		e.preventDefault();
		$("#wrapper").toggleClass("toggled");
	});
</script>