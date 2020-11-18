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
				document.getElementById("afficherVideosDemandees").innerHTML = resultatSelect;
			} else {
				alert('Une erreur est survenue lors de la mise à jour de la page.'
						+ '\n\nCode retour = ' + requete.statusText);
			}
		}
	}