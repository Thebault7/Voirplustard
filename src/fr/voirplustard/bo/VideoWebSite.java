package fr.voirplustard.bo;

import java.io.Serializable;

public class VideoWebSite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int idWebSite;
	private String webSite;
	private String url;
	
	public VideoWebSite() {
		super();
	}

	public VideoWebSite(int idWebSite, String webSite) {
		super();
		this.idWebSite = idWebSite;
		this.webSite = webSite;
	}

	public VideoWebSite(int idWebSite, String webSite, String url) {
		super();
		this.idWebSite = idWebSite;
		this.webSite = webSite;
		this.url = url;
	}

	public int getIdWebSite() {
		return idWebSite;
	}

	public void setIdWebSite(int idWebSite) {
		this.idWebSite = idWebSite;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "VideoWebSite [idWebSite=" + idWebSite + ", webSite=" + webSite + "]";
	}
}
