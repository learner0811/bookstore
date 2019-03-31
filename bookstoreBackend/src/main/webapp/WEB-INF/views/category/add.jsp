<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Category management</title>
<!-- MAIN CSS -->
<%@include file="../common/style.jsp"%>
</head>
<body>
	<div class="d-flex" id="wrapper">
		<!-- Sidebar -->
		<%@include file="../common/sidebar.jsp"%>

		<div id="page-content-wrapper">
			<!-- Header -->
			<%@include file="../common/header.jsp"%>

			<!--Breadcumb  -->
			<%@include file="../common/breadcum.jsp"%>

			<!-- main content -->

			<div class="container">
				<c:if test="${not empty errorMsg}">
					<div class="alert alert-danger alert-dismissible">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<c:out value="${errorMsg}"></c:out>
					</div>
				</c:if>
				<form class="form-horizontal" action="/bookstore/category/add"
					method="post">
					<div class="form-group">
						<label for="name" class="col-md-3">Category Name</label>
						<div class="col-md-6">
							<input type="text" class="form-control" id="name" name="name"
								required="required">
						</div>
					</div>
					<div class="form-group col-md-3">
						<button type="submit" class="btn btn-success">Add</button>
					</div>
				</form>
			</div>
		</div>

		<!-- MAIN SCRIPT -->
		<%@include file="../common/lib.jsp"%>
	</div>
</body>
</html>