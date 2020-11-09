package fr.voirplustard.dal.video;

public abstract class DAOFactory {

	public static VideoDAO getVideoDAO() {
		return new VideoDAOJdbcImpl();
	}
}
