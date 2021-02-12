<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.ArrayList, java.util.List, logic.utils.*, logic.bean.AdvancedUserBean, logic.bean.PlaylistBean"%>
<%
	int i;
	@SuppressWarnings("unchecked")
	List<PlaylistBean> topLP = (List<PlaylistBean>) request.getAttribute("topLP");
%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<title>Playlist</title>
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
			</ul>
		</div>
		<div class="splitRight" style="height: 1300px;">
			<label class="textUp">Top rated playlists</label><br>
			<div>
				<ul style="width: 900px; overflow: auto">

					<%
						for (i = 0; i < topLP.size(); i++) {
					%><li class="itemAd">
						<form class="formCard" action="PlaylistsBeginnerServlet"
							method="post">
							<button type="submit" name="<%="a"%>"
								value="<%=topLP.get(i).getName()%>">
								<%
									if (topLP.get(i).getPlaylistPic() != null) {
								%>
								<img
									src="<%="img/playlistPictures/" + topLP.get(i).getPlaylistPic()%>"
									class="card-img-top cardImg" height="100" width="100" />
								<%
									} else {
								%>
								<img
									src="<%="img/playlistPictures/" + "default2.jpg"%>"
									class="card-img-top cardImg" height="100" width="100" />
								<%
									}
								%>
							</button>
							<br> <label class="nameAd"><%=topLP.get(i).getName()%></label><br>
							<%
							if(topLP.get(i).getVoto().length()>4) {
								String elidedVote;
								elidedVote=topLP.get(i).getVoto().substring(0, 4);
							%><label class="nameAd"><%= elidedVote%>  /10.0</label>
							<% 
							}
							else {
							%><label class="nameAd"><%=topLP.get(i).getVoto()%> /10.0</label>
							<%
							}
							%>
							<input type="hidden" name="index" value="<%=i%>"> <input
								type="hidden" name="P" value="<%=topLP.get(i).getId()%>">
						</form>
					</li>
					<%
						}
					%>
				</ul>
				<br>
			</div>
			<%
				String logx = (String) request.getAttribute("errorx");
				if (logx != null) {
					if (logx != null) {
			%><h6 style="color: RED; margin-left: 30px;"><%=logx%></h6>
			<%
				}
				}
			%>
			<br><br><br>
			<label class="textMiddle">Search playlist</label>
			<form action="SearchPlaylistServlet" method="post">
				<input name="searchString" type="text" class="text-box2"
					aria-label="Search">
			</form>
			<%
				String log = (String) request.getAttribute("error");
				if (log != null) {
					if (log != null) {
			%><h6 style="color: RED; margin-left: 30px;"><%=log%></h6>
			<%
				}
				}
			%>
		</div>
	</div>
</body>
</html>