package ch.hsr.epj.localshare.demo.logic.environment;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class OSDetectorTest {

  @Test
  public void testGetLocalShareDir() {
    OSDetector osDetector = new OSDetector();
    assertNotNull(osDetector.getOSSpecificLocalShareDirectory());
  }

  @Test
  public void testGetDownloadDir() {
    OSDetector osDetector = new OSDetector();
    assertNotNull(osDetector.getOSSpecificDownloadDirectory());
  }

  @Test
  public void testGetConfigDir() {
    OSDetector osDetector = new OSDetector();
    assertNotNull(osDetector.getOSSpecificConfigDirectory());
  }

  @Test
  public void testGetHomeDir() {
    OSDetector osDetector = new OSDetector();
    assertNotNull(osDetector.getOSSpecificHomeDirectory());
  }

  @Test
  public void testIsWindows() {
    OSDetector osDetector = new OSDetector();
    assertNotNull(osDetector.isWindows());
  }

  @Test
  public void testIsLinux() {
    OSDetector osDetector = new OSDetector();
    assertNotNull(osDetector.isLinux());
  }

  @Test
  public void testGetOSName() {
    OSDetector osDetector = new OSDetector();
    assertNotNull(osDetector.getOsName());
  }

}