package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import ch.hsr.epj.localshare.demo.gui.application.FinishedEvent;
import ch.hsr.epj.localshare.demo.gui.presentation.Peer;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.TransferCalculator.BytePrefix;
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
  private Label currentSize;
  private TransferCalculator transferSpeedCalculator;
  private TransferCalculator transferSizeCalculator;
  private TransferTimeCalculator transferTimeCalculator;
  private HTTPDownloader httpDownloader;

  public FileTransfer(final Peer peer, final URL path, final ProgressBar progress,
      final Label bytesPerSecond, final Label secondsToGo, final Label currentSize) {
    this.peer = peer;
    this.path = path;
    this.progress = progress;
    this.transferSpeedInBytesPerSecond = bytesPerSecond;
    this.approximateTimeToDownloadInSeconds = secondsToGo;
    this.currentSize = currentSize;
    this.transferSpeedCalculator = new TransferCalculator(BytePrefix.DECIMAL, false);
    this.transferSizeCalculator = new TransferCalculator(BytePrefix.DECIMAL, true);
    this.transferTimeCalculator = new TransferTimeCalculator();
  }


  public Peer getPeer() {
    return peer;
  }

  URL getPath() {
    return path;
  }

  public void updateProgressBar(final double percentage, boolean isFinished) {
    progress.setProgress(percentage);
    if (isFinished) {
      progress.fireEvent(new FinishedEvent(42));
    }
  }

  public void updateTransferSpeed(final int bps) {
    String niceFormat = transferSpeedCalculator.formatBytesToNiceString(bps);
    transferSpeedInBytesPerSecond.setText(niceFormat);
  }

  public void updateTimeToGo(final long millis) {
    String niceFormat = transferTimeCalculator.formatSecond(millis);
    approximateTimeToDownloadInSeconds.setText(niceFormat);
  }

  public void updateTransferBytes(final long bytes) {
    String niceFormat = transferSizeCalculator.formatBytesToNiceString(bytes);
    currentSize.setText(niceFormat);
  }

  void setHttpDownloader(final HTTPDownloader httpDownloader) {
    this.httpDownloader = httpDownloader;
  }

  public void shutdownDownload() {
    httpDownloader.shutdownDownload();
  }

}
