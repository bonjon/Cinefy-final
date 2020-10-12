<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.ArrayList, java.util.List, logic.utils.*, logic.bean.FilmBean, logic.bean.GeneralUserBean"%>
<%
	GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<title>Home</title>
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
			<%
				String log = (String) request.getAttribute("search");
				if (log != null) {
					if (log != null) {
			%><h6 style="color: RED;"><%=log%></h6>
			<%
				}
				}
			%><label class="textUp">Search films</label><br>
			<form action="HomeBeginnerServlet" method="post">
				<input name="searchString" type="text" class="text-box2"
					aria-label="Search"><select class="formControl"
					name="userProf" id="userProf">
					<option selected="selected">Name</option>
					<option>Director</option>
					<option>Year</option>
					<option>Actor</option>
					<option>Nation</option>
					<option>Genre</option>
				</select>
			</form>
			<br>
		</div>
	</div>
</body>
</html>