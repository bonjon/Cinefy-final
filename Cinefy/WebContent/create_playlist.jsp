<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="logic.utils.*, logic.bean.FilmBean, logic.bean.PlaylistBean"%>
<%
	String check = (String) session.getAttribute("check");
	String search = (String) session.getAttribute("search");
%>
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
			<ul class="listGroup">
				<li class="liBtn"><form action="home_beginner.jsp"
						method="post">
						<input type="submit" class="notSelected" value="Home">
					</form></li>
				<li class="liBtn"><form action="AnswerAdvancedServlet"
						method="post">
						<input type="submit" class="notSelected" value="Ask">
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
			<form action="CreatePlaylistServlet" method="post">
				<%
					if (check.equals("false")) {
				%>
				<label class="textUp">Name of the playlist and choose the
					image</label><br> <br>
				<%
					String logx = (String) request.getAttribute("errorx");
						if (logx != null) {
							if (logx != null) {
				%><h6 style="color: RED;"><%=logx%></h6>
				<%
					}
						}
				%>
				<input id="name" name="name" type="text" class="text-box left"><br>
				<br> <input class="av2" type="file" id="avatar" name="avatar"
					accept="image/png, image/jpeg"><br> <input
					class="signIn left" id="ok" name="ok" type="submit" value="Ok">
				<%
					} else {
						PlaylistBean pb = (PlaylistBean) session.getAttribute("pb");
				%>
				<label class="textUp">Name of the playlist and the image</label><br>
				<img src="<%="img/profilePictures/" + pb.getPlaylistPic()%>"
					class="circleImg" height="150" width="150" /><br> <label
					class="headUser"><%=pb.getName()%></label><br> <label
					class="textMiddle">Now add films on playlist</label><br> <br>
				<input id="film" name="film" type="text" class="text-box left">
				<%
					String log = (String) request.getAttribute("error");
						if (log != null) {
							if (log != null) {
				%><h6 style="color: RED;"><%=log%></h6>
				<%
					}
						}
				%>
				<%
					if (search.equals("true")) {
							FilmBean F = (FilmBean) session.getAttribute("F");
				%>
				<input class="signIn left" id="add" name="add" type="submit"
					value="Add on this playlist"><br> <a class="linkGo"><%=F.getTitolo()%></a>
				<%
					}
				%><br> <input class="signIn left" id="conclude" name="conclude"
					type="submit" value="Finish">
				<%
					}
				%>
			</form>
		</div>
	</div>
</body>
</html>