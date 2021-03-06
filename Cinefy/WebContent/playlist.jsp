<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.ArrayList, java.util.List, java.sql.SQLException, logic.exceptions.FilmNotFoundException, java.util.Collections,  logic.bean.PlaylistBean, logic.bean.GeneralUserBean, logic.bean.FilmBean, logic.controllers.PlaylistDetailsController"%>
<%
	PlaylistBean P = (PlaylistBean) session.getAttribute("P");
	PlaylistDetailsController pdc = new PlaylistDetailsController();
	List<FilmBean> LF = new ArrayList<>();
	try{
		LF = pdc.getFilmPlaylist(P.getId());
	}
	catch(SQLException | FilmNotFoundException e){
		LF = Collections.emptyList();
		
	}
	int i;
	GeneralUserBean gub = (GeneralUserBean) session.getAttribute("user");
%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<title>Playlist Details</title>
<!-- linked CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div>
		<div class="splitLeft" style="height: 1300px;">
			<div class="titolo">Cinefy</div>
			<hr style="border-color: #f5c518;">
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
						<input type="submit" class="selected" value="Playlists">
					</form></li>
				<li class="liBtn"><form action="profile.jsp" method="post">
						<input type="submit" class="notSelected" value="Profile">
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
						<input type="submit" class="selected" value="Playlists">
					</form></li>
				<li class="liBtn"><form action="profile.jsp" method="post">
						<input type="submit" class="notSelected" value="Profile">
					</form></li>
				<%
					}
				%>
			</ul>
		</div>
		<div class="splitRight" style="height: 1300px;">
			<label class="textUp">Playlist details</label><br>
			<div class="cardContainer" style="overflow: auto;">
				<%
					if (P.getPlaylistPic() != null) {
				%>
				<img src="<%="img/playlistPictures/" + P.getPlaylistPic()%>"
					class="circleImg" height="150" width="150" style="margin:auto; 
					margin-left: 35px;"/>
				<%
					} else {
				%>
				<img src="<%="img/playlistPictures/" + "default2.jpg"%>"
					class="circleImg" height="150" width="150" style="margin:auto; 
					margin-left: 35px;" />
				<%
					}
				%>
				<h3 class="headUser" style="padding-left: 35px;"><%=P.getName()%></h3>
				<h3 class="headUser" style="padding-left: 35px;">
					<%=P.getAdvancedName()%>
				</h3><br>
				<h6 class="headSmall" style="padding-left: 35px;">
					Date of pubblication: 
					<%=P.getDate()%></h6>
					
				<%
				if(P.getVoto().length()>4) {
					String elidedVote;
					elidedVote=P.getVoto().substring(0, 4);
					%><h6 class="headSmall" style="padding-top: 6px; padding-left: 35px;">Ratings average: <%= elidedVote%> </h6>
				<% 
				}
				else {
					%><h6 class="headSmall" style="padding-top: 6px; padding-left: 35px;">Ratings average: <%= P.getVoto()%> </h6>
				<%
				}
				%>
				<div style="min-width: 100px; max-height:25px; text-align:center; margin:auto; overflow: auto;">
				<span style="display: inline-block; text-align: center; margin:auto; margin-left: 35px;">
				<%
					int vote = (int) Double.parseDouble(P.getVoto());
					for (int j = 0; j < vote; j++) {
				%><span class="fa" >★</span>
				</span>
				<%
					}
				%>
				</div>
				
				</div>
				<br>
				<br>
				<%
				if(!LF.isEmpty()) {
				%>
				<div class="cardContainer" style="overflow: auto;">
				<ul style="display:block; padding-top: 14px; color: #f5c518;">
					<%
						for (i = 0; i < LF.size(); i++) {
					%><li style="text-align: center; float:none; background-color: #121212;  max-height: 25px; display:flex;min-width:380px;max-width:380px;"
					<span style="text-align: center; margin: auto;"></span>>
					<a class="linkGo3" style="text-align: center; margin: auto; padding-left: 18px;" href="<%=LF.get(i).getUrl()%>"><%=LF.get(i).getTitolo()%></a></li>
						
					<%
						}
					%>
					
				</ul>
				</div>
				<%
				}
				else{
				%>
					<h6 style="color: RED; text-align: center;">This playlist does not contain any films</h6>
				<%
				}
				if (gub.getRole().equals("beginner")) {
			%>
			<form action="PlaylistDetailsServlet" method="post">
				<div
					class="container d-flex justify-content-center mt-100 alignment" style="padding-top:40px; margin-top:0px;">
					<div class="row">
						<div class="col-md-6">
							<div class="card">
								<div class="card-body text-center bigger">
									<fieldset class="rating" style="display: inline-block;">
										<input type="radio" id="star10" name="rating" value="10" /><label
											class="full" for="star10" title="PERFECT - 10 stars"></label>
										<input type="radio" id="star9" name="rating" value="9" /><label
											class="full" for="star9" title="Awesome - 9 stars"></label> <input
											type="radio" id="star8" name="rating" value="8" /><label
											class="full" for="star8" title="Awesome - 8 stars"></label> <input
											type="radio" id="star7" name="rating" value="7" /><label
											class="full" for="star7" title="Pretty Good - 7 stars"></label>
										<input type="radio" id="star6" name="rating" value="6" /><label
											class="full" for="star6" title="Pretty Good - 6 stars"></label>
										<input type="radio" id="star5" name="rating" value="5" /><label
											class="full" for="star5" title="Meh - 5 stars"></label> <input
											type="radio" id="star4" name="rating" value="4" /><label
											class="full" for="star4" title="Meh - 4 stars"></label> <input
											type="radio" id="star3" name="rating" value="3" /><label
											class="full" for="star3" title="Kinda bad - 3 stars"></label>
										<input type="radio" id="star2" name="rating" value="2" /><label
											class="full" for="star2" title="Kinda bad - 2 stars"></label>
										<input type="radio" id="star1" name="rating" value="1" /><label
											class="full" for="star1" title="Sucks big time - 1 star"></label>
									</fieldset>
									<input name="BTNOK" type="submit" id="BTNOK" value="OK"
										class="signIn center">
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<%
				}
			%>
			<%
				String log = (String) request.getAttribute("error");
				if (log != null) {
					if (log != null) {
			%><h6 style="color: RED; text-align: center;"><%=log%></h6>
			<%
				}
				}
			%>
		</div>
	</div>
</body>
</html>