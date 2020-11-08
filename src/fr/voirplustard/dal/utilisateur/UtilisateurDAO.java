package fr.voirplustard.dal.utilisateur;

import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Utilisateur;

public interface UtilisateurDAO {
	
	public Utilisateur selectionnerParEmail(String email) throws SQLException, BusinessException;

	public Utilisateur selectionnerParIdentifiant(String identifiant) throws SQLException, BusinessException;
	
	public int chercherMaxId() throws SQLException, BusinessException;

	public int ajouterUtilisateur(Utilisateur utilisateur) throws SQLException, BusinessException;
	
	public Utilisateur verifierIdentifiantEtEmail(String identifiantEncode, String motDePasseHache) throws SQLException, BusinessException;
}
