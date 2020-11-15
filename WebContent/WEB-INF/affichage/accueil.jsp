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
		<%@ include file="/WEB-INF/affichage/affichageVideos.jspf"%>

	</div>
	<br>
	<hr>
	<br>
	<div>
		<form method="post">
			<label for="rechercheAjax">Entrer le titre de la vidéo
				DAILYMOTION à rechercher</label> <input type="text" id="rechercheAjax"
				placeholder="Titre à chercher" name="rechercheAjax" required /> <input
				type="button" value="Cliquez ici" onclick="envoyerRequete()" />
		</form>
	</div>

	<br>
	<hr>
	<br>
	<%-- 
	<form method="get">
		<label for="essaiAPI">Essai API</label> <input type="button"
			value="Cliquer ici" onclick="essaiAPI()" />
	</form>
	<table>
		<tr>
			<td>Valeur :</td>
			<td nowrap><input type="text" id="donnees" name="donnees"
				size="30" onkeyup="valider();"></td>
			<td>
				<div id="validationMessage"></div>
			</td>
		</tr>
	</table>

--%>

	<br>
	<hr>
	<br>
	<div>
		<a href="#" onclick="ajaxFonction()">ajax</a>
	</div>
	<div id="affichageVideos"></div>



	<h1 id="textici"></h1>
	<h1 id="texticibis"></h1>
</body>

<script type="text/javascript">
	var requete;

	function valider() {
		var donnees = document.getElementById("donnees");
		var url = "valider?valeur=" + escape(donnees.value);
		if (window.XMLHttpRequest) {
			requete = new XMLHttpRequest();
			requete.open("GET", url, true);
			requete.onreadystatechange = majIHM;
			requete.send(null);
			// si on fait une méthode post:
			//requete.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			//requete.send("valeur=" + escape(donnees.value));
		} else if (window.ActiveXObject) {
			requete = new ActiveXObject("Microsoft.XMLHTTP");
			if (requete) {
				requete.open("GET", url, true);
				requete.onreadystatechange = majIHM;
				requete.send();
			}
		} else {
			alert("Le navigateur ne supporte pas la technologie AJAX");
		}
	}

	function majIHMbis() {
		var message = "";
		if (requete.readyState == 4) {
			if (requete.status == 200) {
				// exploitation des données de la réponse
				var messageTag = requete.responseXML
						.getElementsByTagName("message")[0];
				message = messageTag.childNodes[0].nodeValue;
				mdiv = document.getElementById("validationMessage");
				// si la réponse est déjà en HTML, on l'affiche directment :
				// document.getElementById("validationMessage").innerHTML = requete.responseText;
				if (message == "invalide") {
					mdiv.innerHTML = "<p>INVALIDE</p>";
				} else {
					mdiv.innerHTML = "<p>VALIDE</p>";
				}
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

	//______________________________________________________________________________________________
	function essaiAPI() {

	}

	function enregistrerVideoParAjax(duree, description, langue, site, titre,
			channel, proprietaire) {
		var url = 'http://localhost:8080/Voirplustard/valider?duree='
				+ encodeURIComponent(duree) + '&description='
				+ encodeURIComponent(description) + '&langue='
				+ encodeURIComponent(langue) + '&site='
				+ encodeURIComponent(site) + '&titre='
				+ encodeURIComponent(titre) + '&channel='
				+ encodeURIComponent(channel) + '&proprietaire='
				+ encodeURIComponent(proprietaire);
		if (window.XMLHttpRequest) {
			requete = new XMLHttpRequest();
			requete.open("GET", url, true);
			requete.onreadystatechange = majIHM;
			requete.send(null);
		} else if (window.ActiveXObject) {
			requete = new ActiveXObject("Microsoft.XMLHTTP");
			if (requete) {
				requete.open("GET", url, true);
				requete.onreadystatechange = majIHM;
				requete.send();
			}
		} else {
			alert("Le navigateur ne supporte pas la technologie AJAX");
		}
	}

	function majIHM() {
		console.log("majIHM !!!!!!!!!!");
	}

	function envoyerRequete() {
		let motAChercher = document.getElementById("rechercheAjax").value;
		let url = "https://api.dailymotion.com/videos?fields=duration%2Cdescription%2Clanguage%2Cid%2Curl%2Ctitle%2Cchannel.name%2Cowner.screenname&page=1&limit=5&search="
				+ encodeURIComponent(motAChercher);
		ajaxFonction(url);
	}

	function creationAffichageVideos(requete) {
		var affichageVideos = "";
		for (var i = 0; i < requete['list'].length; i++) {
			affichageVideos += "<h2>Titre : " + requete['list'][i]['title']
					+ "</h2><br>";
			affichageVideos += '<h3><input type="button" value="Enregistrer cette vidéo" onclick=\'enregistrerVideoParAjax("'
					+ requete['list'][i]['duration']
					+ '", "'
					+ requete['list'][i]['description']
					+ '", "'
					+ requete['list'][i]['language']
					+ '", "'
					+ 'Dailymotion", "'
					+ requete['list'][i]['title']
					+ '", "'
					+ requete['list'][i]['channel.name']
					+ '", "'
					+ requete['list'][i]['owner.screenname']
					//					+ ', ' + document.getElementById('identifiantUtilisateur').innerHTML
					+ '")\' /></h3><br>';
			affichageVideos += "<h3>Chaîne : "
					+ requete['list'][i]['channel.name'] + "</h3><br>";
			affichageVideos += "<h3>Durée : " + requete['list'][i]['duration']
					+ " secondes</h3><br>";
			affichageVideos += '<h3>Lien : <a href="' + requete['list'][i]['url'] + '">'
					+ requete['list'][i]['url'] + '</a></h3><br>';
		}
		return affichageVideos;
	}

	function ajaxFonction(url) {
		var requete = new XMLHttpRequest();
		requete.open('GET', url);
		requete.onload = function() {
			console.log(requete.responseText);
			if (requete.status >= 200 && requete.status < 400) {
				var data = JSON.parse(requete.responseText);
				var affichageVideos = creationAffichageVideos(data);
				document.getElementById("affichageVideos").innerHTML = affichageVideos;
				//				document.getElementById("textici").innerHTML = data['list'][0]['channel.name'];
				//				document.getElementById("texticibis").innerHTML = data['list'][0]['description'];
			} else {
				alert("Pas de page à afficher");
			}
			;
		};
		requete.onerror = function() {
			alert('autre erreur');
		};
		requete.send();
	}
</script>
</html>