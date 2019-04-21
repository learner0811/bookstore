<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Login</title>
<!-- MAIN CSS -->
<%@include file="../common/style.jsp"%>
<link href="/bookstore/resources/common/css/login.css" rel="stylesheet"
	type="text/css">

</head>
<body>
	<div class="container">
		<div class="row">
			<%@ include file="../common/msg.jsp"%>
		</div>
		<div class="row">
			<div class="col-md-4" style="margin: 50px auto">
				<h1 class="text-center login-title">Sign in to continue</h1>
				<div class="account-wall">
					<img class="profile-img"
						src="/bookstore/resources/common/img/photo.png" alt="">
					<c:if test="${not empty loginMsg}">
						<span style="color : red; margin-left: 30px">${loginMsg }</span>
					</c:if>
					<form class="form-signin" method="post"
						action="/bookstore/admin_login">
						<input type="text" class="form-control" placeholder="username"
							required autofocus name="username"> <input
							type="password" class="form-control" placeholder="Password"
							required name="password">
						<button class="btn btn-lg btn-primary btn-block" type="submit">
							Sign in</button>
						<label class="checkbox pull-left"> <input type="checkbox"
							value="remember-me"> Remember me
						</label>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- MAIN SCRIPT -->
	<%@include file="../common/lib.jsp"%>
</body>
</html>