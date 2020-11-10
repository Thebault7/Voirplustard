package fr.voirplustard.bll;

import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Site;
import fr.voirplustard.dal.DAOFactory;
import fr.voirplustard.dal.site.SiteDAO;

public class SiteManager {

	private SiteDAO siteDAO;
	private static SiteManager instanceSiteManager = null;
	
	private SiteManager() {
		this.siteDAO = DAOFactory.getSiteDAO();
	}
	
	public static SiteManager getInstance() {
		if (instanceSiteManager == null) {
			instanceSiteManager = new SiteManager();
		}
		return instanceSiteManager;
	}
	
	public Site selectionnerParNom(String nom) throws SQLException, BusinessException, Exception {
		return this.siteDAO.selectionnerParNom(nom);
	}
}
