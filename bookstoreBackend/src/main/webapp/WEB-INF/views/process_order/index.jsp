<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Order management</title>
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
				
				<table class="table table-bordered table-striped table-hove">
					<tr>
						<th>STT</th>
						<th width="20%">Order code</th>
						<th>Status</th>
						<th>Publisher</th>
						<th>Category</th>
						<th>Quantity</th>
						<th>Price</th>
						<th>Discount</th>
						<th width="15%">Action</th>
					</tr>									
				</table>


				<!-- Pagination -->
				<ul class="pagination">
					<c:forEach begin="1" end="${totalPage}" varStatus="loop">
						<c:choose>
							<c:when test="${loop.index == page}">
								<li class="page-item active"><a class="page-link"
									href="/bookstore/book/index?page=${loop.index}">${loop.index}</a></li>
							</c:when>
							<c:otherwise>
								<li class="page-item"><a class="page-link"
									href="/bookstore/book/index?page=${loop.index}">${loop.index}</a></li>
							</c:otherwise>
						</c:choose>

					</c:forEach>
				</ul>
			</div>
		</div>


		<!-- The Modal -->
		<div class="modal" id="myModal">
			<div class="modal-dialog modal-sm modal-dialog-centered">
				<div class="modal-content">
					<form action="/bookstore/book/updateQuantity">
						<!-- Modal Header -->
						<div class="modal-header">
							<h4 class="modal-title">Book Quanity</h4>
							<button type="button" class="close" data-dismiss="modal">&times;</button>
						</div>

						<!-- Modal body -->
						<div class="modal-body">
							<input type="hidden" name="id" id="bookId"> <label>Quantity</label>
							<input type="number" min="0" max="10" name="quantity"
								class="quantity-input">
						</div>

						<!-- Modal footer -->
						<div class="modal-footer">
							<input type="submit" value="Ok" class="btn btn-success">
							<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
						</div>
					</form>
				</div>
			</div>
		</div>

		<!-- MAIN SCRIPT -->
		<%@include file="../common/lib.jsp"%>
		
	</div>
</body>
</html>