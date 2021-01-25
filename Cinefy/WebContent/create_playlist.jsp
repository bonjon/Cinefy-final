<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<title>Create Playlist</title>
<!-- linked CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div>
		<div class="splitLeft">
			<div class="titolo">Cinefy</div>
			<hr style="border-color: #f5c518;">
			<ul class="listGroup">
				<li class="liBtn"><form action="home_beginner.jsp"
						method="post">
						<input type="submit" class="notSelected" value="Home">
					</form></li>
				<li class="liBtn"><form action="AnswerAdvancedServlet"
						method="post">
						<input type="submit" class="notSelected" value="Answer">
					</form></li>
				<li class="liBtn"><form action="PlaylistsAdvancedServlet"
						method="post">
						<input type="submit" class="selected" value="Playlists">
					</form></li>
				<li class="liBtn"><form action="profile.jsp" method="post">
						<input type="submit" class="notSelected" value="Profile">
					</form></li>
			</ul>
		</div>
		<div class="splitRight">
			<form action="CreatePlaylistServlet" method="post"
				enctype='multipart/form-data'>
				<label class="textUp">Name of the playlist and choose the
					image</label><br> <br>
				<%
					String logx = (String) request.getAttribute("errorx");
					if (logx != null) {
						if (logx != null) {
				%><h6 style="color: RED; margin-left: 30px;"><%=logx%></h6>
				<%
					}
					}
				%>
				<input id="name" name="name" type="text" class="text-box left"><br>
				<br> <input class="av2" type="file" id="avatar" name="avatar"
					accept="image/png, image/jpeg"><br> <input
					class="signIn left" id="ok" name="ok" type="submit" value="Ok">
			</form>
		</div>
	</div>
</body>
</html>