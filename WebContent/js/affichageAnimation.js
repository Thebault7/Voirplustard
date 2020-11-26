/**
 * 
 */

$("#containerVideoBDD")
		.hover(
				function() {
					if ($("#containerVideoBDD").css("width").slice(0, -1)
							.slice(0, -1) <= $("#containerAffichageBDDetSite")
							.css("width").slice(0, -1).slice(0, -1) / 4) {
						$("#containerVideoSite").animate({
							"fontSize" : "0.5em",
							"width" : "20%"
						}, 1000);
						$("#containerVideoBDD").animate({
							"fontSize" : "2em",
							"width" : "80%"
						}, 1000);
						$(".affichageSite").animate({
							"fontSize" : "0.5em"
						}, 1000);
						$(".affichageBDD").animate({
							"fontSize" : "2em"
						}, 1000);
					}
				});

$("#containerVideoSite")
		.hover(
				function() {
					if ($("#containerVideoSite").css("width").slice(0, -1)
							.slice(0, -1) <= $("#containerAffichageBDDetSite")
							.css("width").slice(0, -1).slice(0, -1) / 4) {
						$("#containerVideoBDD").animate({
							"fontSize" : "0.5em",
							"width" : "20%"
						}, 1000);
						$("#containerVideoSite").animate({
							"fontSize" : "2em",
							"width" : "80%"
						}, 1000);
						$(".affichageBDD").animate({
							"fontSize" : "0.5em"
						}, 1000);
						$(".affichageSite").animate({
							"fontSize" : "2em"
						}, 1000);
					}
				});