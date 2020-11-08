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
 * Servlet implementation class ServletCreationCompte
 */
@WebServlet("/CreationCompte")
public class ServletCreationCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletCreationCompte() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd;

		// v�rification que chaque champ texte est rempli dans le formulaire
		// d'inscription
		if (request.getParameter("emailNouvelUtilisateur").contentEquals("")
				|| request.getParameter("identifiantNouvelUtilisateur").contentEquals("")
				|| request.getParameter("passwordNouvelUtilisateur").contentEquals("")
				|| request.getParameter("confirmationPasswordNouvelUtilisateur").contentEquals("")) {
			request.setAttribute("erreurFormulaire", "Formulaire incomplet");
			request.setAttribute("emailNouvelUtilisateur", request.getParameter("emailNouvelUtilisateur"));
			request.setAttribute("identifiantNouvelUtilisateur", request.getParameter("identifiantNouvelUtilisateur"));
//			request.setAttribute("passwordNouvelUtilisateur", request.getParameter("passwordNouvelUtilisateur"));
//			request.setAttribute("confirmationPasswordNouvelUtilisateur",
//					request.getParameter("confirmationPasswordNouvelUtilisateur"));
			rd = request.getRequestDispatcher("/WEB-INF/affichage/creerNouveauCompte.jsp");
			rd.forward(request, response);
			return;
		}

		// gestion des erreurs
		boolean erreur = false;

		// r�cup�ration des donn�es renseign�es par l'utilisateur
		String email = request.getParameter("emailNouvelUtilisateur");
		String identifiant = request.getParameter("identifiantNouvelUtilisateur");
		String motDePasse = request.getParameter("passwordNouvelUtilisateur");
		String confirmationMotDePasse = request.getParameter("confirmationPasswordNouvelUtilisateur");

		// v�rification que les deux mots de passe fournis se correspondent bien
		if (!motDePasse.contentEquals(confirmationMotDePasse)) {
			erreur = true;
			request.setAttribute("erreurMotDePasse", "Erreur dans la confirmation du mot de passe.");
		}

		// instanciation du manager pour acc�der � la base de donn�es
		UtilisateurManager um = UtilisateurManager.getInstance();
		
		// v�rification que l'identifiant fourni n'existe pas d�j� dans la base de
		// donn�es
		try {
			Utilisateur utilisateurVerificationIdentifiant = new Utilisateur();
			utilisateurVerificationIdentifiant = um.selectionnerParIdentifiant(identifiant);
			if (utilisateurVerificationIdentifiant.getIdentifiant().equals(identifiant)) {
				erreur = true;
				request.setAttribute("erreurIdentifiant",
						"Cet identifiant est d�j� pris. Veuillez en choisir un autre.");
			}
		} catch (SQLException e) {
			System.out.println("Erreur de connexion avec la base de donn�es.");
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("L'identifiant ne figure pas d�j� dans la base de donn�es.");
		}

		// v�rification que l'email n'existe pas d�j� dans la base de donn�es
		try {
			Utilisateur utilisateurVerificationEmail = new Utilisateur();
			utilisateurVerificationEmail = um.selectionnerParEmail(email);
			if (utilisateurVerificationEmail.getEmail().equals(email)) {
				erreur = true;
				request.setAttribute("erreurEmail", "Cet email a d�j� �t� utilis� sur ce site.");
			}
		} catch (SQLException e) {
			System.out.println("Erreur de connexion avec la base de donn�es.");
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("L'email ne figure pas d�j� dans la base de donn�es.");
		}

		// S'il n'y a pas d'erreur, un nouvel utilisateur est cr�� en base de donn�es
		if (!erreur) {
			// hachage du mot de passe
			PasswordHachingGenerator phg = new PasswordHachingGenerator();
			String motDePasseHache = phg.hashing(motDePasse, identifiant);
			// encodage de l'identifiant et de l'email
			InterfaceEncodage ie = new Encodage();
			String identifiantEncode = ie.crypter(identifiant);
			String emailEncode = ie.crypter(email);
			// cr�ation d'un nouvel utilisateur
			Utilisateur utilisateur = new Utilisateur(identifiantEncode, emailEncode, motDePasseHache, false, true);
			// insertion de ce nouvel utilisateur dans la base de donn�es
			try {
				int idUtilisateur = um.ajouter(utilisateur);
				utilisateur.setIdUtilisateur(idUtilisateur);
			} catch (SQLException e) {
				System.out.println("Erreur de connexion avec la base de donn�es.");
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("L'utilisateur figure d�j� dans la base de donn�es.");
				e.printStackTrace();
			}
			// l'utilisateur est ajout� � la session
			HttpSession session = request.getSession();
			session.setAttribute("utilisateur", utilisateur);
			// l'utilisateur est ensuite r�orient� vers la page d'accueil
			rd = request.getRequestDispatcher("/WEB-INF/affichage/accueil.jsp");
			rd.forward(request, response);
		}

		// s'il y a une erreur dans les donn�es fournies par l'utilisateur, celui-ci est
		// envoy�e vers le formulaire d'inscription avec un message d'erreur � afficher.
		if (erreur) {
			rd = request.getRequestDispatcher("/WEB-INF/affichage/creerNouveauCompte.jsp");
			rd.forward(request, response);
		}
	}
}
