<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Order management</title>
<!-- MAIN CSS -->
<%@include file="../common/style.jsp"%>

<style type="text/css">
.right-border {
	border-right: solid 2px;
}

.modal-body .col-md-6 label {
	font-weight: bold;
	margin-right: 10px;
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

				<table class="table table-bordered table-striped table-hove">
					<tr>
						<th>STT</th>
						<th>Order number</th>
						<th>Customer number</th>
						<th>Customer name</th>
						<th>Payment method</th>
						<th>Date Order</th>
						<th>Status</th>
						<th>Detail</th>
					</tr>
					<c:forEach items="${listOrder}" var="i" varStatus="loop">
						<tr>
							<td>${loop.index+1}</td>
							<td>${i.id}</td>
							<td>${i.client.id }</td>
							<td>${i.client.name }</td>
							<td>${i.paymentMethod}</td>
							<td>${i.dateCreate}</td>
							<td>${i.status}</td>
							<td>
								<button class="btn btn-info btn-modal" data-toggle="modal"
									data-target="#myModal" data-order-id="${i.id}">More
									Info</button>
							</td>
						</tr>
					</c:forEach>
				</table>

				<!-- The Modal -->
				<div class="modal fade" id="myModal">
					<div class="modal-dialog modal-lg modal-dialog-centered">
						<div class="modal-content">

							<!-- Modal Header -->
							<div class="modal-header">
								<h4 class="modal-title">Order Information</h4>
								<button type="button" class="close" data-dismiss="modal">&times;</button>
							</div>

							<!-- Modal body -->
							<div class="modal-body">
								<div class="row">
									<div class="col-md-6 right-border">
										<p style="color: #84b3ff; font-size: 30px; font-family:">Customer
											Information</p>
										<div class="customer-info">
											<label>Customer number : </label> <span></span><br> <label>Customer
												name : </label> <span></span><br> <label>Customer email
												: </label> <span></span>
										</div>
									</div>
									<div class="col-md-6">
										<p style="color: #84b3ff; font-size: 30px; font-family:">Shipping
											Information</p>
										<div class="shipping-info">
											<label>Shipping number : </label><span></span><br> <label>Receiver
												: </label><span></span> <br> <label>Address : </label><span></span><br>
											<label>Zip code : </label><span></span><br> <label>Status
												: </label><span></span>
										</div>
									</div>
								</div>
								<div class="row mt-4">
									<div class="container">
										<table class="table table-hover table-order">
											<thead>
												<tr>
													<th width="10%">No</th>
													<th width="20%">Book number</th>
													<th width="40%">Book name</th>
													<th width="10%">Discount</th>
													<th width="20%">Price</th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
										<table width="100%" class="table">
											<tr>
												<td width="10%"></td>
												<td width="20%"></td>
												<td width="40%"></td>
												<td width="10%"></td>
												<td width="20%" id="total"></td>
											</tr>
										</table>
									</div>
								</div>
							</div>

							<!-- Modal footer -->
							<div class="modal-footer">
								<form action="/bookstore//order/changeStatus" method="get">
									<input type="number" value="0" name="orderId" style="display: none" id="orderIdChange"> <input
										type="submit" class="btn btn-success" value="Change status">
								</form>
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">Close</button>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- MAIN SCRIPT -->
	<%@include file="../common/lib.jsp"%>

	<script type="text/javascript">
		var rootUrl = "http://localhost:8080/bookstore/";
		
		//triggered when modal is about to be shown			
		$('.btn-modal').each(function(index) {
			this.addEventListener("click", function(e) {
				var idOrder = $(this).data("order-id");

				$("#orderIdChange").val(idOrder);
				
				$.ajax({
					type : 'GET',
					url : rootUrl + 'get_list_order_json',
					cache : true,
					success : function(data) {
						orderCallBack(data, idOrder);
					}
				});

				$.ajax({
					type : 'GET',
					url : rootUrl + '/get_listbook_byorderid/' + idOrder,
					cache : true,
					success : function(data) {
						bookOrderCallBack(data);
					}
				});
				
			}, false);
		});

		function orderCallBack(data, idOrder) {
			//console.log("id order " + idOrder);
			var order = data.find(function(idOrder) {
				return id = idOrder;
			});
			//console.log(order);

			//get info from object
			var customerNumber = order.client.id;
			var customerName = order.client.name;
			var customerEmail = order.client.email;

			var customer_info = $(".customer-info span");
			customer_info[0].textContent = customerNumber;
			customer_info[1].textContent = customerName;
			customer_info[2].textContent = customerEmail;

			var shippingNumber = order.shippingInfo.id;
			var receiver = order.shippingInfo.receiverName;
			var address = order.shippingInfo.number + ', '
					+ order.shippingInfo.district + ', '
					+ order.shippingInfo.city;
			var zipcode = order.shippingInfo.zipcode;

			var shipping_info = $(".shipping-info span")
			shipping_info[0].textContent = shippingNumber;
			shipping_info[1].textContent = receiver;
			shipping_info[2].textContent = address;
			shipping_info[3].textContent = zipcode;
			shipping_info[4].textContent = order.status;
		};

		
		
		function bookOrderCallBack(data){
			console.log(data);
			$(".table-order tbody").empty();
			var stno = 0;
			var total = 0;
			data.forEach(function(item, index){
				stno += 1;
				let discount = item.bookInfo.discount/100;				
				total += (1-discount) * item.bookInfo.price;
				var row = "<tr>";
				row += "<td>" + stno + "</td>";
				row += "<td>" + item.id + "</td>"
				row += "<td>" + item.bookInfo.name + "</td>";
				row += "<td>" + item.bookInfo.discount + "</td>";
				row += "<td>" + item.bookInfo.price + "</td>"
				row += "</tr>"
				$(".table-order tbody").append(row);	
			});
			$("#total").html(total);					
		}
	</script>
</body>
</html>