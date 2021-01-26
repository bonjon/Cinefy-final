<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="logic.utils.*, logic.bean.FilmBean, logic.bean.PlaylistBean"%>
<%
	PlaylistBean pb = (PlaylistBean) session.getAttribute("pb");
%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<title>Films on playlist</title>
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
			<form action="AddFilmServlet" method="post">
				<label class="textUp">Name of the playlist and the image</label><br>
				<br> <img
					src="<%="img/playlistPictures/" + pb.getPlaylistPic()%>"
					class="circleImg adjust" height="150" width="150" /><br> <label
					class="headUser adjust"><%=pb.getName()%></label><br>
				<br> <label class="textMiddle">Now search film and
					press enter to add in the playlist</label><br> <br> <input
					id="film" name="film" type="text" class="text-box left">
				<%
					String log = (String) request.getAttribute("error");
					if (log != null) {
						if (log != null) {
				%><h6 style="color: RED; margin-left: 30px"><%=log%></h6>
				<%
					}
					}
				%>
			</form>
		</div>
	</div>
</body>
</html>