<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.ArrayList, java.util.List, logic.utils.*, logic.bean.AdvancedUserBean"%>
<%
	AdvancedUserBean AdS = (AdvancedUserBean) session.getAttribute("AdS");
%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<title>Question</title>
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
				<li class="liBtn"><form action="AskBeginnerServlet"
						method="post">
						<input type="submit" class="selected" value="Ask">
					</form></li>
				<li class="liBtn"><form action="PlaylistsBeginnerServlet"
						method="post">
						<input type="submit" class="notSelected" value="Playlists">
					</form></li>
				<li class="liBtn"><form action="profile.jsp" method="post">
						<input type="submit" class="notSelected" value="Profile">
					</form></li>
			</ul>
		</div>
		<div class="splitRight">
			<label class="textUp">Make a question to <%=AdS.getUsername()%></label><br>
			<div class="cardContainer">
				<%
					if (AdS.getProfilePic() != null) {
				%>
				<img src="<%="img/profilePictures/" + AdS.getProfilePic()%>"
					class="circleImg" height="150" width="150" />
				<%
					} else {
				%>
				<img src="<%="img/profilePictures/" + "default.png"%>"
					class="circleImg" height="150" width="150" />
				<%
					}
				%>
				<h6 class="headSmall"><%=AdS.getVoto()%>
					average
				</h6>
				<%
					int vote = (int) Double.parseDouble(AdS.getVoto());
					for (int i = 0; i < vote; i++) {
				%><span class="fa">★</span>
				<%
					}
				%><h3 class="headUser"><%=AdS.getProfession()%></h3>
				<p class="bioP"><%=AdS.getBio()%></p>
			</div>
			<%
				String log = (String) request.getAttribute("error3");
				if (log != null) {
					if (log != null) {
			%><h6 style="color: RED; text-align: center;"><%=log%></h6>
			<%
				}
				}
			%>
			<form class="containerBio" action="MakeQuestionServlet" method="post">
				<textarea name="question" id="question" class="quest"></textarea>
				<br> <br> <input name="make" id="make" type="submit"
					class="signIn" value="Submit">
			</form>
		</div>
	</div>
</body>
</html>