package fr.voirplustard.bo;

import java.io.Serializable;

public class Site implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int idSite;
	private String site;
	private String url;
	
	public Site() {
		super();
	}

	public Site(int idSite, String site) {
		super();
		this.idSite = idSite;
		this.site = site;
	}

	public Site(int idSite, String site, String url) {
		super();
		this.idSite = idSite;
		this.site = site;
		this.url = url;
	}

	public int getidSite() {
		return idSite;
	}

	public void setidSite(int idSite) {
		this.idSite = idSite;
	}

	public String getsite() {
		return site;
	}

	public void setsite(String site) {
		this.site = site;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Videosite [idSite=" + idSite + ", site=" + site + "]";
	}
}
