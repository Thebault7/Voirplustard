package fr.voirplustard.dal.site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Site;
import fr.voirplustard.dal.ConnectionProvider;

public class SiteDAOJdbcImpl implements SiteDAO {
	
	private static final String SELECt_BY_NAME = "SELECT id_site, site, url FROM sites WHERE site=?";

	@Override
	public Site selectionnerParNom(String nomSite) throws SQLException, BusinessException {
		System.out.println("SiteDAOJdbcImpl - selectionnerParNom");
		Site site = new Site();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECt_BY_NAME);
			pstmt.setString(1, nomSite);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				site.setidSite(rs.getInt(1));
				site.setsite(rs.getString(2));
				site.setUrl(rs.getString(3));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("SiteDAOJdbcImpl - selectionnerParNom - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return site;
	}
}
