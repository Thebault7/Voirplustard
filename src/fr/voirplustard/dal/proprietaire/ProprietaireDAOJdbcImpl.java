package fr.voirplustard.dal.proprietaire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Proprietaire;
import fr.voirplustard.dal.ConnectionProvider;

public class ProprietaireDAOJdbcImpl implements ProprietaireDAO {

	private static final String SELECT_BY_NAME = "SELECT id_proprietaire, proprietaire FROM proprietaires WHERE proprietaire=?";
	private static final String SELECT_MAX_ID = "SELECT MAX(id_proprietaire) FROM proprietaires";
	private static final String INSERT_PROPRIETAIRE = "INSERT INTO proprietaires VALUES(?,?)";
	private static final String SELECT_BY_ID = "SELECT id_proprietaire, proprietaire FROM proprietaires WHERE id_proprietaire=?";
	
	@Override
	public Proprietaire selectionnerParNom(String nomProprietaire) throws SQLException, BusinessException {
		System.out.println("ProprietaireDAOJdbcImpl - selectionnerParNom");
		Proprietaire proprietaire = new Proprietaire();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_BY_NAME);
			pstmt.setString(1, nomProprietaire);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				proprietaire.setIdProprietaire(rs.getInt(1));
				proprietaire.setProprietaire(rs.getString(2));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("ProprietaireDAOJdbcImpl - selectionnerParNom - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return proprietaire;
	}

	@Override
	public int chercherMaxId() throws SQLException, BusinessException {
		System.out.println("ProprietaireDAOJdbcImpl - chercherMaxId");
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_MAX_ID, PreparedStatement.RETURN_GENERATED_KEYS);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int i = rs.getInt(1);
				rs.close();
				pstmt.close();
				return i;
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("ProprietaireDAOJdbcImpl - chercherMaxId - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return 0;
	}

	@Override
	public int ajouterProprietaire(Proprietaire proprietaire) throws SQLException, BusinessException {
		System.out.println("ProprietaireDAOJdbcImpl - ajouterProprietaire");
		int maxIdNumberEnBaseDeDonnées = this.chercherMaxId();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(INSERT_PROPRIETAIRE, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, maxIdNumberEnBaseDeDonnées + 1);
			pstmt.setString(2, proprietaire.getProprietaire());
			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				proprietaire.setIdProprietaire(rs.getInt(1));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("ProprietaireDAOJdbcImpl - ajouterUtilisateur - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return maxIdNumberEnBaseDeDonnées + 1;
	}

	@Override
	public Proprietaire selectionnerParId(int id) throws SQLException, BusinessException {
		System.out.println("ProprietaireDAOJdbcImpl - selectionnerParId");
		Proprietaire proprietaire = new Proprietaire();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				proprietaire.setIdProprietaire(rs.getInt(1));
				proprietaire.setProprietaire(rs.getString(2));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("ProprietaireDAOJdbcImpl - selectionnerParId - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return proprietaire;
	}

}
