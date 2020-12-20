<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.ArrayList, java.util.List, logic.bean.PlaylistBean, logic.bean.GeneralUserBean, logic.bean.FilmBean, logic.controllers.PlaylistDetailsController"%>
<%
	PlaylistBean P = (PlaylistBean) session.getAttribute("P");
	PlaylistDetailsController pdc = new PlaylistDetailsController();
	List<FilmBean> LF = pdc.getFilmPlaylist(P.getId());
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
		<div class="splitRight">
			<label class="textUp">Playlist Details</label><br>
			<div class="cardContainer">
				<img src="<%="img/playlistPictures/" + P.getPlaylistPic()%>"
					class="circleImg" height="150" width="150" />
				<h3 class="headUser"><%=P.getName()%></h3>
				<h3 class="headUser">
					<%=P.getAdvancedName()%>
				</h3>
				<h6 class="headSmall">
					Date of Pubblication
					<%=P.getDate()%></h6>
				<h6 class="headSmall"><%=P.getVoto()%>
					average
				</h6>
				<%
					int vote = (int) Double.parseDouble(P.getVoto());
					for (int j = 0; j < vote; j++) {
				%><span class="fa">★</span>
				<%
					}
				%>
				<ul>
					<%
						for (i = 0; i < LF.size(); i++) {
					%><li class="itemFilms"><a class="linkGo3"
					href="<%=LF.get(i).getUrl()%>"><%=LF.get(i).getTitolo()%></a>
				</li>
					<%
						}
					%>
				</ul>
			</div>
			<%
				if (gub.getRole().equals("beginner")) {
			%>
			<form action="PlaylistDetailsServlet" method="post">
				<div
					class="container d-flex justify-content-center mt-100 alignment">
					<div class="row">
						<div class="col-md-6">
							<div class="card">
								<div class="card-body text-center bigger">
									<fieldset class="rating">
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