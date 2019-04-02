<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book management</title>
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
			
				<%@include file="../common/msg.jsp" %>
				
				<button class="btn btn-success" style="margin-bottom: 10px"
					id="btnAdd">Addd</button>

				<table class="table table-bordered table-striped table-hove">
					<tr>
						<th>a</th>
						<th>b</th>
						<th>c</th>
						<th>d</th>
						<th>Action</th>
					</tr>
					<tr>
						<td>1</td>
						<td>1</td>
						<td>1</td>
						<td>1</td>
						<td>
							<button class="btn btn-primary">Edit</button>
							<button class="btn btn-danger">Xoa</button>
						</td>
					</tr>
					<tr>
						<td>2</td>
						<td>2</td>
						<td>2</td>
						<td>2</td>
						<td>
							<button class="btn btn-primary">Edit</button>
							<button class="btn btn-danger">Xoa</button>
						</td>
					</tr>
					<tr>
						<td>1</td>
						<td>1</td>
						<td>1</td>
						<td>1</td>
						<td>
							<button class="btn btn-primary">Edit</button>
							<button class="btn btn-danger">Xoa</button>
						</td>
					</tr>
					<tr>
						<td>1</td>
						<td>1</td>
						<td>1</td>
						<td>1</td>
						<td>
							<button class="btn btn-primary">Edit</button>
							<button class="btn btn-danger">Xoa</button>
						</td>
					</tr>
				</table>
			</div>

			<!-- Pagination -->
			<%@include file="../common/pagination.jsp"%>
		</div>

		<!-- MAIN SCRIPT -->
		<%@include file="../common/lib.jsp"%>

		<script type="text/javascript">
			$("#btnAdd").click(function() {
				window.open('/bookstore/book/add', '_self');
			});
		</script>
	</div>
</body>
</html>