package fr.voirplustard.dal.channel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Channel;
import fr.voirplustard.dal.ConnectionProvider;

public class ChannelDAOJdbcImpl implements ChannelDAO {
	
	private static final String SELECT_BY_NAME = "SELECT id_channel, channel FROM channels WHERE channel=?";

	@Override
	public Channel selectionnerParNom(String nom) throws SQLException, BusinessException {
		System.out.println("ChannelDAOJdbcImpl - selectionnerParNom");
		Channel channel = new Channel();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_BY_NAME);
			pstmt.setString(1, nom);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				channel.setIdChannel(rs.getInt(1));
				channel.setChannel(rs.getString(2));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("ChannelDAOJdbcImpl - selectionnerParNom - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return channel;
	}
}
