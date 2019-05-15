package ch.hsr.epj.localshare.demo.logic.environment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ConfigManagerTest {

  @Test
  public void testMultipleInstancesSameReference() {
    ConfigManager manager1 = ConfigManager.getInstance();
    ConfigManager manager2 = ConfigManager.getInstance();

    assertEquals(manager1, manager2);
  }

  @Test
  public void testGetDownloadPathNormal() {
    ConfigManager configManager = ConfigManager.getInstance();
    String downloadPath = "C:\\Users\\Dominique\\LocalShare";
    configManager.setDownloadPath(downloadPath);

    assertEquals(configManager.getDownloadPath(), downloadPath);
  }

  @Test
  public void testGetDownloadPathNull() {
    ConfigManager configManager = ConfigManager.getInstance();
    String downloadPath = null;
    configManager.setDownloadPath(downloadPath);

    assertNull(configManager.getDownloadPath());
  }

}
