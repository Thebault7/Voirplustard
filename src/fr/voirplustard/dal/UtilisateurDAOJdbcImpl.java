package fr.voirplustard.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.voirplustard.bo.Utilisateur;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	private static final String SELECT_BY_EMAIL = "SELECT * FROM utilisateurs WHERE email=?";
	
	/**
	 * {@inheritDoc}
	 * 
	 * @throws SQLException, BusinessException
	 * 
	 * @see fr.voirplustard.dal.UtilisateurDAO#selectByEMail(java.lang.String)
	 */
	@Override
	public Utilisateur selectByEMail(String email) throws SQLException {
		Utilisateur utilisateur = new Utilisateur();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_BY_EMAIL, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				utilisateur.setIdUtilisateur(rs.getInt(1));
				utilisateur.setIdentifiant(rs.getString(2));
				utilisateur.setEmail(rs.getString(3));
				utilisateur.setMotDePasse(rs.getString(4));
				utilisateur.setAdministrateur(rs.getBoolean(5));
				utilisateur.setActif(rs.getBoolean(6));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			//TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return utilisateur;
	}
}
