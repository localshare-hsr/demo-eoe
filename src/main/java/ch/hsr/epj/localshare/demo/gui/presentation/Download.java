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
  private UIProgress uiProgress;
  private DownloadState downloadState;

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
    return uiProgress.getProgress();
  }

  public Label getTransferSpeed() {
    return uiProgress.getBytesPerSecond();
  }

  public Label getTransferTime() {
    return uiProgress.getSecondsToGo();
  }

  public enum DownloadState {
    RUNNING, WAITING, FINISHED
  }

  public boolean setUiProgress(UIProgress uiProgress) {
    boolean success;
    if (uiProgress != null) {
      this.uiProgress = uiProgress;
      success = true;
    } else {
      success = false;
    }
    return success;
  }

}
