package fr.voirplustard.dal.video;

import java.sql.SQLException;
import java.util.List;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Video;

public interface VideoDAO {

	public List<Video> selectionnerParTitre(String titre, int idUtilisateur) throws SQLException, BusinessException;
	
	public int chercherMaxId() throws SQLException, BusinessException;
	
	public int ajouterVideo(Video video) throws SQLException, BusinessException;
	
	public boolean selectionnerParIdVideoDuSite(String id, int idUtilisateur) throws SQLException, BusinessException;
	
	public Video selectionnerParId(int id, int idUtilisateur) throws SQLException, BusinessException;
	
	public boolean deleteVideo(int id, int idUtilisateur) throws SQLException, BusinessException;
}
