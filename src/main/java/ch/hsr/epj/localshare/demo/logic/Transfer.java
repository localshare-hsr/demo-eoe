package ch.hsr.epj.localshare.demo.logic;

import java.net.InetAddress;

public class Transfer {

  private InetAddress peerAddress;
  private String fileUri;

  public Transfer(InetAddress peerAddress, String fileUri) {
    this.peerAddress = peerAddress;
    this.fileUri = fileUri;
  }

  public InetAddress getPeerAddress() {
    return peerAddress;
  }

  public String getFileUri() {
    return fileUri;
  }
}
