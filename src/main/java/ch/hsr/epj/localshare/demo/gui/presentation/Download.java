package ch.hsr.epj.localshare.demo.gui.presentation;

import java.net.URL;

public class Download {

  private String friendlyName;
  private long size;
  private String fileName;
  private URL url;
  private boolean accepted;

  public enum DownloadState {
    RUNNING, WAITING, FINISHED
  }

  private DownloadState downloadState;

  public Download(String friendlyName, long size, String fileName, URL url) {
    this.friendlyName = friendlyName;
    this.size = size;
    this.fileName = fileName;
    this.url = url;
    accepted = false;
    downloadState = DownloadState.WAITING;
  }

  public DownloadState getDownloadState() {
    return downloadState;
  }

  public void setDownloadState(DownloadState downloadState) {
    this.downloadState = downloadState;
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
