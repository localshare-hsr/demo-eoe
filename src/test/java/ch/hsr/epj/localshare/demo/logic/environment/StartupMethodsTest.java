package ch.hsr.epj.localshare.demo.logic.environment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class StartupMethodsTest {

  private static final String dummyConfig = "{\"friendly_name\":\"Test Dummy\",\"download_path\":\"\\/home\\/TestDummy\\/LocalShare\\/download\\/\",\"config_path\":\"\\/home\\/TestDummy\\/LocalShare\\/config\\/\"}";

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  @Test
  public void testStartUpCheck() {
    StartupMethods startupMethods = new StartupMethods(new OSDetectorMock());
    assertTrue(startupMethods.startupCheck());
  }

  @Test
  public void testLoadingFail() throws IOException {
    File configFolder = tempFolder.getRoot();
    File configFile = tempFolder.newFile("config.json");
    BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
    writer.write("{}");
    writer.flush();
    String delimiter = isWindows() ? "\\" : "/";
    OSDetectorMock osDetectorMock = new OSDetectorMock();
    osDetectorMock.setConfigDirectory(configFolder.getAbsolutePath() + delimiter);
    StartupMethods startupMethods = new StartupMethods(osDetectorMock);
    assertFalse(startupMethods.loadConfig());
  }

  @Test
  public void testLoadingSuccess() throws IOException {
    File configFolder = tempFolder.getRoot();
    File configFile = tempFolder.newFile("config.json");
    BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
    writer.write(dummyConfig);
    writer.flush();
    String delimiter = isWindows() ? "\\" : "/";
    OSDetectorMock osDetectorMock = new OSDetectorMock();
    osDetectorMock.setConfigDirectory(configFolder.getAbsolutePath() + delimiter);
    StartupMethods startupMethods = new StartupMethods(osDetectorMock);
    assertTrue(startupMethods.loadConfig());
  }

  private boolean isWindows() {
    if (System.getProperty("os.name").startsWith("Windows")) {
      return true;
    } else {
      return false;
    }
  }

}