package fr.voirplustard.rest.api;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.voirplustard.bll.ChannelManager;
import fr.voirplustard.bll.LangueManager;
import fr.voirplustard.bll.ProprietaireManager;
import fr.voirplustard.bll.SiteManager;
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-------------API SERVLET-------------");
//		response.getWriter().write("<message>Bonjour :)</message>");
		
//		String resultat = "invalide";
//	    String valeur = request.getParameter("valeur");
//
//	    response.setContentType("text/xml");
//	    response.setHeader("Cache-Control", "no-cache");
//
//	    if ((valeur != null) && valeur.startsWith("X")) {
//	      resultat = "valide";
//	    }
//
//	    response.getWriter().write("<message>"+resultat+"</message>");
		
//		duree, description, langue, site, titre,
//		channel, proprietaire
		
		
		// on charge les manager pour aller chercher les identifiant en base de données
		LangueManager lm = LangueManager.getInstance();
		SiteManager sm = SiteManager.getInstance();
		ChannelManager cm = ChannelManager.getInstance();
		ProprietaireManager pm = ProprietaireManager.getInstance();
				
		// on crée une nouvelle video dont on rempli les attributs
		Video video = new Video();
		video.setDuree(Integer.parseInt(request.getParameter("duree")));
		video.setDescription(request.getParameter("description"));
		video.setTitre(request.getParameter("titre"));
		try {
			video.setLangue(lm.selectionnerParNom(request.getParameter("langue")).getIdLangue());
			video.setIdVideoWebSite(sm.selectionnerParNom(request.getParameter("site")).getidSite());
			video.setNomChannel(cm.selectionnerParNom(request.getParameter("channel")).getIdChannel());
			video.setProprietaire(pm.selectionnerParNom(request.getParameter("proprietaire")).getIdProprietaire());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
