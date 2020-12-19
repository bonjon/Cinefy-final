<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.ArrayList, java.util.List, logic.utils.*, logic.bean.AdvancedUserBean, logic.bean.DomandaBean, logic.bean.GeneralUserBean"%>
<%
	int i;
	int j;
	GeneralUserBean user = (GeneralUserBean) session.getAttribute("user");
	@SuppressWarnings("unchecked")
	List<AdvancedUserBean> topAd = (List<AdvancedUserBean>) request.getAttribute("topAd");
	@SuppressWarnings("unchecked")
	List<DomandaBean> questions = (List<DomandaBean>) request.getAttribute("questions");
%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<title>Ask</title>
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
			<label class="textUp">Top Advanced</label><br>
			<div>
				<%
					String logx = (String) request.getAttribute("errorx");
					if (logx != null) {
						if (logx != null) {
				%><h6 style="color: RED; text-align: center"><%=logx%></h6>
				<%
					}
					}
				%>
				<ul style="width: 900px; overflow: auto">

					<%
						for (i = 0; i < topAd.size(); i++) {
					%><li class="itemAd">
						<form class="formCard" action="AskBeginnerServlet" method="post">
							<button type="submit" name="<%="a"%>"
								value="<%=topAd.get(i).getUsername()%>">
								<img
									src="<%="img/profilePictures/" + topAd.get(i).getProfilePic()%>"
									class="card-img-top cardImg" height="100" width="100" />
							</button>
							<br> <label class="nameAd"><%=topAd.get(i).getUsername()%></label><br>
							<label class="nameAd"><%=topAd.get(i).getVoto()%> average</label>
							<input type="hidden" name="index" value="<%=i%>"> <input
								type="hidden" name="AdS" value="<%=topAd.get(i).getUsername()%>">
						</form>

					</li>
					<%
						}
					%>
				</ul>
				<br>
			</div>
			<label class="textMiddle">Search advanced</label>
			<form action="SearchServlet" method="post">
				<input name="searchString" type="text" class="text-box2"
					aria-label="Search">
			</form>
			<%
				String log = (String) request.getAttribute("error");
				if (log != null) {
					if (log != null) {
			%><h6 style="color: RED text-align: center;"><%=log%></h6>
			<%
				}
				}
			%>
			<br> <label class="textDown">Your last questions</label>
			<div>
				<ul style="height: 300px; overflow: auto">
					<%
						for (j = 0; j < questions.size(); j++) {
					%><li class="itemQ">
						<form action="AskBeginnerServlet" method="post">
							<input class="question" type="submit" name="<%="d"%>"
								value="<%=questions.get(j).getContenuto()%>"><input
								type="hidden" name="index2" value="<%=j%>"><input
								type="hidden" name="QU" value="<%=questions.get(j).getId()%>">
						</form>
					</li>
					<%
						}
					%>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>