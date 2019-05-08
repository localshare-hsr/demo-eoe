package ch.hsr.epj.localshare.demo.gui.presentation;

import java.net.URL;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class Download {

  private String friendlyName;
  private long size;
  private String fileName;
  private URL url;
  private boolean accepted;
  private ProgressBar progressBar;
  private Label transferSpeed;
  private Label transferTime;
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

  public ProgressBar getProgressBar() {
    return progressBar;
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

  public void setProgressBar(ProgressBar progressBar) {
    this.progressBar = progressBar;
  }

  public Label getTransferSpeed() {
    return transferSpeed;
  }

  public void setTransferSpeed(Label transferSpeed) {
    this.transferSpeed = transferSpeed;
  }

  public Label getTransferTime() {
    return transferTime;
  }

  public void setTransferTime(Label transferTime) {
    this.transferTime = transferTime;
  }

  public enum DownloadState {
    RUNNING, WAITING, FINISHED
  }
}
