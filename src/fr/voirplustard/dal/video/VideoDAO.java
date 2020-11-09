package fr.voirplustard.dal.video;

import java.sql.SQLException;

import fr.voirplustard.bo.Video;

public interface VideoDAO {

	public Video selectionnerParTitre(String titre) throws SQLException;
}
