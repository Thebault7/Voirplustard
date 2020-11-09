package fr.voirplustard.dal.video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.voirplustard.bo.Video;
import fr.voirplustard.dal.ConnectionProvider;

public class VideoDAOJdbcImpl implements VideoDAO {
	
	private static final String SELECT_BY_TITLE = "SELECT id_video, duree, description, langue, id_dailymotion, url, titre, nom_channel, proprietaire, utilisateur FROM videos WHERE titre LIKE ?";

	@Override
	public Video selectionnerParTitre(String titre) throws SQLException {
		System.out.println("VideoDAOJdbcImpl - selectionnerParTitre");
		Video video = new Video();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_BY_TITLE, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, "%" + titre + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				video.setIdVideo(rs.getInt(1));
				video.setDuree(rs.getInt(2));
				video.setDescription(rs.getString(3));
				video.setLangue(rs.getInt(4));
				video.setIdVideoWebSite(rs.getInt(5));
				video.setTitre(rs.getString(6));
				video.setNomChannel(rs.getInt(7));
				video.setProprietaire(rs.getInt(8));
				video.setUtilisateur(rs.getInt(9));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("VideoDAOJdbcImpl - selectionnerParTitre - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return video;
	}
}
