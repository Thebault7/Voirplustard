package fr.voirplustard.dal.utilisateur;

public abstract class DAOFactory {

	public static UtilisateurDAO getUtilisateurDAO() {
		return new UtilisateurDAOJdbcImpl();
	}
}
