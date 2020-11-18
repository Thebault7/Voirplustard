package fr.voirplustard.bll;

import java.sql.SQLException;
import java.util.List;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Video;
import fr.voirplustard.dal.DAOFactory;
import fr.voirplustard.dal.video.VideoDAO;

public class VideoManager {
	
	private VideoDAO videoDAO;
	private static VideoManager instanceVideoManager = null;
	
	private VideoManager() {
		this.videoDAO = DAOFactory.getVideoDAO();
	}
	
	public static VideoManager getInstance() {
		if (instanceVideoManager == null) {
			instanceVideoManager = new VideoManager();
		}
		return instanceVideoManager;
	}
	
	public List<Video> selectionnerParTitre(String titre, int idUtilisateur) throws SQLException, BusinessException, Exception {
		return this.videoDAO.selectionnerParTitre(titre, idUtilisateur);
	}
	
	public int chercherMaxId() throws SQLException, BusinessException, Exception {
		return this.videoDAO.chercherMaxId();
	}
	
	public int ajouter(Video video) throws SQLException, BusinessException, Exception {
		return this.videoDAO.ajouterVideo(video);
	}
	
	public boolean isVideoDejaPresente(String id, int idUtilisateur) throws SQLException, BusinessException, Exception {
		return this.videoDAO.selectionnerParIdVideoDuSite(id, idUtilisateur);
	}
	
	public Video selectionnerParId(int id, int idUtilisateur) throws SQLException, BusinessException, Exception {
		return this.videoDAO.selectionnerParId(id, idUtilisateur);
	}
	
	public void deleteVideo(int id, int idUtilisateur) throws SQLException, BusinessException, Exception {
		this.videoDAO.deleteVideo(id, idUtilisateur);
	}
}
