package fr.voirplustard.dal.utilisateur;

import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Utilisateur;

public interface UtilisateurDAO {
	
	public Utilisateur selectionnerParEMail(String email) throws SQLException, BusinessException;

	public Utilisateur selectionnerParIdentifiant(String identifiant) throws SQLException, BusinessException;
}
