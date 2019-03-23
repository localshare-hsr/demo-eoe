package ch.hsr.epj.localshare.demo.gui.data;

public class Peer {
	private String ip_address;
	private String friendly_name;
	
	public Peer(String ip_address, String friendly_name) {
		this.ip_address = ip_address;
		this.friendly_name = friendly_name;
	}
	
	public String getIP(){
		return ip_address;
	}
	
	public String getFirendlyName() {
		return friendly_name;
	}

	@Override
	public int hashCode() {
		return ip_address.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Peer)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		return this.ip_address.equals(((Peer) obj).ip_address);
	}
}
