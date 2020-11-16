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
	 * Servlet appel�e pour initier la cr�ation d'un nouveau compte utilisateur
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
	 * Servlet appel�e pour verifier les donn�es fournies par l'utilisateur lors de
	 * la connexion
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd;

		// r�cup�ration des donn�es renseign�es par l'utilisateur
		String identifiantConnexion = request.getParameter("identifiantUtilisateur");
		String motDePasse = request.getParameter("passwordUtilisateur");

		// r�cup�ration du manager pour acc�der � la base de donn�es
		UtilisateurManager um = UtilisateurManager.getInstance();

		// hachage du mot de passe
		PasswordHachingGenerator phg = new PasswordHachingGenerator();
		String motDePasseHache = phg.hashing(motDePasse, identifiantConnexion);
		
		// encodage de l'identifiant
		InterfaceEncodage ie = new Encodage();
		String identifiantEncode = ie.crypter(identifiantConnexion);

		// recherche en base de donn�es de l'utilisateur correspondant � l'identifant et
		// au mot de passe fournis
		Utilisateur utilisateur = new Utilisateur();
		try {
			utilisateur = um.verifierIdentifiantEtEmail(identifiantEncode, motDePasseHache);
			if (utilisateur.getIdUtilisateur() == 0) {
				request.setAttribute("erreurConnexion", "Echec de la connexion, aucun compte n'a �t� trouv�.");
				request.setAttribute("identifiant", identifiantConnexion);
				rd = request.getRequestDispatcher("/WEB-INF/affichage/accueil.jsp");
				rd.forward(request, response);
				return;
			}
		} catch (SQLException e) {
			System.out.println("Erreur de connexion avec la base de donn�es.");
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("L'identifiant ne figure pas d�j� dans la base de donn�es.");
		}
		
		// d�codage de l'identifiant, de l'email, et restauration du mot de passe
		utilisateur.setIdentifiant(identifiantConnexion);
		String emailEncode = utilisateur.getEmail();
		utilisateur.setEmail(ie.decrypter(emailEncode));
		utilisateur.setMotDePasse(motDePasse);
		
		// l'utilisateur est ajout� � la session
		HttpSession session = request.getSession();
		session.setAttribute("utilisateur", utilisateur);
		session.setAttribute("idUtilisateur", utilisateur.getIdUtilisateur());
		
		rd = request.getRequestDispatcher("/WEB-INF/affichage/accueil.jsp");
		rd.forward(request, response);
	}

}
