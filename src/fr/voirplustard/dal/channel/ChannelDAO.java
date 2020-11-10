package fr.voirplustard.dal.channel;

import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Channel;

public interface ChannelDAO {

	public Channel selectionnerParNom(String nom)  throws SQLException, BusinessException;
}
