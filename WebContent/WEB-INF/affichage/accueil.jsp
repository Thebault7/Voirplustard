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
			Bonjour, vous êtes connecté en tant que " <strong
				id="identifiantUtilisateur"><c:out
					value="${sessionScope.utilisateur.identifiant}" /></strong> "
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
		<%--		<form method="get" action="ChargerVideo">	 --%>
		<form method="get">
			<div>
				<label for="texteAChercher">Titre de la vidéo</label> <input
					type="text" id="texteAChercher" placeholder="Titre de la vidéo"
					name="texteAChercher" required />
			</div>
			<div>
				<input type="button"
					value="Rechercher la vidéo en base de données"
					onclick="genererURLselectionnerEnBDD()" />
			</div>
		</form>
		<div id="afficherVideosDemandees"></div>
	</div>
	<br>
	<hr>
	<br>
	<div>
		<form method="post">
			<label for="rechercheAjax">Entrer le titre de la vidéo
				DAILYMOTION à rechercher</label> <input type="text" id="rechercheAjax"
				placeholder="Titre à chercher" name="rechercheAjax" required /> <input
				type="button" value="Cliquez ici" onclick="creerURL()" />
		</form>
		<div id="validationMessage"></div>
	</div>

	<br>
	<hr>
	<br>

	<div id="affichageVideos"></div>

</body>

<script type="text/javascript">
	var requete;

	function genererURLselectionnerEnBDD() {
		var texteAChercher = document.getElementById("texteAChercher").value;
		var url = 'http://localhost:8080/Voirplustard/valider?texte='
				+ encodeURIComponent(texteAChercher);
		if (window.XMLHttpRequest) {
			requete = new XMLHttpRequest();
			requete.open("GET", url, true);
			requete.onreadystatechange = afficherVideos;
			requete.send(null);
		} else if (window.ActiveXObject) {
			requete = new ActiveXObject("Microsoft.XMLHTTP");
			if (requete) {
				requete.open("GET", url, true);
				requete.onreadystatechange = afficherVideos;
				requete.send();
			}
		} else {
			alert("Le navigateur ne supporte pas la technologie AJAX");
		}
	}
	
	function afficherVideos() {
		if (requete.readyState == 4) {
			if (requete.status == 200) {
				document.getElementById("afficherVideosDemandees").innerHTML = requete.responseText;
			} else {
				alert('Une erreur est survenue lors de la mise à jour de la page.'
						+ '\n\nCode retour = ' + requete.statusText);
			}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	function getFormAsString(nomFormulaire) {

		resultat = "";
		formElements = document.forms[nomFormulaire].elements;

		for (var i = 0; i < formElements.length; i++) {
			if (i > 0) {
				resultat += "&";
			}
			resultat += escape(formElements[i].name) + "="
					+ escape(formElements[i].value);
		}

		return resultat;
	}
</script>
<script src="js/requeteAJAXversSiteVideo.js"></script>
<script src="js/requeteAJAXenregistrerVideoEnBDD.js"></script>
</html>