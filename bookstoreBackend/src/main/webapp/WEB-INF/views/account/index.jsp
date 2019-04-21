<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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

				<%@include file="../common/msg.jsp"%>
				<button class="btn btn-success" style="margin-bottom: 10px"
					id="btnAdd">Addd</button>

				<table class="table table-bordered table-striped table-hove">
					<tr>
						<th>STT</th>
						<th>Name</th>
						<th>User name</th>
						<th>Email</th>
						<th>Role</th>
						<th>Action</th>
					</tr>
					<c:forEach items="${listStaff}" varStatus="loop" var="i">
						<tr>
							<td>${loop.index+1}</td>
							<td>${i.name}</td>
							<td>${i.account.username}</td>
							<td>${i.email}</td>
							<td>${i.role}</td>
							<td><a class="btn btn-danger"
								href="/bookstore/account/delete/${i.account.id}"
								onclick="return confirm('Are you sure?')">Xoa</a></td>
						</tr>
					</c:forEach>
				</table>
			</div>

		</div>

		<!-- MAIN SCRIPT -->
		<%@include file="../common/lib.jsp"%>
		<script type="text/javascript">
			$("#btnAdd").click(function() {
				window.open('/bookstore/account/add', '_self');
			});
		</script>
	</div>
</body>
</html>