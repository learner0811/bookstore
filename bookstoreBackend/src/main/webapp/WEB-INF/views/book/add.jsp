<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
.chosen-container{
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
				<c:if test="${not empty errorMsg}">
					<div class="alert alert-danger alert-dismissible">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<c:out value="${errorMsg}"></c:out>
					</div>
				</c:if>

				<form>
					<div class="row">
						<div class="form-group col-md-6">
							<label>Name</label> <input type="text" class="form-control"
								name="name">
						</div>
						<div class="form-group col-md-6">
							<label style="display: block">Category</label> 
							<select data-placeholder="Select category" multiple
								class="chosen-select" tabindex="8">
								<option value=""></option>
								<option>American Black Bear</option>
								<option>Asiatic Black Bear</option>
								<option>Brown Bear</option>
								<option>Giant Panda</option>
								<option>Sloth Bear</option>
								<option disabled>Sun Bear</option>
								<option selected>Polar Bear</option>
								<option disabled>Spectacled Bear</option>
							</select>
						</div>
					</div>

					<div class="row">
						<div class="form-group col-md-6">
							<label>Author</label> <select class="form-control">
								<option value="0">None</option>
							</select>
						</div>
						<div class="form-group col-md-6">
							<label>Publisher</label> <select class="form-control">
								<option value="0">None</option>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-6">
							<label>Price</label> <input type="text" class="form-control"
								name="name">
						</div>
						<div class="form-group col-md-6">
							<label>Discount</label> <input type="text" class="form-control"
								name="name">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-6">
							<label>Status</label> <select class="form-control">
								<option value="0">Sold</option>
								<option value="1">Available</option>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-6">
							<label>Image</label>
							<div class="custom-file">
								<input type="file" class="custom-file-input" id="customFile">
								<label class="custom-file-label" for="customFile">Choose
									file</label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<button type="submit" class="btn btn-info">Submit</button>
						</div>
					</div>
				</form>


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