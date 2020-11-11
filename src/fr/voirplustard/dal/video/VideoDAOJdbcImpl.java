package fr.voirplustard.dal.video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Video;
import fr.voirplustard.dal.ConnectionProvider;

public class VideoDAOJdbcImpl implements VideoDAO {
	
	private static final String SELECT_BY_TITLE = "SELECT id_video, duree, description, id_langue, id_site, titre, id_channel, id_proprietaire, id_utilisateur FROM videos WHERE titre LIKE ?";
	private static final String SELECT_MAX_ID = "SELECT MAX(id_video) FROM videos";
	private static final String INSERT_VIDEO = "INSERT INTO videos VALUES(?,?,?,?,?,?,?,?,?)";

	@Override
	public List<Video> selectionnerParTitre(String titre) throws SQLException {
		System.out.println("VideoDAOJdbcImpl - selectionnerParTitre");
		List<Video> listVideos = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_BY_TITLE, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, "%" + titre + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Video video = new Video();
				video.setIdVideo(rs.getInt(1));
				video.setDuree(rs.getInt(2));
				video.setDescription(rs.getString(3));
				video.setLangue(rs.getInt(4));
				video.setIdVideoWebSite(rs.getInt(5));
				video.setTitre(rs.getString(6));
				video.setNomChannel(rs.getInt(7));
				video.setProprietaire(rs.getInt(8));
				video.setUtilisateur(rs.getInt(9));
				listVideos.add(video);
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
		return listVideos;
	}

	@Override
	public int chercherMaxId() throws SQLException, BusinessException {
		System.out.println("VideoDAOJdbcImpl - chercherMaxId");
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_MAX_ID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("VideoDAOJdbcImpl - chercherMaxId - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
	}
	
	@Override
	public void ajouterVideo(Video video) throws SQLException, BusinessException {
		System.out.println("VideoDAOJdbcImpl - ajouterVideo");
		int maxIdNumberEnBaseDeDonnees = this.chercherMaxId();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(INSERT_VIDEO, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, maxIdNumberEnBaseDeDonnees + 1);
			pstmt.setInt(2, video.getDuree());
			pstmt.setString(3, video.getDescription());
			pstmt.setInt(4, video.getLangue());
			pstmt.setInt(5, video.getIdVideoWebSite());
			pstmt.setString(6, video.getTitre());
			pstmt.setInt(7, video.getNomChannel());
			pstmt.setInt(8, video.getProprietaire());
			pstmt.setInt(9, video.getUtilisateur());
			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				video.setIdVideo(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("VideoDAOJdbcImpl - ajouterVideo - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
	}
}
