package fr.voirplustard.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bll.UtilisateurManager;
import fr.voirplustard.bo.Utilisateur;
import fr.voirplustard.service.Encodage;
import fr.voirplustard.service.InterfaceEncodage;
import fr.voirplustard.service.PasswordHachingGenerator;

/**
 * Servlet implementation class ServletConnexionUtilisateur
 */
@WebServlet("/ConnexionUtilisateur")
public class ServletConnexionUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletConnexionUtilisateur() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Servlet appelée pour initier la création d'un nouveau compte utilisateur
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/affichage/creerNouveauCompte.jsp");
		rd.forward(request, response);
	}

	/**
	 * Servlet appelée pour verifier les données fournies par l'utilisateur lors de
	 * la connexion
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd;

		// récupération des données renseignées par l'utilisateur
		String identifiantConnexion = request.getParameter("identifiantUtilisateur");
		String motDePasse = request.getParameter("passwordUtilisateur");

		// récupération du manager pour accéder à la base de données
		UtilisateurManager um = UtilisateurManager.getInstance();

		// hachage du mot de passe
		PasswordHachingGenerator phg = new PasswordHachingGenerator();
		String motDePasseHache = phg.hashing(motDePasse, identifiantConnexion);
		
		// encodage de l'identifiant
		InterfaceEncodage ie = new Encodage();
		String identifiantEncode = ie.crypter(identifiantConnexion);

		// recherche en base de données de l'utilisateur correspondant à l'identifant et
		// au mot de passe fournis
		Utilisateur utilisateur = new Utilisateur();
		try {
			utilisateur = um.verifierIdentifiantEtEmail(identifiantEncode, motDePasseHache);
			if (utilisateur.getIdUtilisateur() == 0) {
				request.setAttribute("erreurConnexion", "Echec de la connexion, aucun compte n'a été trouvé.");
				request.setAttribute("identifiant", identifiantConnexion);
				rd = request.getRequestDispatcher("/WEB-INF/affichage/accueil.jsp");
				rd.forward(request, response);
				return;
			}
		} catch (SQLException e) {
			System.out.println("Erreur de connexion avec la base de données.");
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("L'identifiant ne figure pas déjà dans la base de données.");
		}
		
		// décodage de l'identifiant, de l'email, et restauration du mot de passe
		utilisateur.setIdentifiant(identifiantConnexion);
		String emailEncode = utilisateur.getEmail();
		utilisateur.setEmail(ie.decrypter(emailEncode));
		utilisateur.setMotDePasse(motDePasse);
		
		// l'utilisateur est ajouté à la session
		HttpSession session = request.getSession();
		session.setAttribute("utilisateur", utilisateur);
		session.setAttribute("idUtilisateur", utilisateur.getIdUtilisateur());
		
		rd = request.getRequestDispatcher("/WEB-INF/affichage/accueil.jsp");
		rd.forward(request, response);
	}

}
