package fr.voirplustard.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bll.VideoManager;
import fr.voirplustard.bo.Video;

/**
 * Servlet implementation class ServletChargerVideo
 */
@WebServlet("/ChargerVideo")
public class ServletChargerVideo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletChargerVideo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd;

		// récupération du titre fournit par l'utilisateur
		String titreFournit = request.getParameter("texteAChercher");

		// récupération du manager pour accéder à la base de données
		VideoManager vm = VideoManager.getInstance();

		// recherche des vidéos correspondant au titre fournit par l'utilisateur
		List<Video> listeVideo = new ArrayList<>();
		try {
			listeVideo = vm.selectionnerParTitre(titreFournit);
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
			request.setAttribute("listeVide", "Aucune vidéo n'a été trouvée.");
			rd = request.getRequestDispatcher("/WEB-INF/affichage/accueil.jsp");
			rd.forward(request, response);
			return;
		}
		
		// on envoie la liste vers la jsp
		request.setAttribute("listeVideo", listeVideo);
		rd = request.getRequestDispatcher("/WEB-INF/affichage/accueil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
