package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import ch.hsr.epj.localshare.demo.gui.application.FinishedEvent;
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
  private TransferSpeedCalculator transferSpeedCalculator;
  private TransferTimeCalculator transferTimeCalculator;
  private HTTPDownloader httpDownloader;

  public FileTransfer(final Peer peer, final URL path, final ProgressBar progress,
      final Label bytesPerSecond, final Label secondsToGo) {
    this.peer = peer;
    this.path = path;
    this.progress = progress;
    this.transferSpeedInBytesPerSecond = bytesPerSecond;
    this.approximateTimeToDownloadInSeconds = secondsToGo;
    this.transferSpeedCalculator = new TransferSpeedCalculator(BytePrefix.DECIMAL);
    this.transferTimeCalculator = new TransferTimeCalculator();
  }


  public Peer getPeer() {
    return peer;
  }

  public URL getPath() {
    return path;
  }

  public void updateProgressBar(final double percentage, boolean isFinished) {
    progress.setProgress(percentage);
    if (isFinished) {
      System.out.println("fired");
      progress.fireEvent(new FinishedEvent(42));
    }
  }

  public void updateTransferSpeed(final int bps) {
    String niceFormat = transferSpeedCalculator.formatBytesPerSecond(bps);
    transferSpeedInBytesPerSecond.setText(niceFormat);
  }

  public void updateTimeToGo(final long millis) {
    String niceFormat = transferTimeCalculator.formatSecond(millis);
    approximateTimeToDownloadInSeconds.setText(niceFormat);
  }

  void setHttpDownloader(final HTTPDownloader httpDownloader) {
    this.httpDownloader = httpDownloader;
  }

  public void shutdownDownload() {
    httpDownloader.shutdownDownload();
  }

}
