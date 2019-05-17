package ch.hsr.epj.localshare.demo.gui.presentation;

import ch.hsr.epj.localshare.demo.gui.application.FinishedEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class UIProgress {

  private ProgressBar progress;
  private Label bytesPerSecond;
  private Label secondsToGo;
  private Label currentSize;

  public UIProgress(ProgressBar progress, Label bytesPerSecond,
      Label secondsToGo, Label currentSize) {
    this.progress = progress;
    this.bytesPerSecond = bytesPerSecond;
    this.secondsToGo = secondsToGo;
    this.currentSize = currentSize;
  }

  public boolean updateProgressBar(final double percentage, final boolean isFinished) {
    boolean success;
    if (progress != null) {
      progress.setProgress(percentage);
      if (isFinished) {
        progress.fireEvent(new FinishedEvent(42));
      }
      success = true;
    } else {
      success = false;
    }
    return success;
  }

  public boolean updateTransferSpeed(final String bps) {
    boolean success;
    if (bytesPerSecond != null) {
      bytesPerSecond.setText(bps);
      success = true;
    } else {
      success = false;
    }
    return success;
  }

  public boolean updateTimeToGo(final String time) {
    boolean success;
    if (secondsToGo != null) {
      secondsToGo.setText(time);
      success = true;
    } else {
      success = false;
    }
    return success;
  }

  public boolean updateTransferBytes(final String bytes) {
    boolean success;
    if (currentSize != null) {
      currentSize.setText(bytes);
      success = true;
    } else {
      success = false;
    }
    return success;
  }
}
