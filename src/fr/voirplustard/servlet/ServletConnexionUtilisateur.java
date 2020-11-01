package fr.voirplustard.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.voirplustard.bo.Utilisateur;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/affichage/creerNouveauCompte.jsp");
		Utilisateur utilisateur = new Utilisateur();
		request.setAttribute("utilisateur", utilisateur);
		rd.forward(request, response);
	}

	/**
	 * Servlet appelée pour verifier les données fournies par l'utilisateur lors de la connexion
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
