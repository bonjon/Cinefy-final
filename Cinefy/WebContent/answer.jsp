<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.ArrayList, java.util.List, logic.utils.*, logic.bean.BeginnerUserBean, logic.bean.DomandaBean"%>
<%
	
	BeginnerUserBean beg = (BeginnerUserBean) session.getAttribute("begS");
%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<title>answer</title>
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
						<input type="submit" class="selected" value="Answer">
					</form></li>
				<li class="liBtn"><form action="PlaylistsAdvancedServlet"
						method="post">
						<input type="submit" class="notSelected" value="Playlists">
					</form></li>
				<li class="liBtn"><form action="profile.jsp" method="post">
						<input type="submit" class="notSelected" value="Profile">
					</form></li>
			</ul>
		</div>
		<div class="splitRight">
			<label class="textUp">Answer to <%=beg.getUsername()%></label><br>
			<div class="cardContainer">
				<img src="<%="img/profilePictures/" + beg.getProfilePic()%>"
					class="circleImg" height="150" width="150" />
				
				<p class="bioP"><%=beg.getBio()%></p>
			</div>
			<%
				String log = (String) request.getAttribute("error");
				if (log != null) {
					if (log != null) {
			%><h6 style="color: RED; text-align: center;"><%=log%></h6>
			<%
				}
				}
			%>
			
			<form class="containerBio" action="GeneralAnswerServlet" method="post">
				<div>
				<textarea name="answer" id="answer" class="quest" style="margin: auto;"></textarea>
				</div>
					<br>
					<div class="gAnswerRes">
						<div class="resContainerL" >
						<span  class="checkBL" >
							<input  type="checkbox"> Suggest to interact also with a collegue
						</span></div>
						<div class="resContainerR" >
						<span  class="checkBR" >
							<input  type="checkbox" > Suggest additional web resources
						</span></div>
					</div>
					
				<br> <br> <input name="make" id="make" type="submit"
					class="signIn" value="Submit">
			</form>
			
		</div>
	</div>
</body>
</html>