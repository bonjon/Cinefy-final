<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<!-- linked CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div>
		<div class="loginBackground">
			<img src="img/cinema.png" width="740" height="720">
			<div class="rectangular"></div>
			<div class="cinefy">Cinefy</div>
			<form action="LoginServlet" method="post">
				<input class="registration" type="submit" name="registration"
					id="registration" value="Registration">
			</form>
		</div>
		<div class="loginBackground2">
			<h1 class="login">Login</h1>
			<%
				String log = (String) request.getAttribute("login");
				if (log != null) {
					if (log != null) {
			%><h6 style="color: RED;"><%=log%></h6>
			<%
				}
				}
			%>
			<!-- Login Form -->
			<form class="form" action="LoginServlet" name="loginForm"
				method="post">
				<div>
					<label for="username" class="loginText">Username</label><br> <input
						id="username" name="username" type="text" class="text-box">
				</div>
				<br>
				<div>
					<label for="password" class="loginText">Password</label><br> <input
						id="password" name="password" type="password" class="text-box">
				</div>
				<br>
				<div>
					<input name="login" type="submit" id="login" value="Sign in"
						class="signIn">
				</div>
				<br>
				<div>
					<a href="" class="facebook connect">Sign in with Facebook</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>