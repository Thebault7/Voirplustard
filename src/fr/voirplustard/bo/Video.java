package fr.voirplustard.bo;

import java.io.Serializable;

public class Video implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int idVideo;
	private int duree;
	private String description;
	private int langue;
	private int idVideoWebSite;
	private String titre;
	private int nomChannel;
	private int proprietaire;
	private int utilisateur;
	
	public Video() {
		super();
	}
	
	public Video(int idVideo) {
		super();
		this.idVideo = idVideo;
	}

	public Video(int duree, String description, int langue, int idVideoWebSite, String titre,
			int nomChannel, int proprietaire) {
		super();
		this.duree = duree;
		this.description = description;
		this.langue = langue;
		this.idVideoWebSite = idVideoWebSite;
		this.titre = titre;
		this.nomChannel = nomChannel;
		this.proprietaire = proprietaire;
	}

	public Video(int idVideo, int duree, String description, int langue, int idVideoWebSite, 
			String titre, int nomChannel, int proprietaire) {
		super();
		this.idVideo = idVideo;
		this.duree = duree;
		this.description = description;
		this.langue = langue;
		this.idVideoWebSite = idVideoWebSite;
		this.titre = titre;
		this.nomChannel = nomChannel;
		this.proprietaire = proprietaire;
	}

	public Video(int idVideo, int duree, String description, int langue, int idVideoWebSite, 
			String titre, int nomChannel, int proprietaire, int utilisateur) {
		super();
		this.idVideo = idVideo;
		this.duree = duree;
		this.description = description;
		this.langue = langue;
		this.idVideoWebSite = idVideoWebSite;
		this.titre = titre;
		this.nomChannel = nomChannel;
		this.proprietaire = proprietaire;
		this.utilisateur = utilisateur;
	}

	public int getIdVideo() {
		return idVideo;
	}

	public void setIdVideo(int idVideo) {
		this.idVideo = idVideo;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLangue() {
		return langue;
	}

	public void setLangue(int langue) {
		this.langue = langue;
	}

	public int getIdVideoWebSite() {
		return idVideoWebSite;
	}

	public void setIdVideoWebSite(int idVideoWebSite) {
		this.idVideoWebSite = idVideoWebSite;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public int getNomChannel() {
		return nomChannel;
	}

	public void setNomChannel(int nomChannel) {
		this.nomChannel = nomChannel;
	}

	public int getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(int proprietaire) {
		this.proprietaire = proprietaire;
	}
	
	public int getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(int utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public String toString() {
		return "Video [idVideo=" + idVideo + ", duree=" + duree + ", description=" + description + ", langue=" + langue
				+ ", idVideoWebSite=" + idVideoWebSite + ", titre=" + titre + ", nomChannel="
				+ nomChannel + ", proprietaire=" + proprietaire + ", utilisateur=" + utilisateur + "]";
	}
}
