package ch.hsr.epj.localshare.demo.gui.presentation;

import java.net.URL;

public class Transfer {

  private String friendlyName;
  private long size;
  private String fileName;
  private URL url;
  private boolean accepted;


  public Transfer(String friendlyName, long size, String fileName, URL url) {
    this.friendlyName = friendlyName;
    this.size = size;
    this.fileName = fileName;
    this.url = url;
    accepted = false;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public double getSize() {
    return size;
  }

  public String getFileName() {
    return fileName;
  }

  public String getFriendlyName() {
    return friendlyName;
  }

  public URL getUrl() {
    return url;
  }
}
