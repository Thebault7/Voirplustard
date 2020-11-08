package fr.voirplustard.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		// vérification que chaque champ texte est rempli dans le formulaire
		// d'inscription
		if (request.getParameter("emailNouvelUtilisateur").contentEquals("")
				|| request.getParameter("identifiantNouvelUtilisateur").contentEquals("")
				|| request.getParameter("passwordNouvelUtilisateur").contentEquals("")
				|| request.getParameter("confirmationPasswordNouvelUtilisateur").contentEquals("")) {
			request.setAttribute("erreurFormulaire", "formulaire incomplet");
			request.setAttribute("emailNouvelUtilisateur", request.getParameter("emailNouvelUtilisateur"));
			request.setAttribute("identifiantNouvelUtilisateur", request.getParameter("identifiantNouvelUtilisateur"));
			request.setAttribute("passwordNouvelUtilisateur", request.getParameter("passwordNouvelUtilisateur"));
			request.setAttribute("confirmationPasswordNouvelUtilisateur",
					request.getParameter("confirmationPasswordNouvelUtilisateur"));
			rd = request.getRequestDispatcher("/WEB-INF/affichage/creerNouveauCompte.jsp");
			rd.forward(request, response);
			return;
		}

		// gestion des erreurs
		boolean erreur = false;

		// récupération des données renseignées par l'utilisateur
		String email = request.getParameter("emailNouvelUtilisateur");
		String identifiant = request.getParameter("identifiantNouvelUtilisateur");
		String motDePasse = request.getParameter("passwordNouvelUtilisateur");
		String confirmationMotDePasse = request.getParameter("confirmationPasswordNouvelUtilisateur");

		// vérification que les deux mots de passe fournis se correspondent bien
		if (!motDePasse.contentEquals(confirmationMotDePasse)) {
			erreur = true;
			request.setAttribute("erreurMotDePasse", "echecConfirmation");
		} else {
			UtilisateurManager um = UtilisateurManager.getInstance();
			try {
				// vérification que l'identifiant fourni n'existe pas déjà dans la base de
				// données
				Utilisateur utilisateurVerificationIdentifiant = new Utilisateur();
				utilisateurVerificationIdentifiant = um.selectionnerParIdentifiant(identifiant);
				if (utilisateurVerificationIdentifiant.getIdentifiant().equals(identifiant)) {
					erreur = true;
					request.setAttribute("erreurIdentifiant", "identifiantDejaPrit");
				}
			} catch (SQLException e) {
				System.out.println("Erreur de connexion avec la base de données.");
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("L'identifiant ne figure pas déjà dans la base de données.");
			}

			try {
				// vérification que l'email n'existe pas déjà dans la base de données
				Utilisateur utilisateurVerificationEmail = new Utilisateur();
				utilisateurVerificationEmail = um.selectionnerParIdentifiant(email);
				if (utilisateurVerificationEmail.getEmail().equals(email)) {
					erreur = true;
					request.setAttribute("erreurEmail", "emailExisteDeja");
				}
			} catch (SQLException e) {
				System.out.println("Erreur de connexion avec la base de données.");
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("L'email ne figure pas déjà dans la base de données.");
			}
		}
		
		// S'il n'y a pas d'erreur, un nouvel utilisateur est créé en base de données
		if (!erreur) {
			// hachage du mot de passe
			PasswordHachingGenerator phg = new PasswordHachingGenerator();
			String motDePasseHache = phg.hashing(motDePasse, identifiant);
			// encodage de l'identifiant et de l'email
			InterfaceEncodage ie = new Encodage();
			String identifiantEncode = ie.crypter(identifiant);
			String emailEncode = ie.crypter(email);
			// création d'un nouvel utilisateur
			Utilisateur utilisateur = new Utilisateur(identifiantEncode, emailEncode, motDePasseHache, false, true);
			// insertion de ce nouvel utilisateur dans la base de données
			try {
				UtilisateurManager um = UtilisateurManager.getInstance();
				int idUtilisateur = um.ajouter(utilisateur);
				utilisateur.setIdUtilisateur(idUtilisateur);
			} catch (SQLException e) {
				System.out.println("Erreur de connexion avec la base de données.");
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("L'utilisateur figure déjà dans la base de données.");
				e.printStackTrace();
			}
		}
	}
}
