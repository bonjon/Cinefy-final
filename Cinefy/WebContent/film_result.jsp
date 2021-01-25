<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="logic.utils.*, logic.bean.FilmBean, logic.bean.GeneralUserBean"%>
<%
	FilmBean listFilms = (FilmBean) session.getAttribute("listFilms");
	GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<title>Result</title>
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
				<%
					if (gub.getRole().equals("advanced")) {
				%><li class="liBtn"><form action="home_beginner.jsp"
						method="post">
						<input type="submit" class="selected" value="Home">
					</form></li>
				<li class="liBtn"><form action="AnswerAdvancedServlet"
						method="post">
						<input type="submit" class="notSelected" value="Answer">
					</form></li>
				<li class="liBtn"><form action="PlaylistsAdvancedServlet"
						method="post">
						<input type="submit" class="notSelected" value="Playlists">
					</form></li>
				<li class="liBtn"><form action="profile.jsp"
						method="post">
						<input type="submit" class="notSelected" value="Profile">
					</form></li>
				<%
					} else {
				%>
				<li class="liBtn"><form action="home_beginner.jsp"
						method="post">
						<input type="submit" class="selected" value="Home">
					</form></li>
				<li class="liBtn"><form action="AskBeginnerServlet"
						method="post">
						<input type="submit" class="notSelected" value="Ask">
					</form></li>
				<li class="liBtn"><form action="PlaylistsBeginnerServlet"
						method="post">
						<input type="submit" class="notSelected" value="Playlists">
					</form></li>
				<li class="liBtn"><form action="profile.jsp"
						method="post">
						<input type="submit" class="notSelected" value="Profile">
					</form></li>
				<%
					}
				%>
			</ul>
		</div>
		<div class="splitRight">
			<label class="textUp">Click on the title for visiting IMDB
				page</label><br> <a class="linkGo" href="<%=listFilms.getUrl()%>"><%=listFilms.getTitolo()%></a>
		</div>
	</div>
</body>
</html>