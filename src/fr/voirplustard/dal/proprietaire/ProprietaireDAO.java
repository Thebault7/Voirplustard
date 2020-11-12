package fr.voirplustard.dal.proprietaire;

import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Proprietaire;

public interface ProprietaireDAO {

	public Proprietaire selectionnerParNom(String proprietaire) throws SQLException, BusinessException;
	
	public int chercherMaxId() throws SQLException, BusinessException;
	
	public int ajouterProprietaire(Proprietaire proprietaire) throws SQLException, BusinessException;
}
