package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import ch.hsr.epj.localshare.demo.gui.presentation.Peer;
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

  public void updateProgressBar(final double percentage) {
    progress.setProgress(percentage);
  }

  public void updateTransferSpeed(final int bps) {
    String niceFormat = transferSpeedCalculator.formatBytesPerSecond(bps);
    transferSpeedInBytesPerSecond.setText(niceFormat);
  }

  public void updateTimeToGo(final long millis) {
    String niceFormat = transferTimeCalculator.formatSecond(millis);
    approximateTimeToDownloadInSeconds.setText(niceFormat);
  }

}
