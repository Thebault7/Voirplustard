package fr.voirplustard.dal.langue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Langue;
import fr.voirplustard.dal.ConnectionProvider;

public class LangueDAOJdbcImpl implements LangueDAO {
	
	private static final String SELECT_BY_NAME = "SELECT id_langue, langue, description FROM langues WHERE langue=?";
	private static final String SELECT_MAX_ID = "SELECT MAX(id_langue) FROM langues";
	private static final String INSERT_LANGUE = "INSERT INTO langues VALUES(?,?,?)";
	private static final String SELECT_BY_ID = "SELECT id_langue, langue, description FROM langues WHERE id_langue=?";

	@Override
	public Langue selectionnerParNom(String nom) throws SQLException, BusinessException {
		System.out.println("LangueDAOJdbcImpl - selectionnerParNom");
		Langue langue = new Langue();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_BY_NAME);
			pstmt.setString(1, nom);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				langue.setIdLangue(rs.getInt(1));
				langue.setLangue(rs.getString(2));
				langue.setDescription(rs.getString(3));
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
		return langue;
	}

	@Override
	public int chercherMaxId() throws SQLException, BusinessException {
		System.out.println("LangueDAOJdbcImpl - chercherMaxId");
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
			System.out.println("LangueDAOJdbcImpl - chercherMaxId - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return 0;
	}

	@Override
	public int ajouterLangue(Langue langue) throws SQLException, BusinessException {
		System.out.println("LangueDAOJdbcImpl - ajouterLangue");
		int maxIdNumberEnBaseDeDonnées = this.chercherMaxId();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(INSERT_LANGUE, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, maxIdNumberEnBaseDeDonnées + 1);
			pstmt.setString(2, langue.getLangue());
			pstmt.setString(3, langue.getDescription());
			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				langue.setIdLangue(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("LangueDAOJdbcImpl - ajouterUtilisateur - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return maxIdNumberEnBaseDeDonnées;
	}

	@Override
	public Langue selectionnerParId(int id) throws SQLException, BusinessException {
		System.out.println("LangueDAOJdbcImpl - selectionnerParId");
		Langue langue = new Langue();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				langue.setIdLangue(rs.getInt(1));
				langue.setLangue(rs.getString(2));
				langue.setDescription(rs.getString(3));
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
		return langue;
	}

}
