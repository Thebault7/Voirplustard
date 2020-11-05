package fr.voirplustard.dal.utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Utilisateur;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	private static final String SELECT_BY_EMAIL = "SELECT id_utilisateur, identifiant, email, mot_de_passe, is_administrateur, is_actif FROM utilisateurs WHERE email=?";
	private static final String SELECT_BY_PSEUDO = "SELECT id_utilisateur, identifiant, email, mot_de_passe, is_administrateur, is_actif FROM utilisateurs WHERE identifiant=?";

	/**
	 * {@inheritDoc}
	 * 
	 * @throws SQLException, BusinessException
	 * 
	 * @see fr.voirplustard.dal.utilisateur.UtilisateurDAO#selectByEMail(java.lang.String)
	 */
	@Override
	public Utilisateur selectionnerParEMail(String email) throws SQLException {
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
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return utilisateur;
	}

	@Override
	public Utilisateur selectionnerParIdentifiant(String identifiant) throws SQLException {
		Utilisateur utilisateur = new Utilisateur();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_BY_PSEUDO, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1,  identifiant);
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
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return utilisateur;
	}
}
