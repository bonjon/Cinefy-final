<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.ArrayList, java.util.List, logic.utils.*, logic.bean.DomandaBean, logic.bean.RispostaBean,logic.bean.GeneralUserBean"%>
<%
	int i;
	@SuppressWarnings("unchecked")
	List<DomandaBean> questionsList = (List<DomandaBean>) session.getAttribute("questionsList");
	@SuppressWarnings("unchecked")
	List<RispostaBean> answersList = (List<RispostaBean>) session.getAttribute("answersList");
%>
<!-- HTML5 -->
<!DOCTYPE html>
<html>
<head>
<title>Home</title>
<!-- linked CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div>
		<div class="splitLeft pageStretch">
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
		<div class="splitRight pageStretch">
			<label class="textUp">List questions</label><br>
			<%
				String log = (String) request.getAttribute("error");
				if (log != null) {
					if (log != null) {
			%><h6 style="color: RED; margin-left: 30px;"><%=log%></h6>
			<%
				}
				}
			%>
			<div>
				<ul style="height: 300px; overflow: auto">
					<%
						for (i = 0; i < questionsList.size(); i++) {
					%><li class="itemQ" style=" padding:0; margin: 0; display:flex; line-height:110%; height:145px;">
						<form action="HomeAdminServlet" method="post">
							<input class="question" type="submit" name="<%="d"%>"
								value="<%=questionsList.get(i).getContenuto()%>"><input
								type="hidden" name="index2" value="<%=i%>"><input
								type="hidden" name="QU"
								value="<%=questionsList.get(i).getId()%>">
						</form>
					</li>
					<%
						}
					%>
				</ul>
			</div>
			<br> <label class="textMiddle">List answers</label><br>
			<%
				String log2 = (String) request.getAttribute("search");
				if (log2 != null) {
					if (log2 != null) {
			%><h6 style="color: RED; margin-left: 30px;"><%=log2%></h6>
			<%
				}
				}
			%>
			<div>
				<ul style="height: 300px; overflow: auto">
					<%
						for (i = 0; i < answersList.size(); i++) {
					%><li class="itemQ" style=" padding:0; margin: 0; display:flex; line-height:110%; height:145px;">
						<form action="HomeAdminServlet" method="post">
							<input class="question" type="submit" name="<%="r"%>"
								value="<%=answersList.get(i).getContenuto()%>"><input
								type="hidden" name="index" value="<%=i%>"><input
								type="hidden" name="R" value="<%=answersList.get(i).getId()%>">
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