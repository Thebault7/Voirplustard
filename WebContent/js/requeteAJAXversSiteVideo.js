/**
 * 
 */

var requete = new XMLHttpRequest();

var durations = [];
var descriptions = [];
var languages = [];
var sites = ["Dailymotion"];
var titles = [];
var channels = [];
var proprietaires = [];
var ids = [];

function ajouterCaractere(texte, caractere, caractereAAjouter) {
	var indexes = [];
	var continuerRecherche = true;
	var texteModifie = "";
	var i = 0;

	if (texte.indexOf(caractere) === -1) {
		texteModifie = texte;
		continuerRecherche = false;
	} else {
		texteModifie += texte.substr(0, texte.indexOf(caractere))
				+ caractereAAjouter;
		while (continuerRecherche) {
			if (indexes.length === 0) {
				indexes[0] = texte.indexOf(caractere);
				i++
			} else {
				indexes[i] = texte.indexOf(caractere, indexes[i - 1] + 1);
				texteModifie += texte.substr(indexes[i - 1], indexes[i]
						- indexes[i - 1])
						+ caractereAAjouter;
				i++;
			}
			if (texte.indexOf(caractere, indexes[i - 1] + 1) === -1) {
				texteModifie += texte.substr(indexes[i - 1]);
				continuerRecherche = false;
			}
		}
	}
	return texteModifie;
}

function creerURL() {
	let motAChercher = document.getElementById("rechercheAjax").value;
	let url = "https://api.dailymotion.com/videos?fields=duration%2Cdescription%2Clanguage%2Cid%2Curl%2Ctitle%2Cchannel.name%2Cowner.screenname&page=1&limit=5&search="
			+ encodeURIComponent(motAChercher);
	envoyerRequeteAJAX(url);
}

function envoyerRequeteAJAX(url) {
	requete.open('GET', url);
	requete.onload = function() {
		if (requete.status >= 200 && requete.status < 400) {
			var data = JSON.parse(requete.responseText);
			var affichageVideos = creationAffichageVideos(data);
			document.getElementById("affichageVideos").innerHTML = affichageVideos;
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

function creationAffichageVideos(requete) {
	var affichageVideos = "";
	for (var count = 0; count < requete['list'].length; count++) {
		durations[count] = requete['list'][count]['duration'];
		descriptions[count] = requete['list'][count]['description'];
		languages[count] = requete['list'][count]['language'];
		titles[count] = requete['list'][count]['title'];
		channels[count] = requete['list'][count]['channel.name'];
		proprietaires[count] = requete['list'][count]['channel.name'];
		ids[count] = requete['list'][count]['id'];
		
		affichageVideos += "<h2>Titre : " + requete['list'][count]['title']
				+ "</h2><br>";
		affichageVideos += '<h3><input type="button" value="Enregistrer cette vidéo" onclick="enregistrerVideoParAjax(' + count + ')" /></h3><br>';
		affichageVideos += "<h3>Description : "
				+ requete['list'][count]['description'] + "</h3><br>";
		affichageVideos += "<h3>Chaîne : " + requete['list'][count]['channel.name']
				+ "</h3><br>";
		affichageVideos += "<h3>Durée : " + requete['list'][count]['duration']
				+ " secondes</h3><br>";
		affichageVideos += '<h3>Lien : <a href="' + requete['list'][count]['url']
				+ '">' + requete['list'][count]['url'] + '</a></h3><br>';
	}
	return affichageVideos;
}