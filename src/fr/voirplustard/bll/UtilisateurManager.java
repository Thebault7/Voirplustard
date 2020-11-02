package fr.voirplustard.bll;

import fr.voirplustard.dal.DAOFactory;
import fr.voirplustard.dal.UtilisateurDAO;

public class UtilisateurManager {

	private UtilisateurDAO utilisateurDAO;
	private static UtilisateurManager instanceUtilisateurManager = null;
	
	private UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}
}
