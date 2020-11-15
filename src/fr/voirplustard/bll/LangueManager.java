package fr.voirplustard.bll;

import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Langue;
import fr.voirplustard.dal.DAOFactory;
import fr.voirplustard.dal.langue.LangueDAO;

public class LangueManager {

	private LangueDAO langueDAO;
	private static LangueManager instanceLangueManager = null;
	
	private LangueManager() {
		this.langueDAO = DAOFactory.getLangueDAO();
	}
	
	public static LangueManager getInstance() {
		if (instanceLangueManager == null) {
			instanceLangueManager = new LangueManager();
		}
		return instanceLangueManager;
	}
	
	public Langue selectionnerParNom(String nom) throws SQLException, BusinessException, Exception {
		return this.langueDAO.selectionnerParNom(nom);
	}
}
