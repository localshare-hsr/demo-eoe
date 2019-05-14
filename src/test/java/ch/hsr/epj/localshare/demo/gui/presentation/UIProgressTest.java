package ch.hsr.epj.localshare.demo.gui.presentation;

import static org.junit.Assert.assertNotNull;

import ch.hsr.epj.localshare.demo.gui.MockApp;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.junit.Rule;
import org.junit.Test;

public class UIProgressTest {

  @Rule
  public MockApp javafxRule = new MockApp();

  @Test
  public void testProgressbar() {
    ProgressBar progress = new ProgressBar(0.5);
    UIProgress uiProgress = new UIProgress(progress, null, null, null);
    assertNotNull(uiProgress.getProgress());
  }

  @Test
  public void testBytesPerSecond() {
    Label bytesPerSecond = new Label();
    UIProgress uiProgress = new UIProgress(null, bytesPerSecond, null, null);
    assertNotNull(uiProgress.getBytesPerSecond());
  }

  @Test
  public void testSecondToGo() {
    Label secondsToGo = new Label();
    UIProgress uiProgress = new UIProgress(null, null, secondsToGo, null);
    assertNotNull(uiProgress.getSecondsToGo());
  }

  @Test
  public void testCurrentSize() {
    Label currentSize = new Label();
    UIProgress uiProgress = new UIProgress(null, null, null, currentSize);
    assertNotNull(uiProgress.getCurrentSize());
  }

}