<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<h1>Page d'accueil de Voirplustard</h1>
	<br>
	<div>
		<form method="post" action="ServletConnexionUtilisateur">
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
				<span class="pull-right"><a href="">Mot de passe oublié</a></span>
			</div>
		</form>
		<form method="get" action="ServletConnexionUtilisateur">
			<div>
				<button type="submit" name="nouveauCompte">S'inscrire</button>
			</div>
		</form>
	</div>
</body>
</html>