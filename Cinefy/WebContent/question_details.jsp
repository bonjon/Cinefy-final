<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.ArrayList, java.util.List, logic.utils.*, logic.bean.DomandaBean, logic.bean.RispostaBean"%>
<%
	DomandaBean QU = (DomandaBean) session.getAttribute("QU");
	RispostaBean R = (RispostaBean) session.getAttribute("R");
%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<title>Question Details</title>
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
			<label class="textUp">Question details</label><br>
			<div class="cardContainer" style="overflow: auto; height: 100px;">
				<label class="headde show-white-space"><%=QU.getContenuto()%></label>
			</div>
			<br> <label class="textMiddle">Answer from <%=QU.getAdvancedName()%></label><br>
			<div class="cardContainer" style= "overflow: auto; height: 100px">
				<label class="headde show-white-space"><%=R.getContenuto()%></label>
			</div>
			<br>
			<form action="QuestionDetailsServlet" method="post">
				<div
					class="container d-flex justify-content-center mt-100 alignment">
					<div class="row">
						<div class="col-md-6">
							<div class="card">
								<div class="card-body text-center">
									<fieldset class="rating" >
										<input type="radio" id="star5" name="rating" value="5" /><label
											class="full" for="star5" title="Awesome - 5 stars"></label> <input
											type="radio" id="star4" name="rating" value="4" /><label
											class="full" for="star4" title="Pretty good - 4 stars"></label>
										<input type="radio" id="star3" name="rating" value="3" /><label
											class="full" for="star3" title="Meh - 3 stars"></label> <input
											type="radio" id="star2" name="rating" value="2" /><label
											class="full" for="star2" title="Kinda bad - 2 stars"></label>
										<input type="radio" id="star1" name="rating" value="1" /><label
											class="full" for="star1" title="Sucks big time - 1 star"></label>
									</fieldset>
									<input name="BTNOK" type="submit" id="BTNOK" value="OK"
										class="signIn" style="margin-left: 18px;">
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
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