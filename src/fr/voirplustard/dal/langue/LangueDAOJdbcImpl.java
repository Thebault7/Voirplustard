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

}
