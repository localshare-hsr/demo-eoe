package ch.hsr.epj.localshare.demo.network.transfer;

import ch.hsr.epj.localshare.demo.logic.networkcontroller.FileTransfer;
import java.util.Observable;
import javafx.application.Platform;

public class HTTPProgress extends Observable {

  private FileTransfer transfer;
  private long totalByteLength;
  private long bytesAlreadySent;
  private long startMillis;
  private double currentPercentageDecimal;
  private int currentPercentage;
  private int bytesPerSecond;
  private long secondsToGo;
  private boolean isFinished;

  public HTTPProgress(final FileTransfer transfer) {
    this.transfer = transfer;
    this.currentPercentageDecimal = 0.0;
    this.currentPercentage = 0;
    this.bytesPerSecond = 0;
    this.secondsToGo = 0;
    this.isFinished = false;
  }

  /**
   * Set total file length.
   *
   * @param totalByteLength Total file length in byte
   */
  public void setTotalByteLength(final long totalByteLength) {
    if (totalByteLength <= 0) {
      throw new IllegalArgumentException("Total byte length must not be <= 0");
    }
    this.totalByteLength = totalByteLength;
    startTimer();
  }

  /**
   * Update progress.
   *
   * @param newlyReadBytes Set the newly read bytes
   * @return Percentage in decimal notation (100% == 1, 50% == 0.5, etc.)
   */
  public synchronized double updateProgress(final long newlyReadBytes) {
    if (!isFinished) {
      bytesAlreadySent += newlyReadBytes;

      double newPercentage = (double) bytesAlreadySent / totalByteLength;
      double difference = newPercentage - currentPercentageDecimal;

      if (difference >= 0.01) {
        currentPercentageDecimal = (double) bytesAlreadySent / totalByteLength;
        currentPercentage = (int) (currentPercentageDecimal * 100);
        updateUIProgressBar(currentPercentageDecimal);
      }

      long currentMillis = System.currentTimeMillis();
      bytesPerSecond = calculateBytesPerSecond(currentMillis);
      secondsToGo = calculateSecondToGo(currentMillis);
    }
    return currentPercentageDecimal;
  }

  /**
   * Mark download as finished.
   */
  public boolean setFinished() {
    boolean success = true;
    this.isFinished = success;
    currentPercentageDecimal = 1.0;
    currentPercentage = 100;
    return success;
  }

  private void startTimer() {
    startMillis = System.currentTimeMillis();
  }

  private void updateUIProgressBar(final double progress) {
    if (transfer != null) {
      Platform.runLater(
          () -> {
            transfer.updateProgressBar(progress, isFinished);
            transfer.updateTransferSpeed(bytesPerSecond);
            transfer.updateTimeToGo(secondsToGo);
          }
      );
    }
  }

  private long calculateTimeDifference(final long start, final long end) {
    return start - end;
  }

  private long milliToSeconds(final long milliseconds) {
    return milliseconds / 1000;
  }

  private int calculateBytesPerSecond(final long currentMillis) {
    long timeDiffInMilliSeconds = calculateTimeDifference(startMillis, currentMillis);
    if (timeDiffInMilliSeconds > 0) {
      return (int) milliToSeconds(bytesAlreadySent / timeDiffInMilliSeconds);
    } else {
      return 0;
    }
  }

  private long calculateSecondToGo(final long currentMillis) {
    if (currentPercentage > 0) {
      long timeDiffInMilliSeconds = calculateTimeDifference(startMillis, currentMillis);
      return milliToSeconds(
          (timeDiffInMilliSeconds / currentPercentage * 100) - timeDiffInMilliSeconds);
    } else {
      return 0;
    }
  }
}
