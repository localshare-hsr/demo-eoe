package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import ch.hsr.epj.localshare.demo.gui.presentation.Peer;
import ch.hsr.epj.localshare.demo.gui.presentation.UIProgress;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.TransferCalculator.BytePrefix;
import ch.hsr.epj.localshare.demo.network.transfer.client.HTTPDownloader;
import java.net.URL;

public class FileTransfer {

  private Peer peer;
  private URL path;
  private UIProgress uiProgress;
  private TransferCalculator transferSpeedCalculator;
  private TransferCalculator transferSizeCalculator;
  private TransferTimeCalculator transferTimeCalculator;
  private HTTPDownloader httpDownloader;

  public FileTransfer(final Peer peer, final URL path, final UIProgress uiProgress) {
    this.peer = peer;
    this.path = path;
    this.uiProgress = uiProgress;
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

  public boolean updateProgressBar(final double percentage, boolean isFinished) {
    return uiProgress.updateProgressBar(percentage, isFinished);
  }

  public boolean updateTransferSpeed(final int bps) {
    String niceFormat = transferSpeedCalculator.formatBytesToNiceString(bps);
    return uiProgress.updateTransferSpeed(niceFormat);
  }

  public boolean updateTimeToGo(final long millis) {
    String niceFormat = transferTimeCalculator.formatSecond(millis);
    return uiProgress.updateTimeToGo(niceFormat);
  }

  public boolean updateTransferBytes(final long bytes) {
    String niceFormat = transferSizeCalculator.formatBytesToNiceString(bytes);
    return uiProgress.updateTransferBytes(niceFormat);
  }

  void setHttpDownloader(final HTTPDownloader httpDownloader) {
    this.httpDownloader = httpDownloader;
  }

  public void shutdownDownload() {
    httpDownloader.shutdownDownload();
  }

}
