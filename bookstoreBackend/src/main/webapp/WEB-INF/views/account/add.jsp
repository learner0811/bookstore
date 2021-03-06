<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Category management</title>
<!-- MAIN CSS -->
<%@include file="../common/style.jsp"%>
<style type="text/css">
.row {
	margin: 15px 0;
}
</style>
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
				<%@include file="../common/msg.jsp"%>
				<form class="form-horizontal" action="/bookstore/account/add"
					method="post">
					<div class="form-group">
						<div class="row">
							<div class="col-md-6">
								<label for="name">staff name</label> <input type="text"
									class="form-control" name="name" required="required">
							</div>
							<div class="col-md-6">
								<label for="name">Email</label> <input type="email"
									class="form-control" name="email" required="required">
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<label for="name">User name</label> <input type="text"
									class="form-control" name="account.username"
									required="required">
							</div>
							<div class="col-md-6">
								<label for="name">Password</label> <input type="password"
									class="form-control" name="account.password" required="required">
							</div>
						</div>
						<div class="row">
							<div class="col-md-3">
								<button type="submit" class="btn btn-success">Add</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>

		<!-- MAIN SCRIPT -->
		<%@include file="../common/lib.jsp"%>
	</div>
</body>
</html>