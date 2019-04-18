<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Book management</title>
<!-- MAIN CSS -->
<%@include file="../common/style.jsp"%>

<link href="/bookstore/resources/chosen/chosen.css" rel="stylesheet">

<style type="text/css">
.row {
	margin: 15px 0;
}

label {
	margin-bottom: 0;
	margin-left: 1px;
}

.chosen-container {
	width: 100% !important;
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
				<%@include file="../common/msg.jsp" %>
				<form:form enctype="multipart/form-data" method="post"
					action="/bookstore/book/add" modelAttribute="book" acceptCharset="UTF-8">
					<div class="row">
						<div class="form-group col-md-6">
							<label>Name</label> <input type="text" class="form-control"
								name="name">
						</div>
						<div class="form-group col-md-6">
							<label style="display: block">Category</label> <select
								data-placeholder="Select category" multiple
								class="chosen-select" tabindex="8" name="catList" required="required">
								<c:forEach items="${listCategory}" var="i">
									<option value=${i.id }>${i.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="row">
						<div class="form-group col-md-6">
							<label>Author</label> <select class="form-control"
								name="author.id">
								<option value="0">None</option>
								<c:forEach items="${listAuthor}" var="i">
									<option value=${i.id }>${i.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group col-md-6">
							<label>Publisher</label> <select class="form-control"
								name="publisher.id">
								<option value="0">None</option>
								<c:forEach items="${listPublisher}" var="i">
									<option value="${i.id}">${i.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-6">
							<label>Price</label> <input type="text" class="form-control"
								name="price">
						</div>
						<div class="form-group col-md-6">
							<label>Discount</label> <input type="text" class="form-control"
								name="discount" value="0">
						</div>
					</div>					
					<div class="row">
						<div class="form-group col-md-6">
							<label>Image</label>
							<div class="custom-file">
								<input type="file" class="custom-file-input" id="customFile"
									accept="image/x-png,image/gif,image/jpeg" name="imageFile"
									required="required"> <label class="custom-file-label"
									for="customFile">Choose file</label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-12">
							<label for="comment">Description:</label>
							<textarea class="form-control" rows="5" id="comment"
								name="description"></textarea>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<button type="submit" class="btn btn-info">Submit</button>
						</div>
					</div>
				</form:form>


			</div>
		</div>
		<!-- MAIN SCRIPT -->
		<%@include file="../common/lib.jsp"%>
		<script type="text/javascript"
			src='/bookstore/resources/chosen/chosen.jquery.js'></script>
		<script type="text/javascript"
			src='/bookstore/resources/chosen/chosen.proto.js'></script>

		<script>
			// Add the following code if you want the name of the file appear on select
			$(".custom-file-input").on(
					"change",
					function() {
						var fileName = $(this).val().split("\\").pop();
						$(this).siblings(".custom-file-label").addClass(
								"selected").html(fileName);
					});
			$(function() {
				$('.chosen-select').chosen();
				$('.chosen-select-deselect').chosen({
					allow_single_deselect : true
				});
			});
		</script>
	</div>
</body>
</html>