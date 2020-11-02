package fr.voirplustard.bo;

import java.io.Serializable;

public class Utilisateur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idUtilisateur;
	private String identifiant;
	private String email;
	private String motDePasse;
	private boolean administrateur;
	private boolean actif;
	
	public Utilisateur() {
	}

	public Utilisateur(String identifiant, String email, String motDePasse) {
		super();
		this.identifiant = identifiant;
		this.email = email;
		this.motDePasse = motDePasse;
	}

	public Utilisateur(String identifiant, String email, String motDePasse, boolean administrateur) {
		super();
		this.identifiant = identifiant;
		this.email = email;
		this.motDePasse = motDePasse;
		this.administrateur = administrateur;
	}

	public Utilisateur(int idUtilisateur, String identifiant, String email, String motDePasse,
			boolean administrateur) {
		super();
		this.idUtilisateur = idUtilisateur;
		this.identifiant = identifiant;
		this.email = email;
		this.motDePasse = motDePasse;
		this.administrateur = administrateur;
	}

	public Utilisateur(int idUtilisateur, String identifiant, String email, String motDePasse, boolean administrateur,
			boolean actif) {
		super();
		this.idUtilisateur = idUtilisateur;
		this.identifiant = identifiant;
		this.email = email;
		this.motDePasse = motDePasse;
		this.administrateur = administrateur;
		this.actif = actif;
	}

	public int getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(int idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public boolean isAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	@Override
	public String toString() {
		return "Utilisateur [numeroUtilisateur=" + idUtilisateur + ", identifiant=" + identifiant + ", email="
				+ email + ", motDePasse=" + motDePasse + ", administrateur=" + administrateur + "]";
	}
}
