<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Création d'un compte</title>
</head>
<body>
	<h1>Création d'un nouveau compte utilisateur</h1>
	<div>
		<!-- Création des messages d'erreur -->
		<%
			boolean erreurFormulaire = false;
			boolean erreurIdentifiant = false;
			boolean erreurEmail = false;
			boolean erreurMotDePasse = false;

			if (request.getAttribute("erreurFormulaire") != null) {
				erreurFormulaire = true;
			}
			if (request.getAttribute("erreurIdentifiant") != null) {
				erreurIdentifiant = true;
			}
			if (request.getAttribute("erreurEmail") != null) {
				erreurEmail = true;
			}
			if (request.getAttribute("erreurMotDePasse") != null) {
				erreurMotDePasse = true;
			}
		%>
		<!-- Affichage des messages d'erreur -->
		<p <%if (!erreurFormulaire) {%> style="display: none;" <%}%>>
			<c:out value="${erreurFormulaire}" />
		</p>
		<p <%if (!erreurIdentifiant) {%> style="display: none;" <%}%>>
			<c:out value="${erreurIdentifiant}" />
		</p>
		<p <%if (!erreurEmail) {%> style="display: none;" <%}%>>
			<c:out value="${erreurEmail}" />
		</p>
		<p <%if (!erreurMotDePasse) {%> style="display: none;" <%}%>>
			<c:out value="${erreurMotDePasse}" />
		</p>
	</div>
	<div>
		<form method="post" action="CreationCompte">
			<div>
				<label for="emailNouvelUtilisateur">Email</label> <input
					type="email" id="emailNouvelUtilisateur"
					<%if (request.getAttribute("emailNouvelUtilisateur") == null) {%>
					placeholder="Entrez un email" <%} else {%>
					value="<%=request.getAttribute("emailNouvelUtilisateur")%>" <%}%>
					name="emailNouvelUtilisateur" required />
			</div>
			<div>
				<label for="identifiantNouvelUtilisateur">Pseudonyme</label> <input
					type="text" id="identifiantNouvelUtilisateur"
					<%if (request.getAttribute("identifiantNouvelUtilisateur") == null) {%>
					placeholder="Entrez un pseudonyme" <%} else {%>
					value="<%=request.getAttribute("identifiantNouvelUtilisateur")%>"
					<%}%> name="identifiantNouvelUtilisateur" required />
			</div>
			<div>
				<label for="passwordNouvelUtilisateur">Mot de passe</label> <input
					type="password" id="passwordNouvelUtilisateur"
					placeholder="Entrez un mot de passe"
					name="passwordNouvelUtilisateur" required />
			</div>
			<div>
				<label for="confirmationPasswordNouvelUtilisateur">Confirmation
					du mot de passe</label> <input type="password"
					id="confirmationPasswordNouvelUtilisateur"
					placeholder="Entrez à nouveau le mot de passe"
					name="confirmationPasswordNouvelUtilisateur" required />
			</div>
			<div>
				<button type="submit" value="Creer">Enregistrer</button>
			</div>
		</form>
		<form method="get" action="Accueil">
			<button type="submit" value="annuler">Annuler</button>
		</form>
	</div>
</body>
</html>