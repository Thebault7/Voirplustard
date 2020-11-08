package fr.voirplustard.dal.utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.voirplustard.BusinessException;
import fr.voirplustard.bo.Utilisateur;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	private static final String SELECT_BY_EMAIL = "SELECT id_utilisateur, identifiant, email, mot_de_passe, is_administrateur, is_actif FROM utilisateurs WHERE email=? AND is_actif=1";
	private static final String SELECT_BY_PSEUDO = "SELECT id_utilisateur, identifiant, email, mot_de_passe, is_administrateur, is_actif FROM utilisateurs WHERE identifiant=? AND is_actif=1";
	private static final String SELECT_MAX_ID = "SELECT MAX(id_utilisateur) FROM utilisateurs";
	private static final String INSERT_UTILISATEUR = "INSERT INTO utilisateurs VALUES(?,?,?,?,?,?)";
	private static final String SELECT_PSEUDO_AND_PASSWORD = "SELECT id_utilisateur, identifiant, email, mot_de_passe, is_administrateur, is_actif FROM utilisateurs WHERE identifiant LIKE ? AND mot_de_passe=? AND is_actif=1";

	/**
	 * {@inheritDoc}
	 * 
	 * @throws SQLException, BusinessException
	 * 
	 * @see fr.voirplustard.dal.utilisateur.UtilisateurDAO#selectByEMail(java.lang.String)
	 */
	@Override
	public Utilisateur selectionnerParEmail(String email) throws SQLException {
		System.out.println("UtilisateurDAOJdbcImpl - selectionnerParEMail");
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
			System.out.println("UtilisateurDAOJdbcImpl - selectionnerParEMail - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return utilisateur;
	}

	@Override
	public Utilisateur selectionnerParIdentifiant(String identifiant) throws SQLException {
		System.out.println("UtilisateurDAOJdbcImpl - selectionnerParIdentifiant");
		Utilisateur utilisateur = new Utilisateur();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_BY_PSEUDO, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, identifiant);
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
			System.out.println("UtilisateurDAOJdbcImpl - selectionnerParIdentifiant - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return utilisateur;
	}

	@Override
	public int chercherMaxId() throws SQLException {
		System.out.println("UtilisateurDAOJdbcImpl - chercherMaxId");
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_MAX_ID, PreparedStatement.RETURN_GENERATED_KEYS);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("UtilisateurDAOJdbcImpl - chercherMaxId - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
	}

	@Override
	public int ajouterUtilisateur(Utilisateur utilisateur) throws SQLException {
		System.out.println("UtilisateurDAOJdbcImpl - ajouterUtilisateur");
		int maxIdNumberEnBaseDeDonnees = this.chercherMaxId();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(INSERT_UTILISATEUR, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, maxIdNumberEnBaseDeDonnees + 1);
			pstmt.setString(2, utilisateur.getIdentifiant());
			pstmt.setString(3, utilisateur.getEmail());
			pstmt.setString(4, utilisateur.getMotDePasse());
			pstmt.setBoolean(5, utilisateur.isAdministrateur());
			pstmt.setBoolean(6, utilisateur.isActif());
			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				utilisateur.setIdUtilisateur(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("UtilisateurDAOJdbcImpl - ajouterUtilisateur - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
		return maxIdNumberEnBaseDeDonnees + 1;
	}

	@Override
	public Utilisateur verifierIdentifiantEtEmail(String identifiantEncode, String motDePasseHache)
			throws SQLException, BusinessException {
		System.out.println("UtilisateurDAOJdbcImpl - verifierIdentifiantEtEmail");
		String nouvelIdentifiantEncode = "%" + identifiantEncode.substring(1);
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt;
			pstmt = cnx.prepareStatement(SELECT_PSEUDO_AND_PASSWORD, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, nouvelIdentifiantEncode);
			pstmt.setString(2, motDePasseHache);
			ResultSet rs = pstmt.executeQuery();
			Utilisateur utilisateur = new Utilisateur();
			if (rs.next()) {
				utilisateur.setIdUtilisateur(rs.getInt(1));
				utilisateur.setIdentifiant(rs.getString(2));
				utilisateur.setEmail(rs.getString(3));
				utilisateur.setMotDePasse(rs.getString(4));
				utilisateur.setAdministrateur(rs.getBoolean(5));
				utilisateur.setActif(rs.getBoolean(6));
			}
			return utilisateur;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur, échec de la connexion.");
			System.out.println("UtilisateurDAOJdbcImpl - verifierIdentifiantEtEmail - SQLException");
			// TODO faire remonter l'erreur à l'utilisateur
			throw e;
		}
	}
}
