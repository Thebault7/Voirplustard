package fr.voirplustard.bo;

import java.io.Serializable;

public class Channel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int idChannel;
	private String channel;
	
	public Channel() {
		super();
	}

	public Channel(String channel) {
		super();
		this.channel = channel;
	}

	public Channel(int idChannel, String channel) {
		super();
		this.idChannel = idChannel;
		this.channel = channel;
	}

	public int getIdChannel() {
		return idChannel;
	}

	public void setIdChannel(int idChannel) {
		this.idChannel = idChannel;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return "Channel [idChannel=" + idChannel + ", channel=" + channel + "]";
	}
}
