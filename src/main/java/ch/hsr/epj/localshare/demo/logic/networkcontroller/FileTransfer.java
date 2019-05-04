package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import ch.hsr.epj.localshare.demo.gui.presentation.Peer;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class FileTransfer {

  private Peer peer;
  private URL path;
  private ProgressBar progress;
  private Label transferSpeedInBytesPerSecond;
  private Label approximateTimeToDownloadInSeconds;

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

  public void updateTransferSpeed(final int bps) {
    String niceFormat;

    if (bps > 1_000_000_000) {
      niceFormat = (bps / 1_000_000_000) + " GB/s";
    } else if (bps > 1_000_000) {
      niceFormat = (bps / 1_000_000) + " MB/s";
    } else if (bps > 1_000) {
      niceFormat = (bps / 1_000) + " kB/s";
    } else {
      niceFormat = bps + " B/s";
    }

    transferSpeedInBytesPerSecond.setText(niceFormat);
  }

  public void updateTimeToGo(final long millis) {
    long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
    long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
    String niceFormat;
    if (minutes == 0) {
      niceFormat = String.format("%2d sec", seconds - TimeUnit.MINUTES.toSeconds(minutes));
    } else {
      niceFormat = String
          .format("%d min %2d sec", minutes, seconds - TimeUnit.MINUTES.toSeconds(minutes));
    }
    approximateTimeToDownloadInSeconds.setText(niceFormat);
  }

}
