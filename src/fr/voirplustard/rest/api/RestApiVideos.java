package fr.voirplustard.rest.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

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
import fr.voirplustard.bo.VideoJSON;

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

		// récupération du titre fournit par l'utilisateur
		String titreFournit = request.getParameter("texte");

		// récupération du manager pour accéder à la base de données
		ChannelManager cm = ChannelManager.getInstance();
		LangueManager lm = LangueManager.getInstance();
		ProprietaireManager pm = ProprietaireManager.getInstance();
		SiteManager sm = SiteManager.getInstance();
		VideoManager vm = VideoManager.getInstance();

		// recherche des vidéos correspondant au titre fournit par l'utilisateur
		List<Video> listeVideo = new ArrayList<>();
		HttpSession session = request.getSession();
		try {
			listeVideo = vm.selectionnerParTitre(titreFournit,
					Integer.parseInt(session.getAttribute("idUtilisateur").toString()));
		} catch (SQLException e) {
			System.out.println("Erreur de connexion avec la base de données.");
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Aucune vidéo correspondant dans la base de données.");
		}

		// on vérifie si la liste de vidéos obtenues est vide ou pas
		if (listeVideo.isEmpty()) {
			response.getWriter().write("{\"message\":\"<p>Aucune vidéo n'a été trouvée.</p>\"}");
			return;
		}

		List<VideoJSON> listeVideoJSON = new ArrayList<>();
		for (int i = 0; i < listeVideo.size(); i++) {
			VideoJSON videoJSON = new VideoJSON();
			videoJSON.setIdVideo(listeVideo.get(i).getIdVideo());
			videoJSON.setDuree(listeVideo.get(i).getDuree());
			videoJSON.setDescription(listeVideo.get(i).getDescription());
			videoJSON.setIdVideoDuSite(listeVideo.get(i).getIdVideoDuSite());
			videoJSON.setTitre(listeVideo.get(i).getTitre());
			try {
				videoJSON.setLangue(lm.selectionnerParId(listeVideo.get(i).getLangue()).getLangue());
				videoJSON.setIdVideoWebSite(sm.selectionnerParId(listeVideo.get(i).getIdVideoWebSite()).getsite());
				videoJSON.setNomChannel(cm.selectionnerParId(listeVideo.get(i).getNomChannel()).getChannel());
				videoJSON.setProprietaire(pm.selectionnerParId(listeVideo.get(i).getProprietaire()).getProprietaire());
			} catch (Exception e) {
				System.out.println("Problème de connexion");
				e.printStackTrace();
			}
			listeVideoJSON.add(videoJSON);
		}

		// on envoie la liste formattée JSON vers la jsp
		Gson gson = new Gson();
		String jsonListeVideos = gson.toJson(listeVideoJSON);
		response.getWriter().write(jsonListeVideos);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("------------------------REST API doPost-----------------------");

		// on charge les manager pour aller chercher les identifiant en base de données
		LangueManager lm = LangueManager.getInstance();
		SiteManager sm = SiteManager.getInstance();
		ChannelManager cm = ChannelManager.getInstance();
		ProprietaireManager pm = ProprietaireManager.getInstance();
		VideoManager vm = VideoManager.getInstance();

		// message à envoyer vers la jsp à la fin
		String message = "";

		// on vérifie si la vidéo n'est pas déjà en base de données
		HttpSession session = request.getSession();
		try {
			if (vm.isVideoDejaPresente(request.getParameter("id"),
					Integer.parseInt(session.getAttribute("idUtilisateur").toString()))) {
				message += "Cette vidéo est déjà enregistrée parmis vos favoris.";
			} else {
				// on crée une nouvelle video dont on rempli les attributs
				Video video = new Video();
				video.setDuree(Integer.parseInt(request.getParameter("duree")));
				video.setDescription(request.getParameter("description"));
				video.setTitre(request.getParameter("titre"));
				video.setUtilisateur((Integer) (request.getSession().getAttribute("idUtilisateur")));
				video.setIdVideoDuSite(request.getParameter("id"));

				// on vérifie si la langue de la video est déjà en base de données. Sinon, on
				// l'y ajoute
				Langue langue = lm.selectionnerParNom(request.getParameter("langue"));
				if (langue.getLangue() == null) {
					int idLangue = lm.ajouter(new Langue(request.getParameter("langue")));
					video.setLangue(idLangue);
				} else {
					video.setLangue(langue.getIdLangue());
				}

				video.setIdVideoWebSite(sm.selectionnerParNom(request.getParameter("site")).getidSite());

				// on vérifie si le channel de la video est déjà en base de données. Sinon, on
				// l'y ajoute
				Channel channel = cm.selectionnerParNom(request.getParameter("channel"));
				if (channel.getChannel() == null) {
					int idChannel = cm.ajouter(new Channel(request.getParameter("channel")));
					video.setNomChannel(idChannel);
				} else {
					video.setNomChannel(channel.getIdChannel());
				}

				// de même pour le propriétaire
				Proprietaire proprietaire = pm.selectionnerParNom(request.getParameter("proprietaire"));
				if (proprietaire.getProprietaire() == null) {
					int idProprietaire = pm.ajouter(new Proprietaire(request.getParameter("proprietaire")));
					video.setProprietaire(idProprietaire);
				} else {
					video.setProprietaire(proprietaire.getIdProprietaire());
				}

				// on enregistre la vidéo en base de données
				vm.ajouter(video);
			}
			message += "Vidéo enregistrée.";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Problème de connexion");
		}

		response.getWriter().write("<p>" + message + "</p>");
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("------------------------REST API doDelete-----------------------");
		
		// on cherche l'id de l'utilisateur dans la session
		HttpSession session = request.getSession();
		int idUtilisateur = Integer.parseInt(session.getAttribute("idUtilisateur").toString());
		
		// on supprime la video
		VideoManager vm = VideoManager.getInstance();
		try {
			boolean suppressionReussie = vm.deleteVideo(Integer.parseInt(request.getParameter("idVideo")), idUtilisateur);
			if (suppressionReussie) {
				response.getWriter().write("<p>Suppression de la vidéo réussie</p>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
