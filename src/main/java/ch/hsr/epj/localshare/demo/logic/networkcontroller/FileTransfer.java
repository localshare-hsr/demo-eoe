package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import ch.hsr.epj.localshare.demo.gui.presentation.Peer;
import ch.hsr.epj.localshare.demo.network.transfer.client.HTTPDownloader;
import java.net.URL;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class FileTransfer {

  private Peer peer;
  private URL path;
  private ProgressBar progress;
  private Label transferSpeedInBytesPerSecond;
  private Label approximateTimeToDownloadInSeconds;
  private HTTPDownloader httpDownloader;

  public FileTransfer(final Peer peer, final URL path, final ProgressBar progress,
      final Label bytesPerSecond, final Label secondsToGo) {
    this.peer = peer;
    this.path = path;
    this.progress = progress;
    this.transferSpeedInBytesPerSecond = bytesPerSecond;
    this.approximateTimeToDownloadInSeconds = secondsToGo;
  }


  public Peer getPeer() {
    return peer;
  }

  public URL getPath() {
    return path;
  }

  public void updateProgressBar(final double percentage) {
    progress.setProgress(percentage);
  }

  public void updateTransferSpeed(final long bps) {
    transferSpeedInBytesPerSecond.setText(bps + " Bps");
  }

  public void updateTimeToGo(final long seconds) {
    approximateTimeToDownloadInSeconds.setText(seconds + " s");
  }

  void setHttpDownloader(final HTTPDownloader httpDownloader) {
    this.httpDownloader = httpDownloader;
  }

  public void shutdownDownload() {
    httpDownloader.shutdownDownload();
  }
}
