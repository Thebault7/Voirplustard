/**
 * 
 */

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
			var resultatSelect = JSON.parse(requete.responseText);
			var affichage = "";
			for (var i = 0; i < resultatSelect.length; i++) {
				affichage += "<h3 class='affichageBDD'>titre : " + resultatSelect[i]['titre']
						+ "</h3><br>";
				affichage += '<h3 class="affichageBDD"><input type="button" value="Suprimer cette vidéo" onclick="genererURLdeleteEnBDD('
						+ resultatSelect[i]['idVideo'] + ')"</h3><br>';
			}

			document.getElementById("afficherVideosDemandees").innerHTML = affichage;
		} else {
			alert('Une erreur est survenue lors de la mise à jour de la page.'
					+ '\n\nCode retour = ' + requete.statusText);
		}
	}
}