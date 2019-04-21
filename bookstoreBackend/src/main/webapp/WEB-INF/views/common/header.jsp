<nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
	<button class="btn btn-primary" id="menu-toggle">
		<i class="fas fa-bars"></i>
	</button>

	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarSupportedContent"
		aria-controls="navbarSupportedContent" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav ml-auto mt-2 mt-lg-0">
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"><i class="fas fa-user"></i> ${user.name} </a>
				<div class="dropdown-menu dropdown-menu-right"
					aria-labelledby="navbarDropdown">
					<a class="dropdown-item" href="/bookstore/admin_login"><i
						class="fas fa-sign-in-alt"></i>Login</a> <a class="dropdown-item"
						href="/bookstore/signout"><i class="fas fa-sign-out-alt"></i>Signout</a>
					<!--  <div class="dropdown-divider"></div>
					<a class="dropdown-item" href="#">Something else here</a>-->
				</div></li>
		</ul>
	</div>
</nav>