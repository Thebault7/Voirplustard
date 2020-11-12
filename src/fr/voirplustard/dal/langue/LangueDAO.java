package fr.voirplustard.dal.langue;

import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Langue;

public interface LangueDAO {

	public Langue selectionnerParNom(String nom) throws SQLException, BusinessException;
	
	public int chercherMaxId() throws SQLException, BusinessException;
	
	public int ajouterLangue(Langue langue) throws SQLException, BusinessException;
}
