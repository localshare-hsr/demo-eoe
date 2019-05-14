package ch.hsr.epj.localshare.demo.gui.presentation;

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

  public ProgressBar getProgress() {
    return progress;
  }

  public Label getBytesPerSecond() {
    return bytesPerSecond;
  }

  public Label getSecondsToGo() {
    return secondsToGo;
  }

  public Label getCurrentSize() {
    return currentSize;
  }
}
