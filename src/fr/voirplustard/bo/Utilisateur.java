package fr.voirplustard.bo;

import java.io.Serializable;

public class Utilisateur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int numeroUtilisateur;
	private String identifiant;
	private String email;
	private String motDePasse;
	private boolean administrateur;
	
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

	public Utilisateur(int numeroUtilisateur, String identifiant, String email, String motDePasse,
			boolean administrateur) {
		super();
		this.numeroUtilisateur = numeroUtilisateur;
		this.identifiant = identifiant;
		this.email = email;
		this.motDePasse = motDePasse;
		this.administrateur = administrateur;
	}

	public int getNumeroUtilisateur() {
		return numeroUtilisateur;
	}

	public void setNumeroUtilisateur(int numeroUtilisateur) {
		this.numeroUtilisateur = numeroUtilisateur;
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

	@Override
	public String toString() {
		return "Utilisateur [numeroUtilisateur=" + numeroUtilisateur + ", identifiant=" + identifiant + ", email="
				+ email + ", motDePasse=" + motDePasse + ", administrateur=" + administrateur + "]";
	}
}
