package ch.hsr.epj.localshare.demo.network.transfer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HTTPProgressTest {

  @Test(expected = IllegalArgumentException.class)
  public void testSetInvalidTotalLengthOfZero() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetInvalidTotalLengthOfSmallerZero() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(-1);
  }

  @Test
  public void testValidSetTotalLength() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(42);
    double result = progress.updateProgress(42);
    assertEquals(1, result, 0);
  }

  @Test
  public void testProgress0Percent() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(100);
    assertEquals(0.0, progress.updateProgress(0), 0);
  }

  @Test
  public void testProgress10Percent() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(100);
    assertEquals(0.1, progress.updateProgress(10), 0);
  }

  @Test
  public void testProgress20Percent() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(100);
    assertEquals(0.2, progress.updateProgress(20), 0);
  }

  @Test
  public void testProgress40Percent() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(100);
    assertEquals(0.4, progress.updateProgress(40), 0);
  }

  @Test
  public void testProgress60Percent() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(100);
    assertEquals(0.6, progress.updateProgress(60), 0);
  }

  @Test
  public void testProgress80Percent() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(100);
    assertEquals(0.8, progress.updateProgress(80), 0);
  }

  @Test
  public void testProgress100Percent() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(100);
    assertEquals(1.0, progress.updateProgress(100), 0);
  }

  @Test
  public void testProgressSmallSteps0() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(1000);
    double percent = progress.updateProgress(0);
    assertEquals(0.0, percent, 0);
  }

  @Test
  public void testProgressSmallSteps1() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(1000);
    double percent = progress.updateProgress(1);
    assertEquals(0.0, percent, 0);
  }

  @Test
  public void testProgressSmallSteps2() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(1000);
    double percent = progress.updateProgress(2);
    assertEquals(0.0, percent, 0);
  }

  @Test
  public void testProgressSmallSteps3() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(1000);
    double percent = progress.updateProgress(3);
    assertEquals(0.0, percent, 0);
  }

  @Test
  public void testProgressSmallSteps4() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(1000);
    double percent = progress.updateProgress(4);
    assertEquals(0.0, percent, 0);
  }

  @Test
  public void testProgressSmallSteps5() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(1000);
    double percent = progress.updateProgress(5);
    assertEquals(0.0, percent, 0);
  }

  @Test
  public void testProgressSmallSteps10() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(1000);
    double percent = progress.updateProgress(10);
    assertEquals(0.01, percent, 0);
  }

  @Test
  public void testProgressSmallSteps11() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(1000);
    assertEquals(0.01, progress.updateProgress(11), 0.01);
  }

  @Test
  public void testProgressSmallSteps15() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(1000);
    assertEquals(0.01, progress.updateProgress(15), 0.01);
  }

  @Test
  public void testProgressSmallSteps19() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(1000);
    assertEquals(0.01, progress.updateProgress(19), 0.01);
  }

  @Test
  public void testProgressSmallSteps20() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(1000);
    assertEquals(0.02, progress.updateProgress(20), 0);
  }

  @Test
  public void testProgressSmallSteps21() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(1000);
    double percent = progress.updateProgress(21);
    assertEquals(0.02, percent, 0.01);
  }

  @Test
  public void testProgressSmallSteps500() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(1000);
    assertEquals(0.5, progress.updateProgress(500), 0);
  }

  @Test
  public void testProgressSmallSteps1000() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(1000);
    assertEquals(1.0, progress.updateProgress(1000), 0);
  }

  @Test
  public void testProgressSetFinished() {
    HTTPProgress progress = new HTTPProgress(null);
    progress.setTotalByteLength(1000);
    boolean result = progress.setFinished();
    assertTrue(result);
  }
}