<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.ArrayList, java.util.List, logic.utils.*, logic.bean.RispostaBean, logic.bean.GeneralUserBean"%>
<%
	RispostaBean question = (RispostaBean) session.getAttribute("answer");
%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<title>Manage Q and A</title>
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
				<li class="liBtn"><form action="HomeAdminServlet" method="post">
						<input type="submit" class="selected" value="Home">
					</form></li>
				<li class="liBtn"><form action="LogoutServlet" method="post">
						<input name="logout" type="submit" class="notSelected"
							value="Logout">
					</form></li>
			</ul>
		</div>
		<div class="splitRight">
			<label class="textUp">Q and A Details</label><br>
			<div style="text-align: center">
				<label class="contenuto"><%=question.getContenuto()%></label>
				<form class="questionContainer" action="ManageAnswerServlet" method="post">
					<input name="accept" class="acceptB" type="submit" value="accept">
					<input name="reject" class="rejectB" type="submit" value="reject">
				</form>
			</div>
			<br>
		</div>
	</div>
</body>
</html>