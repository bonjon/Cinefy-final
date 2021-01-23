<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.ArrayList, java.util.List, logic.utils.*, logic.bean.BeginnerUserBean, logic.bean.DomandaBean"%>
<%
	
	BeginnerUserBean beg = (BeginnerUserBean) session.getAttribute("begS");
	DomandaBean QU = (DomandaBean) session.getAttribute("QU");
	
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
		<div class="splitLeft pageStretch" style="height: 990px">
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
		<div class="splitRight pageStretch" style="height: 990px">
			<label class="textUp">Answer to <%= beg.getUsername() %></label><br>
			<div class="cardContainer">
				<%
					if (beg.getProfilePic() != null) {
				%>
				<img src="<%="img/profilePictures/" + beg.getProfilePic()%>"
					class="circleImg" height="150" width="150" />
				<%
					} else {
				%>
				<img src="<%="img/profilePictures/" + "default.png"%>"
					class="circleImg" height="150" width="150" />
				<%
					}
				%>
				<p class="bioP"><%=beg.getBio()%></p>
			</div>
			
			<div style="text-align: center; padding-top:6px;">
			<label class="textUp" >Question</label><br>
			<div class="cardContainer" style="padding-top:6px;">
				<label class="headde"><%=QU.getContenuto()%></label>
			</div></div>
			<br><br>
			<input name="switch" id="switch" type="submit"
					class="signIn" value="Switch type of answer" style="margin-left:566px; text-align: center;">
			<br><br>
			<%
				String log = (String) request.getAttribute("error");
				if (log != null) {
					if (log != null) {
			%><h6 style="color: RED; text-align: center; padding-left:12px"><%=log%></h6>
			<%
					}
					}
			%>
			<div style="text-align: center;">
			<label class="textMiddle">General answer</label><br>
			<form class="containerBio" style="padding-top:6px;" action="GeneralAnswerServlet" method="post">
				<div>
				<textarea name="answer" id="answer" class="quest" style="margin: auto; height: 150px;"></textarea>
				</div>
					<br>
					
					<div class="gAnswerRes" style="width: 900px; display: inline-block; background: #1c1c1c;">
						<div class="resContainerL" style=" min-width:450px; float:left;">
							<span  class="checkBL" style="color: #f5c518; margin: auto; text-align:center;" >
							<input  type="checkbox"  id="colleagueMark" name="colleagueMark" value="colmark"> Suggest to interact also with a colleague 
							</span>
							<br><br>
							<label for="colleagueName" class="resFieldsText" style="text-align:center; font-size: 12px; color: #f5c518">Advanced user's nickname</label><br> <input
								id="colleagueName" name="colleagueName" type="text" class="text-box"> <br> <br>
							<label for="reasonChoice" class="resFieldsText" style="text-align:center; font-size: 12px; color: #f5c518">Why I suggest this colleague</label><br>
								<select class="formControl" name="reasonChoice" id="reasonChoice" onChange="select()"> 
									<option>My personal knowledge</option>
									<option>Renowned person in this sector</option>
								</select> <br>
							
						</div>
						<div class="resContainerR" style=" min-width:449px; float: right;">
							<span  class="checkBR"  style="color: #f5c518; margin: auto; text-align:center;" >
							<input  type="checkbox" id="resourceMark" name="resourceMark" value="resmark"> Suggest additional web resources
							</span>
							<br><br>
							<label for="wikiLink" class="resFieldsText" style="text-align:center; font-size: 12px; color: #f5c518">Wikipedia link</label><br> <input
								id="wikiLink" name="wikiLink" type="text" class="text-box"> <br> <br>
							<label for="youtubeLink" class="resFieldsText" style="text-align:center; font-size: 12px; color: #f5c518">YouTube link</label><br> <input
								id="youtubeLink" name="youtubeLink" type="text" class="text-box"> <br> <br>
						</div>
					</div>
					
					
				<br> <br> <input name="make" id="make" type="submit"
					class="signIn" value="Submit" style="margin-left: 20px;">
			</form>
			</div>
			</div>
	</div>
</body>
</html>