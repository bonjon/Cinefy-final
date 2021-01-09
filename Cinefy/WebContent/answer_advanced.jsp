<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page
import="java.util.ArrayList, java.util.List, logic.utils.*, logic.bean.AdvancedUserBean, logic.bean.RispostaBean, logic.bean.DomandaBean, logic.bean.GeneralUserBean"%>
<%	
	int j;
	GeneralUserBean user = (GeneralUserBean) session.getAttribute("user");
	@SuppressWarnings("unchecked")
	List<DomandaBean> questions = (List<DomandaBean>) request.getAttribute("questions");
%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Answer</title>
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
		<label class="textDown">Your last questions</label>
			<div>
				<ul style="height: 300px; overflow: auto">
					<%
						for (j = 0; j < questions.size(); j++) {
					%><li class="itemQ">
						<form action="AnswerAdvancedServlet" method="post">
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