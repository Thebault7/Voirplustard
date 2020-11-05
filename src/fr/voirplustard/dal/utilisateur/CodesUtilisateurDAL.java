package fr.voirplustard.dal.utilisateur;

/**
 * Les codes disponibles sont entre 10000 et 19999
 */
public abstract class CodesUtilisateurDAL {

	/**
	 * Echec g�n�ral quand tentative d'ajouter un objet null
	 */
	public static final int INSERT_UTILISATEUR_NULL = 10000;

	/**
	 * Echec g�n�ral quand erreur non g�r�e � l'insertion
	 */
	public static final int INSERT_UTILISATEUR_ECHEC = 10001;

	/**
	 * Echec de la lecture des utilisateurs
	 */
	public static final int LECTURE_UTILISATEUR_ECHEC = 10002;

	/**
	 * Echec de la suppression d'un utilisateur
	 */
	public static final int DELETE_UTILISATEUR_ECHEC = 10003;
}