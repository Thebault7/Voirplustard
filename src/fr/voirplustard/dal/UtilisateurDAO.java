package fr.voirplustard.dal;

import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Utilisateur;

public interface UtilisateurDAO {

	public Utilisateur selectByEMail(String email) throws SQLException, BusinessException;
}
