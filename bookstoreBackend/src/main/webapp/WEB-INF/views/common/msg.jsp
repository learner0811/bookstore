<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty errorMsg}">
	<div class="alert alert-danger alert-dismissible">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<c:out value="${errorMsg}"></c:out>
	</div>
</c:if>
<c:if test="${not empty successMsg}">
	<div class="alert alert-success alert-dismissible">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<c:out value="${successMsg}"></c:out>
	</div>
</c:if>