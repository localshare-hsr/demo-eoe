package ch.hsr.epj.localshare.demo.gui.presentation;

import ch.hsr.epj.localshare.demo.logic.networkcontroller.TransferCalculator;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.TransferCalculator.BytePrefix;
import java.net.URL;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class Download {

  private String friendlyName;
  private String fileSize;
  private long size;
  private String fileName;
  private URL url;
  private ProgressBar progressBar;
  private Label transferSpeed;
  private Label transferTime;

  public Download(String friendlyName, long size, String fileName, URL url) {
    this.friendlyName = friendlyName;
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.DECIMAL,
        true);
    this.fileSize = transferCalculator.formatBytesToNiceString(size);
    this.size = size;
    this.fileName = fileName;
    this.url = url;
    downloadState = DownloadState.WAITING;
  }

  private DownloadState downloadState;

  public String getFileSize() {
    return fileSize;
  }

  public DownloadState getDownloadState() {
    return downloadState;
  }

  public void setDownloadState(DownloadState downloadState) {
    this.downloadState = downloadState;
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

  public ProgressBar getProgressBar() {
    return progressBar;
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
    RUNNING, WAITING
  }
}
