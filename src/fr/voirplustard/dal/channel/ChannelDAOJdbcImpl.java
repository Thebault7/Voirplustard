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
	private static final String SELECT_MAX_ID = "SELECT MAX(id_channel) FROM channels";
	private static final String INSERT_CHANNEL = "INSERT INTO channels VALUES(?,?)";
	private static final String SELECT_BY_ID = "SELECT id_channel, channel FROM channels WHERE id_channel=?";

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

	@Override
	public int chercherMaxId() throws SQLException, BusinessException {
		System.out.println("ChannelDAOJdbcImpl - chercherMaxId");
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_MAX_ID, PreparedStatement.RETURN_GENERATED_KEYS);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("ChannelDAOJdbcImpl - chercherMaxId - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return 0;
	}

	@Override
	public int ajouterChannel(Channel channel) throws SQLException, BusinessException {
		System.out.println("ChannelDAOJdbcImpl - ajouterChannel");
		int maxIdNumberEnBaseDeDonnées = this.chercherMaxId();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(INSERT_CHANNEL, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, maxIdNumberEnBaseDeDonnées + 1);
			pstmt.setString(2, channel.getChannel());
			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				channel.setIdChannel(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("ChannelDAOJdbcImpl - ajouterUtilisateur - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return maxIdNumberEnBaseDeDonnées;
	}

	@Override
	public Channel selectionnerParId(int id) throws SQLException, BusinessException {
		System.out.println("ChannelDAOJdbcImpl - selectionnerParId");
		Channel channel = new Channel();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setInt(1, id);
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
			System.out.println("ChannelDAOJdbcImpl - selectionnerParId - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return channel;
	}
}
