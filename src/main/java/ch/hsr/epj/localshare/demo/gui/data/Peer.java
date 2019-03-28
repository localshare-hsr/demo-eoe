package ch.hsr.epj.localshare.demo.gui.data;

public class Peer {
	private String ip_address;
	private String friendly_name;
	private String displayName;
	private String fingerPrint;
	private boolean isTrusted;
	
	public Peer(String ip_address, String friendly_name, String displayName, String fingerPrint) {
		this.ip_address = ip_address;
		this.friendly_name = friendly_name;
		this.displayName = displayName;
		this.fingerPrint = fingerPrint;
		isTrusted = false;
	}
	
	public String getIP(){
		return ip_address;
	}
	
	public String getFriendlyName() {
		return friendly_name;
	}
	public String getDisplayName() { return displayName; }
	public String getFingerPrint() { return fingerPrint; }
	public boolean getTrustState() { return isTrusted; }
	public void setTrustState(boolean value) { isTrusted = value; }
	public void setDisplayName(String name) { displayName = name; }

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
