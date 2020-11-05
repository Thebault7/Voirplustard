<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
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
		<form method="post" action="ConnexionUtilisateur">
			<div>
				<label for="emailUtilisateur">Identifiant</label>
				<input type="email" id="emailUtilisateur" placeholder="Entrez un identifiant" name="nomUtilisateur"/>
			</div>
			<div>
				<label for="passwordUtilisateur">Mot de passe</label>
				<input type="password" id="passwordUtilisateur" placeholder="Mot de passe" name="passwordUtilisateur"/>
			</div>
			<div>
				<button type="submit">Se connecter</button>
				<a href="">Mot de passe oublié</a>
			</div>
		</form>
		<form method="get" action="ConnexionUtilisateur">
			<div>
				<button type="submit" name="nouveauCompte">S'inscrire</button>
			</div>
		</form>
	</div>
	
	
	
	
	
	
	

	
	<br><hr><br>
	<div>
		<a href="#" onclick="ajaxFonction()">ajax</a>
	</div>
	<h1 id="textici"></h1>
</body>

<script type="text/javascript">
	function ajaxFonction() {
		var url = "https://api.dailymotion.com/videos?fields=title%2Clanguage%2Cchannel.name%2Cduration%2Cowner.screenname&page=1&limit=3&search=html&languages=fr";
		var addressRequest = new XMLHttpRequest();
		addressRequest.open('GET', url);
		addressRequest.onload = function() {
//			console.log(addressRequest.responseText);
			if (addressRequest.status >= 200 && addressRequest.status < 400) {
				var addressData = JSON.parse(addressRequest.responseText);
				document.getElementById("textici").innerHTML = addressData['list'][1]['channel.name'];
			} else {
				alert("Pas de page à afficher");
			};	
		};
		addressRequest.onerror = function() {
			alert('autre erreur');
		};
		addressRequest.send();
	}
</script>
</html>