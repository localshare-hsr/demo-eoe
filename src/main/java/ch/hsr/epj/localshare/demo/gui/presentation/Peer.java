package ch.hsr.epj.localshare.demo.gui.presentation;

public class Peer {

  private String ipAddress;
  private String friendlyName;
  private String displayName;
  private String fingerPrint;
  private boolean isTrusted;

  public Peer(String ipAddress, String friendlyName, String displayName, String fingerPrint) {
    this.ipAddress = ipAddress;
    this.friendlyName = friendlyName;
    this.displayName = displayName;
    this.fingerPrint = fingerPrint;
    isTrusted = false;
  }

  public String getIP() {
    return ipAddress;
  }

  public String getFriendlyName() {
    return friendlyName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String name) {
    displayName = name;
  }

  public String getFingerPrint() {
    return fingerPrint;
  }

  public String getFingerPrintShort() {
    return fingerPrint.substring(0, fingerPrint.length() / 2);
  }

  public boolean getTrustState() {
    return isTrusted;
  }

  public void setTrustState(boolean value) {
    isTrusted = value;
  }

  public void setFriendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  public void setFingerPrint(String fingerPrint) {
    this.fingerPrint = fingerPrint;
  }

  @Override
  public int hashCode() {
    return ipAddress.hashCode();
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
    return this.ipAddress.equals(((Peer) obj).ipAddress);
  }

  @Override
  public String toString() {
    return getIP() + " " + getFriendlyName();
  }
}
