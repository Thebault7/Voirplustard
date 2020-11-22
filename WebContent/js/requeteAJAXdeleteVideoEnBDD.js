/**
 * 
 */

var requete;

function genererURLdeleteEnBDD(idVideo) {
	var url = 'http://localhost:8080/Voirplustard/valider?idVideo='
			+ idVideo;
	if (window.XMLHttpRequest) {
		requete = new XMLHttpRequest();
		requete.open("DELETE", url, true);
		requete.onreadystatechange = affichageMessageSuppression;
		requete.send(null);
	} else if (window.ActiveXObject) {
		requete = new ActiveXObject("Microsoft.XMLHTTP");
		if (requete) {
			requete.open("DELETE", url, true);
			requete.onreadystatechange = affichageMessageSuppression;
			requete.send();
		}
	} else {
		alert("Le navigateur ne supporte pas la technologie AJAX");
	}
}

function affichageMessageSuppression() {
	if (requete.readyState == 4) {
		if (requete.status == 200) {
			
		} else {
			
		}
	}
}