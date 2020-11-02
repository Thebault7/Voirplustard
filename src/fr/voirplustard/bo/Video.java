package fr.voirplustard.bo;

import java.io.Serializable;

public class Video implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int idVideo;
	private String titre;
	private String langage;
	private String nomChaine;
	private int duree;
	private String auteur;
	
	public Video() {
		super();
	}

	public Video(String titre, String langage, String nomChaine, int duree, String auteur) {
		super();
		this.titre = titre;
		this.langage = langage;
		this.nomChaine = nomChaine;
		this.duree = duree;
		this.auteur = auteur;
	}

	public Video(int idVideo, String titre, String langage, String nomChaine, int duree, String auteur) {
		super();
		this.idVideo = idVideo;
		this.titre = titre;
		this.langage = langage;
		this.nomChaine = nomChaine;
		this.duree = duree;
		this.auteur = auteur;
	}

	public int getIdVideo() {
		return idVideo;
	}

	public void setIdVideo(int idVideo) {
		this.idVideo = idVideo;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getLangage() {
		return langage;
	}

	public void setLangage(String langage) {
		this.langage = langage;
	}

	public String getNomChaine() {
		return nomChaine;
	}

	public void setNomChaine(String nomChaine) {
		this.nomChaine = nomChaine;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	@Override
	public String toString() {
		return "Video [idVideo=" + idVideo + ", titre=" + titre + ", langage=" + langage + ", nomChaine=" + nomChaine
				+ ", duree=" + duree + ", auteur=" + auteur + "]";
	}
}
