<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.ArrayList, java.util.List, logic.utils.*, logic.controllers.ProfileController, logic.bean.AdvancedUserBean, logic.bean.GeneralUserBean, logic.bean.BeginnerUserBean"%>
<%
	GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
	BeginnerUserBean bub = new BeginnerUserBean();
	AdvancedUserBean aub = new AdvancedUserBean();
	ProfileController pc = new ProfileController();
	if (gub.getRole().equals("beginner"))
		bub = pc.getUser(gub.getUsername(), gub.getRole());
	else
		aub = pc.getUser2(gub.getUsername(), gub.getRole());
%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<title>Profile</title>
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
					if (gub.getRole().equals("beginner")) {
				%>
				<li class="liBtn"><form action="home_beginner.jsp"
						method="post">
						<input type="submit" class="notSelected" value="Home">
					</form></li>
				<li class="liBtn"><form action="AskBeginnerServlet"
						method="post">
						<input type="submit" class="notSelected" value="Ask">
					</form></li>
				<li class="liBtn"><form action="PlaylistsBeginnerServlet"
						method="post">
						<input type="submit" class="notSelected" value="Playlists">
					</form></li>
				<li class="liBtn"><form action="profile.jsp" method="post">
						<input type="submit" class="selected" value="Profile">
					</form></li>
				<%
					} else {
				%>
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
						<input type="submit" class="notSelected" value="Playlists">
					</form></li>
				<li class="liBtn"><form action="profile.jsp" method="post">
						<input type="submit" class="selected" value="Profile">
					</form></li>
				<%
					}
				%>
			</ul>
		</div>
		<div class="splitRight">
			<%
				if (gub.getRole().equals("advanced")) {
			%>
			<label class="textUp">Profile</label><br>
			<div class="cardContainer">
				<%
					if (aub.getProfilePic() == null) {
				%>
				<img src="<%="img/profilePictures/" + "default.png"%>"
					class="circleImg" height="150" width="150" />
				<%
					} else {
				%>
				<img src="<%="img/profilePictures/" + aub.getProfilePic()%>"
					class="circleImg" height="150" width="150" />
				<%
					}
				%>
				<h3 class="headUser"><%=aub.getUsername()%></h3>
				<h6 class="headSmall"><%=aub.getVoto()%>
					average
				</h6>
				<%
					int vote = (int) Double.parseDouble(aub.getVoto());
						for (int i = 0; i < vote; i++) {
				%><span class="fa">★</span>
				<%
					}
				%><h3 class="headUser"><%=aub.getProfession()%></h3>
				<h3 class="headUser">
					Tokens:
					<%=aub.getTokens()%></h3>
				<p class="bioP"><%=aub.getBio()%></p>
			</div>
			<%
				} else {
			%>
			<label class="textUp">Profile</label><br>
			<div class="cardContainer">
				<%
					if (bub.getProfilePic() == null) {
				%>
				<img src="<%="img/profilePictures/" + "default.png"%>"
					class="circleImg" height="150" width="150" />
				<%
					} else {
				%>
				<img src="<%="img/profilePictures/" + bub.getProfilePic()%>"
					class="circleImg" height="150" width="150" />
				<%
					}
				%>
				<h3 class="headUser"><%=bub.getUsername()%></h3>
				<p class="bioP"><%=bub.getBio()%></p>
			</div>
			<br>
			<%
				}
			%>
			<form class="logout" action="LogoutServlet" method="post">
				<input type="submit" name="logout" class="logoutB" value="Logout">
			</form>
		</div>
	</div>
</body>
</html>