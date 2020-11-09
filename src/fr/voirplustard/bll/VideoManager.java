package fr.voirplustard.bll;

import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Video;
import fr.voirplustard.dal.video.DAOFactory;
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
	
	public Video selectionnerParTitre(String titre) throws SQLException, BusinessException, Exception {
		return this.videoDAO.selectionnerParTitre(titre);
	}
}
