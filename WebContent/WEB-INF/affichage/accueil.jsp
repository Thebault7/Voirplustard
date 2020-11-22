<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="fr.voirplustard.bo.Video"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
	integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/accueil.css" />
<title>Page d'accueil</title>
</head>
<body>
	<div>
		<%
			if (session.getAttribute("utilisateur") != null) {
		%>
		<p>
		<h1>Page d'accueil de Voirplustard</h1>
		<br> Bonjour, vous êtes connecté en tant que " <strong
			id="identifiantUtilisateur"> <c:out
				value="${sessionScope.utilisateur.identifiant}" /></strong> ".
		</p>
		<div>
			<form method="get" action="Accueil">
				<button type="submit">Déconnexion</button>
			</form>
		</div>
		<%
			} else {
		%>

		<div id="pageLogin" class="row limiter">
			<div id="pageLogin1" class="col-6 container-login100">
				<div class="row wrap-login100">
					<div class="col-3"></div>
					<div class="col-6" id="connexionContainer">
						<h1>Page de connexion</h1>
						<span class="login100-form-title p-b-43"> Login to continue
						</span> <br>
						<form method="post" action="ConnexionUtilisateur"
							class="login100-form validate-form">
							<%
								if (request.getAttribute("erreurConnexion") != null) {
							%>
							<div id="erreurConnexion">
								<c:out value="${erreurConnexion}" />
							</div>
							<br> <br>
							<%
								}
							%>
							<div>
								<div class="wrap-input100 validate-input">
									<input type="text" id="identifiantUtilisateur"
										<%if (request.getAttribute("identifiant") == null) {%>
										<%} else {%> value="<%=request.getAttribute("identifiant")%>"
										<%}%> name="identifiantUtilisateur" class="input100" required />
									<span class="focus-input100"></span> <span
										class="label-input100">Email</span>
								</div>
							</div>
							<div>
								<div class="wrap-input100 validate-input">
									<input type="password" id="passwordUtilisateur"
										name="passwordUtilisateur" class="input100" required /> <span
										class="focus-input100"></span> <span class="label-input100">Password</span>
								</div>
							</div>
							<div>
								<button type="submit" class="btn btn-primary"
									id="btnSeConnecter">Se connecter</button>
							</div>
						</form>
						<form method="get" action="ConnexionUtilisateur">
							<div>
								<button type="submit" name="nouveauCompte" id="btnSInscrire">S'inscrire</button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div id="pageLogin2" class="col-6">
				<img src="<%=request.getContextPath()%>/img/accueil.jpg"
					alt="image de la page de connexion" id="imgAccueil" class="login100-more" />
			</div>
		</div>
		<%
			}
		%>
	</div>
	<div>
		<%
			if (session.getAttribute("utilisateur") != null) {
		%>
		<div>
			<div>
				<label for="texteAChercher">Titre de la vidéo</label> <input
					type="text" id="texteAChercher" placeholder="Titre de la vidéo"
					name="texteAChercher"
					onKeyPress='if(event.keyCode == 13) genererURLselectionnerEnBDD();'
					required />
			</div>
			<div>
				<input type="button" value="Rechercher la vidéo en base de données"
					onclick="genererURLselectionnerEnBDD()" />
			</div>
			<div id="afficherVideosDemandees"></div>
		</div>
		<br>
		<hr>
		<br>
		<div>
			<label for="rechercheAjax">Entrer le titre de la vidéo
				DAILYMOTION à rechercher</label> <input type="text" id="rechercheAjax"
				placeholder="Titre à chercher" name="rechercheAjax"
				onKeyPress='if(event.keyCode == 13) creerURL();' required /> <input
				type="button" value="Cliquez ici" onclick="creerURL()" />
			<div id="validationMessage"></div>
		</div>

		<br>
		<hr>
		<br>

		<div id="affichageVideos"></div>
		<%
			} else {
		%>
		<div></div>
		<%
			}
		%>
	</div>
</body>


<script type="text/javascript">
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
<script src="js/requeteAJAXselectVideosEnBDD.js"></script>
<script src="js/requeteAJAXdeleteVideoEnBDD.js"></script>
<script src="js/jquery-3.5.1.js"></script>
</html>