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
	private static final String SELECT_MAX_ID = "SELECT MAX(id_site) FROM sites";
	private static final String INSERT_SITE = "INSERT INTO sites VALUES(?,?,?)";

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

	@Override
	public int chercherMaxId() throws SQLException, BusinessException {
		System.out.println("SiteDAOJdbcImpl - chercherMaxId");
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
			System.out.println("SiteDAOJdbcImpl - chercherMaxId - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return 0;
	}
	
	@Override
	public int ajouterSite(Site site) throws SQLException, BusinessException {
		System.out.println("SiteDAOJdbcImpl - ajouterSite");
		int maxIdNumberEnBaseDeDonnées = this.chercherMaxId();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(INSERT_SITE, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, maxIdNumberEnBaseDeDonnées + 1);
			pstmt.setString(2, site.getsite());
			pstmt.setString(3, site.getUrl());
			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				site.setidSite(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("SiteDAOJdbcImpl - ajouterUtilisateur - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return maxIdNumberEnBaseDeDonnées;
	}
}
