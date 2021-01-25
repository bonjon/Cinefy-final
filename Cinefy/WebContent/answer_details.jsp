<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page
	import="java.util.ArrayList, java.util.List, logic.utils.*, logic.bean.DomandaBean, logic.bean.RispostaBean"%>
<%
	DomandaBean QU = (DomandaBean) session.getAttribute("quDetails");
	RispostaBean R = (RispostaBean) session.getAttribute("RI");
	boolean check = (boolean) session.getAttribute("check");
	int vote = (int) session.getAttribute("vote");
	String feedback = "Beginner user's vote was: "+(double)vote+"\n You gained: "+vote+" tokens"; 
%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Answer Details</title>
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
				<li class="liBtn"><form action="home_beginner.jsp"
						method="post">
						<input type="submit" class="notSelected" value="Home">
					</form></li>
				<li class="liBtn"><form action="AnswerAdvancedServlet"
						method="post">
						<input type="submit" class="selected" value="Answer">
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
			<label class="textUp">Answer Details</label>
			<div style="text-align: center;">
			<label class="textUp" >Question from <%=QU.getBeginnerName() %></label><br>
			<div class="cardContainer" style="overflow: auto; height: 100px;">
				<label class="headde show-white-space"><%=QU.getContenuto()%></label>
			</div>
			<br> <br><label class="textMiddle">Your answer</label><br>
			<div class="cardContainer" style= "overflow: auto; height: 100px">
				<label class="headde show-white-space"><%=R.getContenuto()%></label>
			</div>
			<br><br>
			<label class="textMiddle">Answer state</label>
			<%
			if(check){
			%>
			<div class="cardContainer" style= "overflow: auto;">
				<label class="headde show-white-space">Our admins accepted your answer</label>
			</div>
			<%
			if(vote==0){
			%>
				<br><br>
				<label class="textMiddle">Feedback</label>
				<div class="cardContainer" style= "overflow: auto;">
					<label class="headde show-white-space">Beginner user hasn't voted your answer yet</label>
				</div>
			<%
			}
			else{
				%>
				<br><br>
				<label class="textMiddle">Feedback</label>
				<div class="cardContainer" style= "overflow: auto;">
					<label class="headde show-white-space"><%=feedback %></label>
				</div>
			<%
			}
			%>
			<%
			}
			else{
			%>
			<div class="cardContainer" style= "overflow: auto;">
				<label class="headde show-white-space">Answer was sent to our admins who will accept or reject it</label>
			</div>
			<br><br>
			<label class="textMiddle">Feedback</label>
			<div class="cardContainer" style= "overflow: auto;">
				<label class="headde show-white-space">Beginner user hasn't received your answer yet</label>
			</div>
			<%
			}
			%>
		</div>
	</div>
	</div>
</body>
</html>