<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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

				<%@include file="../common/msg.jsp"%>

				<button class="btn btn-success" style="margin-bottom: 10px"
					id="btnAdd">Addd</button>

				<table class="table table-bordered table-striped table-hove">
					<tr>
						<th>STT</th>
						<th width="20%">Name</th>
						<th>Author</th>
						<th>Publisher</th>
						<th>Category</th>
						<th>Quantity</th>
						<th>Price</th>
						<th>Discount</th>
						<th width="15%">Action</th>
					</tr>
					<c:forEach items="${listBook}" var="i" varStatus="loop"
						begin="${startIndex}" end="${endIndex}">
						<tr>
							<td>${loop.index +1}</td>
							<td>${i.name }</td>
							<td>${i.author.name }</td>
							<td>${i.publisher.name }</td>
							<td><c:forEach items="${i.category}" var="ii">
									${ii.name}
								</c:forEach></td>
							<td>${i.availableQuantity }</td>
							<td>${i.price }</td>
							<td>${i.discount }</td>
							<td><button class="btn btn-success btn-modal" data-toggle="modal"
								data-target="#myModal" data-book-id="${i.id }" data-book-quantity="${i.availableQuantity}">Import</button>
								<a class="btn btn-primary" href="/bookstore/book/edit/${i.id}">Edit</a>
								<a class="btn btn-danger" href="/bookstore/book/delete/${i.id}"
								onclick="return confirm('Are you sure?')">Delete</a></td>
						</tr>
					</c:forEach>
					<tr>

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

		<script type="text/javascript">
			$("#btnAdd").click(function() {
				window.open('/bookstore/book/add', '_self');
			});

			//triggered when modal is about to be shown			
			$('.btn-modal').each(function(index){
				this.addEventListener("click", function(e) {
					var idBook = $(this).data("book-id");
					var quantity = $(this).data("book-quantity");
					$("#bookId").val(idBook);
					$("input[name='quantity']").val(quantity);					
				}, false);
			});
			
		</script>
	</div>
</body>
</html>