package fr.voirplustard.dal;

import fr.voirplustard.dal.channel.ChannelDAO;
import fr.voirplustard.dal.channel.ChannelDAOJdbcImpl;
import fr.voirplustard.dal.langue.LangueDAO;
import fr.voirplustard.dal.langue.LangueDAOJdbcImpl;
import fr.voirplustard.dal.proprietaire.ProprietaireDAO;
import fr.voirplustard.dal.proprietaire.ProprietaireDAOJdbcImpl;
import fr.voirplustard.dal.site.SiteDAO;
import fr.voirplustard.dal.site.SiteDAOJdbcImpl;
import fr.voirplustard.dal.utilisateur.UtilisateurDAO;
import fr.voirplustard.dal.utilisateur.UtilisateurDAOJdbcImpl;
import fr.voirplustard.dal.video.VideoDAO;
import fr.voirplustard.dal.video.VideoDAOJdbcImpl;

public abstract class DAOFactory {
	
	public static ChannelDAO getChannelDAO() {
		return new ChannelDAOJdbcImpl();
	}
	
	public static LangueDAO getLangueDAO() {
		return new LangueDAOJdbcImpl();
	}

	public static ProprietaireDAO getProprietaireDAO() {
		return new ProprietaireDAOJdbcImpl();
	}
	
	public static SiteDAO getSiteDAO() {
		return new SiteDAOJdbcImpl();
	}
	
	public static UtilisateurDAO getUtilisateurDAO() {
		return new UtilisateurDAOJdbcImpl();
	}
	
	public static VideoDAO getVideoDAO() {
		return new VideoDAOJdbcImpl();
	}
}
