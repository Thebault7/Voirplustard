package fr.voirplustard.bll;

import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Utilisateur;
import fr.voirplustard.dal.utilisateur.DAOFactory;
import fr.voirplustard.dal.utilisateur.UtilisateurDAO;

public class UtilisateurManager {

	private UtilisateurDAO utilisateurDAO;
	private static UtilisateurManager instanceUtilisateurManager = null;

	private UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}

	public static UtilisateurManager getInstance() {
		if (instanceUtilisateurManager == null) {
			instanceUtilisateurManager = new UtilisateurManager();
		}
		return instanceUtilisateurManager;
	}

	public Utilisateur selectionnerParEmail(String email) throws SQLException, BusinessException, Exception {
		return this.utilisateurDAO.selectionnerParEmail(email);
	}

	public Utilisateur selectionnerParIdentifiant(String identifiant)
			throws SQLException, BusinessException, Exception {
		return this.utilisateurDAO.selectionnerParIdentifiant(identifiant);
	}

	public int chercherMaxId() throws SQLException, BusinessException, Exception {
		return this.utilisateurDAO.chercherMaxId();
	}

	public int ajouter(Utilisateur utilisateur) throws SQLException, BusinessException, Exception {
		return this.utilisateurDAO.ajouterUtilisateur(utilisateur);
	}

	public Utilisateur verifierIdentifiantEtEmail(String identifiantEncode, String motDePasseHache)
			throws SQLException, BusinessException, Exception {
		return this.utilisateurDAO.verifierIdentifiantEtEmail(identifiantEncode, motDePasseHache);
	}
}
