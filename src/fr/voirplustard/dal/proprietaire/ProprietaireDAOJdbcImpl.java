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

}
