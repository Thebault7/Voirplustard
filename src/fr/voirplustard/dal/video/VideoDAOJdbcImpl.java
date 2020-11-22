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
	
	private static final String SELECT_BY_TITLE = "SELECT id_video, duree, description, id_langue, id_site, id_video_du_site, titre, id_channel, id_proprietaire, id_utilisateur FROM videos WHERE titre LIKE ? AND id_utilisateur=?";
	private static final String SELECT_MAX_ID = "SELECT MAX(id_video) FROM videos";
	private static final String INSERT_VIDEO = "INSERT INTO videos VALUES(?,?,?,?,?,?,?,?,?,?)";
	private static final String SELECT_BY_ID_VIDEO_DU_SITE = "SELECT id_video_du_site FROM videos WHERE id_video_du_site=? AND id_utilisateur=?";
	private static final String SELECT_BY_ID = "SELECT id_video, duree, description, id_langue, id_site, id_video_du_site, titre, id_channel, id_proprietaire, id_utilisateur FROM videos WHERE id_video=? AND id_utilisateur=?";
	private static final String DELETE_BY_ID = "DELETE FROM videos WHERE id_video=? AND id_utilisateur=?";
	
	@Override
	public List<Video> selectionnerParTitre(String titre, int idUtilisateur) throws SQLException {
		System.out.println("VideoDAOJdbcImpl - selectionnerParTitre");
		List<Video> listVideos = new ArrayList<>();
		
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_BY_TITLE, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, "%" + titre + "%");
			pstmt.setInt(2, idUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Video video = new Video();
				video.setIdVideo(rs.getInt(1));
				video.setDuree(rs.getInt(2));
				video.setDescription(rs.getString(3));
				video.setLangue(rs.getInt(4));
				video.setIdVideoWebSite(rs.getInt(5));
				video.setIdVideoDuSite(rs.getString(6));
				video.setTitre(rs.getString(7));
				video.setNomChannel(rs.getInt(8));
				video.setProprietaire(rs.getInt(9));
				video.setUtilisateur(rs.getInt(10));
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
				int i = rs.getInt(1);
				rs.close();
				pstmt.close();
				return i;
			}
			rs.close();
			pstmt.close();
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
	public int ajouterVideo(Video video) throws SQLException, BusinessException {
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
			pstmt.setString(6, video.getIdVideoDuSite());
			pstmt.setString(7, video.getTitre());
			pstmt.setInt(8, video.getNomChannel());
			pstmt.setInt(9, video.getProprietaire());
			pstmt.setInt(10, video.getUtilisateur());
			pstmt.execute();
//			ResultSet rs = pstmt.getGeneratedKeys();
//			if (rs != null && rs.next()) {
//				video.setIdVideo(rs.getInt(1));
//			}
//			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("VideoDAOJdbcImpl - ajouterVideo - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return maxIdNumberEnBaseDeDonnees + 1;
	}

	@Override
	public boolean selectionnerParIdVideoDuSite(String id, int idUtilisateur) throws SQLException, BusinessException {
		System.out.println("VideoDAOJdbcImpl - selectionnerParIdVideoDuSite");
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_BY_ID_VIDEO_DU_SITE, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, id);
			pstmt.setInt(2, idUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				rs.close();
				pstmt.close();
				return true;
			}
			rs.close();
			pstmt.close();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("VideoDAOJdbcImpl - selectionnerParIdVideoDuSite - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
	}

	@Override
	public Video selectionnerParId(int idVideo, int idUtilisateur) throws SQLException, BusinessException {
		System.out.println("VideoDAOJdbcImpl - selectionnerParId");
		Video video = new Video();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_BY_ID, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, idVideo);
			pstmt.setInt(2, idUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				video.setIdVideo(rs.getInt(1));
				video.setDuree(rs.getInt(2));
				video.setDescription(rs.getString(3));
				video.setLangue(rs.getInt(4));
				video.setIdVideoWebSite(rs.getInt(5));
				video.setIdVideoDuSite(rs.getString(6));
				video.setTitre(rs.getString(7));
				video.setNomChannel(rs.getInt(8));
				video.setProprietaire(rs.getInt(9));
				video.setUtilisateur(rs.getInt(10));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("VideoDAOJdbcImpl - selectionnerParId - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return video;
	}

	@Override
	public boolean deleteVideo(int idVideo, int idUtilisateur) throws SQLException, BusinessException {
		System.out.println("VideoDAOJdbcImpl - deleteVideo");
		try (Connection cnx = ConnectionProvider.getConnection()) {
			cnx.setAutoCommit(false);
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(DELETE_BY_ID);
			pstmt.setInt(1, idVideo);
			pstmt.setInt(2, idUtilisateur);
			pstmt.executeUpdate();
			pstmt.close();
			cnx.commit();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("VideoDAOJdbcImpl - selectionnerParId - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Echec de la suppression de la vidéo.");
			throw e;
		}
		return true;
	}
}
