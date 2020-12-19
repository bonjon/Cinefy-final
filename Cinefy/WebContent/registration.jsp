<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String regMessage = "Registration";
	String res = (String) request.getAttribute("reg");
	if (res != null) {
		if (res.equals("registered")) {
			regMessage = "Registration successfull";
		} else if (res.equals("notRegistered")) {
			regMessage = "Registration unsuccessfull";
		}
	}
%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<title>Registration</title>
<!-- linked CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
	function select() {
		var mySelect = document.getElementById('userType');
		var selected = mySelect.options[mySelect.selectedIndex].text;
		if (selected == "Beginner") {
			document.getElementById("userProf").disabled = true;
		} else {
			document.getElementById("userProf").disabled = false;
		}
	}
</script>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div>
		<div class="loginBackground">
			<img src="img/cinema.png" width="740" height="720">
			<div class="rectangular"></div>
			<div class="cinefy">Cinefy</div>
		</div>
		<div class="loginBackground2">
			<h1 class="login">Registration</h1>
			<%
				String log2 = (String) request.getAttribute("reg");
				if (log2 != null) {
					if (log2 != null) {
			%><h6 style="color: RED text-align: center;"><%=log2%></h6>
			<%
				}
				}
			%>
			<!-- Reg Form -->
			<%
				String log = (String) request.getAttribute("field");
				if (log != null) {
					if (log != null) {
			%><h6 style="color: RED text-align: center;"><%=log%></h6>
			<%
				}
				}
			%>
			<form class="formRegi" action="RegistrationServlet" name="loginForm"
				method="post" enctype='multipart/form-data'>
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
					<label for="userType" class="loginText">Type of User</label><br>
					<select class="formControl" name="userType" id="userType"
						onChange="select()">
						<option>Beginner</option>
						<option>Advanced</option>
					</select> <br>
					<div>
						<select class="formControl" name="userProf" id="userProf" disabled>
							<option>Director</option>
							<option>Productor</option>
							<option>Actor</option>
							<option>Screenwriter</option>
							<option>Film editor</option>
						</select>
					</div>
				</div>
				<br>
				<div>
					<label class="loginText" for="avatar">Choose a profile
						picture:</label><br> <input class="av" type="file" id="avatar"
						name="avatar" accept="image/png, image/jpeg">
				</div>
				<br>
				<div>
					<label class="loginText" for="bio">Tell us about you (200
						characters max)</label><br>
					<textarea name="bio" id="bio" class="bio"></textarea>
				</div>
				<br>
				<div>
					<input name="registration" type="submit" id="registration"
						value="Sign up" class="btn">
				</div>
			</form>
		</div>
	</div>
</body>
</html>