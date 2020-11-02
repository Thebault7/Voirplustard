<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
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
		<form method="post" action="CreationCompte">
			<div>
				<label for="emailNouvelUtilisateur">Email</label>
				<input type="email" id="emailNouvelUtilisateur" placeholder="Entrez un email" name="emailNouvelUtilisateur"/>
			</div>
			<div>
				<label for="identifiantNouvelUtilisateur">Pseudonyme</label>
				<input type="text" id="identifiantNouvelUtilisateur" placeholder="Entrez un pseudonyme" name="identifiantNouvelUtilisateur"/>
			</div>
			<div>
				<label for="passwordNouvelUtilisateur">Mot de passe</label>
				<input type="password" id="passwordNouvelUtilisateur" placeholder="Entrez un mot de passe" name="passwordNouvelUtilisateur"/>
			</div>
			<div>
				<button type="submit" name="nouveauCompte">Enregistrer</button>
			</div>
		</form>
		<form method="get" action="Accueil">
			<button type="submit" name="retourAccueil">Annuler</button>
		</form>
	</div>
</body>
</html>