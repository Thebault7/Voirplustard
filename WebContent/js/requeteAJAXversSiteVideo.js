/**
 * 
 */

function creerURL() {
	let motAChercher = document.getElementById("rechercheAjax").value;
	let url = "https://api.dailymotion.com/videos?fields=duration%2Cdescription%2Clanguage%2Cid%2Curl%2Ctitle%2Cchannel.name%2Cowner.screenname&page=1&limit=5&search="
			+ encodeURIComponent(motAChercher);
	envoyerRequeteAJAX(url);
}

function envoyerRequeteAJAX(url) {
	var requete = new XMLHttpRequest();
	requete.open('GET', url);
	requete.onload = function() {
		console.log(requete.responseText);
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
				+ '", "'
				+ requete['list'][i]['id']
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