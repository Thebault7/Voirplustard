<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="fr.voirplustard.bo.Video"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Page d'accueil</title>
</head>
<body>
	<h1>Page d'accueil de Voirplustard</h1>
	<br>
	<div>
		<%
			if (session.getAttribute("utilisateur") != null) {
		%>
		<p>
			Bonjour, vous êtes connecté en tant que "
			<c:out value="${sessionScope.utilisateur.identifiant}" />
			"
		</p>
		<%
			} else {
		%>
		<div>
			<form method="post" action="ConnexionUtilisateur">
				<%
					if (request.getAttribute("erreurConnexion") != null) {
				%>
				<c:out value="${erreurConnexion}" />
				<%
					}
				%>
				<div>
					<label for="identifiantUtilisateur">Identifiant</label> <input
						type="text" id="identifiantUtilisateur"
						<%if (request.getAttribute("identifiant") == null) {%>
						placeholder="Entrez un identifiant" <%} else {%>
						value="<%=request.getAttribute("identifiant")%>" <%}%>
						name="identifiantUtilisateur" required />
				</div>
				<div>
					<label for="passwordUtilisateur">Mot de passe</label> <input
						type="password" id="passwordUtilisateur"
						placeholder="Mot de passe" name="passwordUtilisateur" required />
				</div>
				<div>
					<button type="submit">Se connecter</button>
					<a href="#">Mot de passe oublié</a>
				</div>
			</form>
			<form method="get" action="ConnexionUtilisateur">
				<div>
					<button type="submit" name="nouveauCompte">S'inscrire</button>
				</div>
			</form>
		</div>
		<%
			}
		%>
	</div>

	<br>
	<hr>
	<br>

	<div>
		<form method="get" action="ChargerVideo">
			<div>
				<label for="texteAChercher">Titre de la vidéo</label> <input
					type="text" id="texteAChercher" placeholder="Titre de la vidéo"
					name="texteAChercher" required />
			</div>
			<div>
				<button type="submit" name="rechercheVideo">Rechercher la
					vidéo en base de données</button>
			</div>
		</form>
	</div>
	<div>
		<%
			List<Video> listeVideos = (List<Video>) request.getAttribute("listeVideo");
			if (listeVideos == null) {
		%>
		<p>Aucune vidéo n'a été trouvée.</p>
		<%
			} else {
				for (int i = 0; i < listeVideos.size(); i++) {
					Video video = listeVideos.get(i);
		%>
		<div>
			<p>
				Titre de la video:
				<%=video.getTitre()%></p>
		</div>
		<%
			}
			}
		%>
	</div>
	<br>
	<hr>
	<br>
	<div>
		<form method="post">
			<label for="rechercheAjax">Entrer le titre de la vidéo DAILYMOTION à rechercher</label>
			<input type="text" id="rechercheAjax" placeholder="Titre à chercher" name="rechercheAjax" required />
			<input type="button" value="Clickez ici" onclick="envoyerRequete()" />
		</form>
	</div>








	<br>
	<hr>
	<br>
	<div>
		<a href="#" onclick="ajaxFonction()">ajax</a>
	</div>
	<h1 id="textici"></h1>
	<h1 id="texticibis"></h1>
</body>

<script type="text/javascript">
	function envoyerRequete() {
		let motAChercher = document.getElementById("rechercheAjax").value;
		let url = "https://api.dailymotion.com/videos?fields=duration%2Cdescription%2Clanguage%2Cid%2Curl%2Ctitle%2Cchannel.name%2Cowner.screenname&page=1&limit=9&search=" + motAChercher;
		ajaxFonction(url);
	}

	function ajaxFonction(url) {
		var addressRequest = new XMLHttpRequest();
		addressRequest.open('GET', url);
		addressRequest.onload = function() {
			console.log(addressRequest.responseText);
			if (addressRequest.status >= 200 && addressRequest.status < 400) {
				var addressData = JSON.parse(addressRequest.responseText);
				document.getElementById("textici").innerHTML = addressData['list'][0]['channel.name'];
				document.getElementById("texticibis").innerHTML = addressData['list'][0]['description'];
			} else {
				alert("Pas de page à afficher");
			}
			;
		};
		addressRequest.onerror = function() {
			alert('autre erreur');
		};
		addressRequest.send();
	}
</script>
</html>