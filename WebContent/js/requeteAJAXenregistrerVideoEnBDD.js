/**
 * 
 */


function enregistrerVideoParAjax(count) {
	var url = 'http://localhost:8080/Voirplustard/valider?duree='
			+ encodeURIComponent(durations[count]) + '&description='
			+ encodeURIComponent(descriptions[count]) + '&langue='
			+ encodeURIComponent(languages[count]) + '&site='
			+ encodeURIComponent(sites[0]) + '&titre='
			+ encodeURIComponent(titles[count]) + '&channel='
			+ encodeURIComponent(channels[count]) + '&proprietaire='
			+ encodeURIComponent(proprietaires[count]) + '&id='
			+ encodeURIComponent(ids[count]);
	if (window.XMLHttpRequest) {
		requete = new XMLHttpRequest();
		requete.open("POST", url, true);
		requete.onreadystatechange = majIHM;
		requete.send(null);
	} else if (window.ActiveXObject) {
		requete = new ActiveXObject("Microsoft.XMLHTTP");
		if (requete) {
			requete.open("POST", url, true);
			requete.onreadystatechange = majIHM;
			requete.send();
		}
	} else {
		alert("Le navigateur ne supporte pas la technologie AJAX");
	}
}

function majIHM() {
	if (requete.readyState == 4) {
		if (requete.status == 200) {
			document.getElementById("validationMessage").innerHTML = requete.responseText;
		} else {
			alert('Une erreur est survenue lors de la mise à jour de la page.'
					+ '\n\nCode retour = ' + requete.statusText);
		}
	}
}