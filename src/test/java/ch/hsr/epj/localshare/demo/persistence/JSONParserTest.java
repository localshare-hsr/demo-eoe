package ch.hsr.epj.localshare.demo.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class JSONParserTest {

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  @Test
  public void testValidConstruction() throws IOException {
    File configFolder = tempFolder.newFolder("config");
    JSONParser jsonParser = new JSONParser(configFolder.getAbsolutePath());
    assertEquals(configFolder.getAbsoluteFile().toString(), jsonParser.getConfigPath());
  }

  @Test
  public void testInvalidConstruction() throws IOException {
    File configFolder = tempFolder.newFolder("config");
    JSONParser jsonParser = new JSONParser(configFolder.getAbsolutePath());
    assertFalse(jsonParser.writeData());
  }

  @Test
  public void testConstructor2() throws IOException {
    File configFolder = tempFolder.newFolder("config");
    JSONParser jsonParser = new JSONParser(configFolder.getAbsolutePath(), "Foo",
        "/download/path/");
    assertEquals(configFolder.getAbsoluteFile().toString(), jsonParser.getConfigPath());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2NoConfigPath() throws IOException {
    File configFolder = tempFolder.newFolder("config");
    JSONParser jsonParser = new JSONParser(null, "Foo", "/download/path/");
    assertEquals(configFolder.getAbsoluteFile().toString(), jsonParser.getConfigPath());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2NoFriendlyName() throws IOException {
    File configFolder = tempFolder.newFolder("config");
    JSONParser jsonParser = new JSONParser(configFolder.getAbsolutePath(), null, "/download/path/");
    assertEquals(configFolder.getAbsoluteFile().toString(), jsonParser.getConfigPath());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2NoDownloadPath() throws IOException {
    File configFolder = tempFolder.newFolder("config");
    JSONParser jsonParser = new JSONParser(configFolder.getAbsolutePath(), "Foo", null);
    assertEquals(configFolder.getAbsoluteFile().toString(), jsonParser.getConfigPath());
  }

  @Test
  public void testWriteConfig() throws IOException {
    File configFolder = tempFolder.newFolder("config");
    File downloadFolder = tempFolder.newFolder("download");
    String config = configFolder.getAbsolutePath();
    String download = downloadFolder.getAbsolutePath();
    String friendlyName = "Foo";
    JSONParser jsonParser = new JSONParser(config, friendlyName, download);
    assertTrue(jsonParser.writeData());
  }

  @Test
  public void testWriteAndLoad() throws IOException {
    File configFolder = tempFolder.newFolder("config");
    File downloadFolder = tempFolder.newFolder("download");
    String config = configFolder.getAbsolutePath();
    String download = downloadFolder.getAbsolutePath();
    String friendlyName = "Foo";
    JSONParser jsonParser = new JSONParser(config, friendlyName, download);
    jsonParser.writeData();
    assertTrue(jsonParser.loadData());
  }

  @Test
  public void testWriteAndLoadConfig() throws IOException {
    File configFolder = tempFolder.newFolder("config");
    File downloadFolder = tempFolder.newFolder("download");
    String config = configFolder.getAbsolutePath();
    String download = downloadFolder.getAbsolutePath();
    String friendlyName = "Foo";
    JSONParser jsonParser = new JSONParser(config, friendlyName, download);
    jsonParser.writeData();
    jsonParser.loadData();
    assertEquals(config, jsonParser.getConfigPath());
  }

  @Test
  public void testWriteAndLoadFriendlyName() throws IOException {
    File configFolder = tempFolder.newFolder("config");
    File downloadFolder = tempFolder.newFolder("download");
    String config = configFolder.getAbsolutePath();
    String download = downloadFolder.getAbsolutePath();
    String friendlyName = "Foo";
    JSONParser jsonParser = new JSONParser(config, friendlyName, download);
    jsonParser.writeData();
    jsonParser.loadData();
    assertEquals(friendlyName, jsonParser.getFriendlyName());
  }

  @Test
  public void testWriteAndLoadDowload() throws IOException {
    File configFolder = tempFolder.newFolder("config");
    File downloadFolder = tempFolder.newFolder("download");
    String config = configFolder.getAbsolutePath();
    String download = downloadFolder.getAbsolutePath();
    String friendlyName = "Foo";
    JSONParser jsonParser = new JSONParser(config, friendlyName, download);
    jsonParser.writeData();
    jsonParser.loadData();
    assertEquals(download, jsonParser.getDownloadPath());
  }

}