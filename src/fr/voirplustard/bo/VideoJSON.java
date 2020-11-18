package fr.voirplustard.bo;

import java.io.Serializable;

public class VideoJSON implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int idVideo;
	private int duree;
	private String description;
	private String langue;
	private String idVideoWebSite;
	private String idVideoDuSite;
	private String titre;
	private String nomChannel;
	private String proprietaire;
	
	public VideoJSON() {
		super();
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

	public String getLangue() {
		return langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

	public String getIdVideoWebSite() {
		return idVideoWebSite;
	}

	public void setIdVideoWebSite(String idVideoWebSite) {
		this.idVideoWebSite = idVideoWebSite;
	}

	public String getIdVideoDuSite() {
		return idVideoDuSite;
	}

	public void setIdVideoDuSite(String idVideoDuSite) {
		this.idVideoDuSite = idVideoDuSite;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getNomChannel() {
		return nomChannel;
	}

	public void setNomChannel(String nomChannel) {
		this.nomChannel = nomChannel;
	}

	public String getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(String proprietaire) {
		this.proprietaire = proprietaire;
	}
}
