package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import java.net.InetAddress;

public class Publisher {

  private InetAddress peerAddress;
  private String fileUri;

  public Publisher(InetAddress peerAddress, String fileUri) {
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
