package fr.voirplustard.bo;

import java.io.Serializable;

public class Proprietaire implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int idProprietaire;
	private String proprietaire;
	
	public Proprietaire() {
		super();
	}

	public Proprietaire(int idProprietaire, String proprietaire) {
		super();
		this.idProprietaire = idProprietaire;
		this.proprietaire = proprietaire;
	}

	public int getIdProprietaire() {
		return idProprietaire;
	}

	public void setIdProprietaire(int idProprietaire) {
		this.idProprietaire = idProprietaire;
	}

	public String getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(String proprietaire) {
		this.proprietaire = proprietaire;
	}

	@Override
	public String toString() {
		return "Proprietaire [idProprietaire=" + idProprietaire + ", proprietaire=" + proprietaire + "]";
	}
}
