package fr.voirplustard.bll;

import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Channel;
import fr.voirplustard.dal.DAOFactory;
import fr.voirplustard.dal.channel.ChannelDAO;

public class ChannelManager {

	private ChannelDAO channelDAO;
	private static ChannelManager instanceChannelManager = null;
	
	private ChannelManager() {
		this.channelDAO = DAOFactory.getChannelDAO();
	}
	
	public static ChannelManager getInstance() {
		if (instanceChannelManager == null) {
			instanceChannelManager = new ChannelManager();
		}
		return instanceChannelManager;
	}
	
	public Channel selectionnerParNom(String nom) throws SQLException, BusinessException, Exception {
		return this.channelDAO.selectionnerParNom(nom);
	}
}
