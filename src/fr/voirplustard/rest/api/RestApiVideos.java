package fr.voirplustard.rest.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bll.ChannelManager;
import fr.voirplustard.bll.LangueManager;
import fr.voirplustard.bll.ProprietaireManager;
import fr.voirplustard.bll.SiteManager;
import fr.voirplustard.bll.VideoManager;
import fr.voirplustard.bo.Channel;
import fr.voirplustard.bo.Langue;
import fr.voirplustard.bo.Proprietaire;
import fr.voirplustard.bo.Video;

@WebServlet("/valider")
public class RestApiVideos extends HttpServlet implements Servlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RestApiVideos() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("------------------------REST API doGet-----------------------");

		// r�cup�ration du titre fournit par l'utilisateur
		String titreFournit = request.getParameter("texte");

		// r�cup�ration du manager pour acc�der � la base de donn�es
		VideoManager vm = VideoManager.getInstance();

		// recherche des vid�os correspondant au titre fournit par l'utilisateur
		List<Video> listeVideo = new ArrayList<>();
		try {
			listeVideo = vm.selectionnerParTitre(titreFournit);
		} catch (SQLException e) {
			System.out.println("Erreur de connexion avec la base de donn�es.");
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Aucune vid�o correspondant dans la base de donn�es.");
		}
		
		// on v�rifie si la liste de vid�os obtenues est vide ou pas
		if (listeVideo.isEmpty()) {
			response.getWriter().write("<p>Aucune vid�o n'a �t� trouv�e.</p>");
			return;
		}
		
		// on envoie la liste formatt�e HTML vers la jsp
		String message = "";
		for (int i = 0; i < listeVideo.size(); i++) {
			message += "titre de la vid�o : " + listeVideo.get(i).getTitre() + "<br>";
		}
		response.getWriter().write("<p>" + message + "</p>");
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("------------------------REST API doPost-----------------------");
		
		// on charge les manager pour aller chercher les identifiant en base de donn�es
		LangueManager lm = LangueManager.getInstance();
		SiteManager sm = SiteManager.getInstance();
		ChannelManager cm = ChannelManager.getInstance();
		ProprietaireManager pm = ProprietaireManager.getInstance();
		VideoManager vm = VideoManager.getInstance();

		// message � envoyer vers la jsp � la fin
		String message = "";

		// on v�rifie si la vid�o n'est pas d�j� en base de donn�es

		try {
			if (vm.isVideoDejaPresente(request.getParameter("id"))) {
				message += "Cette vid�o est d�j� enregistr�e parmis vos favoris.";
			} else {
				// on cr�e une nouvelle video dont on rempli les attributs
				Video video = new Video();
				video.setDuree(Integer.parseInt(request.getParameter("duree")));
				video.setDescription(request.getParameter("description"));
				video.setTitre(request.getParameter("titre"));
				video.setUtilisateur((Integer) (request.getSession().getAttribute("idUtilisateur")));
				video.setIdVideoDuSite(request.getParameter("id"));

				// on v�rifie si la langue de la video est d�j� en base de donn�es. Sinon, on
				// l'y ajoute
				Langue langue = lm.selectionnerParNom(request.getParameter("langue"));
				if (langue.getLangue() == null) {
					int idLangue = lm.ajouter(new Langue(request.getParameter("langue")));
					video.setLangue(idLangue);
				} else {
					video.setLangue(langue.getIdLangue());
				}

				video.setIdVideoWebSite(sm.selectionnerParNom(request.getParameter("site")).getidSite());

				// on v�rifie si le channel de la video est d�j� en base de donn�es. Sinon, on
				// l'y ajoute
				Channel channel = cm.selectionnerParNom(request.getParameter("channel"));
				if (channel.getChannel() == null) {
					int idChannel = cm.ajouter(new Channel(request.getParameter("channel")));
					video.setNomChannel(idChannel);
				} else {
					video.setNomChannel(channel.getIdChannel());
				}

				// de m�me pour le propri�taire
				Proprietaire proprietaire = pm.selectionnerParNom(request.getParameter("proprietaire"));
				if (proprietaire.getProprietaire() == null) {
					int idProprietaire = pm.ajouter(new Proprietaire(request.getParameter("proprietaire")));
					video.setProprietaire(idProprietaire);
				} else {
					video.setProprietaire(proprietaire.getIdProprietaire());
				}

				// on enregistre la vid�o en base de donn�es
				vm.ajouter(video);
			}
			message += "Vid�o enregistr�e.";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Probl�me de connexion");
		}

		response.getWriter().write("<p>" + message + "</p>");
	}
}
