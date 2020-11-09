package fr.voirplustard.bo;

import java.io.Serializable;

public class Langue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idLangue;
	private String langue;
	
	
	public Langue() {
		super();
	}


	public Langue(int idLangue, String langue) {
		super();
		this.idLangue = idLangue;
		this.langue = langue;
	}


	public int getIdLangue() {
		return idLangue;
	}


	public void setIdLangue(int idLangue) {
		this.idLangue = idLangue;
	}


	public String getLangue() {
		return langue;
	}


	public void setLangue(String langue) {
		this.langue = langue;
	}


	@Override
	public String toString() {
		return "Langue [idLangue=" + idLangue + ", langue=" + langue + "]";
	}
}
