package ch.hsr.epj.localshare.demo.network.transfer.utils;

import static org.junit.Assert.assertEquals;

import ch.hsr.epj.localshare.demo.gui.presentation.Download;
import ch.hsr.epj.localshare.demo.network.transfer.server.DownloadFile;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class MetaParserTest {

  private static final String SIMPLE_JSON = "{"
      + "\"friendlyName\":\"Elvis\","
      + "\"files\":[{"
      + "\"name\":\"abc.txt\","
      + "\"size\":\"1234567890\","
      + "\"url\":\"https://10.10.10.10:8640/share/354363462/abc.txt\""
      + "},"
      + "{"
      + "\"name\":\"def.pdf\","
      + "\"size\":\"1234567\","
      + "\"url\":\"https://10.10.10.10:8640/share/354363462/def.pdf\""
      + "},"
      + "{"
      + "\"name\":\"ghi.png\","
      + "\"size\":\"98876\","
      + "\"url\":\"https://10.10.10.10:8640/share/354363462/ghi.png\""
      + "}"
      + "]"
      + "}";

  private File index;
  private File json;

  @Before
  public void createIndexFile() throws IOException {
    index = File.createTempFile("index", ".json");
    BufferedWriter bw = new BufferedWriter(new FileWriter(index));
    bw.write(SIMPLE_JSON);
    bw.close();
  }

  @Before
  public void createJsonFile() {
    DownloadFile df1 = new DownloadFile("Elvis", "abc.txt", 1234567890,
        "https://10.10.10.10:8640/share/354363462/abc.txt");
    DownloadFile df2 = new DownloadFile("Elvis", "def.pdf", 1234567,
        "https://10.10.10.10:8640/share/354363462/def.pdf");
    DownloadFile df3 = new DownloadFile("Elvis", "ghi.png", 98876,
        "https://10.10.10.10:8640/share/354363462/ghi.png");

    List<DownloadFile> downloadFiles = new LinkedList<>();
    downloadFiles.add(df1);
    downloadFiles.add(df2);
    downloadFiles.add(df3);

    json = MetaParser.generateMeta(downloadFiles);
  }

  @Test
  public void testFriendlyNameFirstObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(index);
    assertEquals("Elvis", downloadList.get(0).getFriendlyName());
  }

  @Test
  public void testFileNameFirstObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(index);
    assertEquals("abc.txt", downloadList.get(0).getFileName());
  }

  @Test
  public void testSizeFirstObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(index);
    assertEquals(1234567890, downloadList.get(0).getSize(), 0);
  }

  @Test
  public void testURLFirstObject() throws FileNotFoundException {
    List<Download> downloadList = MetaParser.parse(index);
    assertEquals("https://10.10.10.10:8640/share/354363462/abc.txt",
        downloadList.get(0).getUrl().toString());
  }

  @Test
  public void testFriendlyNameSecondObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(index);
    assertEquals("Elvis", downloadList.get(1).getFriendlyName());
  }

  @Test
  public void testFileNameSecondObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(index);
    assertEquals("def.pdf", downloadList.get(1).getFileName());
  }

  @Test
  public void testSizeSecondObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(index);
    assertEquals(1234567, downloadList.get(1).getSize(), 0);
  }

  @Test
  public void testURLSecondObject() throws FileNotFoundException {
    List<Download> downloadList = MetaParser.parse(index);
    assertEquals("https://10.10.10.10:8640/share/354363462/def.pdf",
        downloadList.get(1).getUrl().toString());
  }

  @Test
  public void testFriendlyNameThirdObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(index);
    assertEquals("Elvis", downloadList.get(2).getFriendlyName());
  }

  @Test
  public void testFileNameThirdObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(index);
    assertEquals("ghi.png", downloadList.get(2).getFileName());
  }

  @Test
  public void testSizeThirdObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(index);
    assertEquals(98876, downloadList.get(2).getSize(), 0);
  }

  @Test
  public void testURLThirdObject() throws FileNotFoundException {
    List<Download> downloadList = MetaParser.parse(index);
    assertEquals("https://10.10.10.10:8640/share/354363462/ghi.png",
        downloadList.get(2).getUrl().toString());
  }

  @Test
  public void testFriendlyNameFirstGeneratedObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(json);
    assertEquals("Elvis", downloadList.get(0).getFriendlyName());
  }

  @Test
  public void testFileNameFirstGeneratedObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(json);
    assertEquals("abc.txt", downloadList.get(0).getFileName());
  }

  @Test
  public void testSizeFirstGeneratedObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(json);
    assertEquals(1234567890, downloadList.get(0).getSize(), 0);
  }

  @Test
  public void testURLFirstGeneratedObject() throws FileNotFoundException {
    List<Download> downloadList = MetaParser.parse(json);
    assertEquals("https://10.10.10.10:8640/share/354363462/abc.txt",
        downloadList.get(0).getUrl().toString());
  }

  @Test
  public void testFriendlyNameSecondGeneratedObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(json);
    assertEquals("Elvis", downloadList.get(1).getFriendlyName());
  }

  @Test
  public void testFileNameSecondGeneratedObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(json);
    assertEquals("def.pdf", downloadList.get(1).getFileName());
  }

  @Test
  public void testSizeSecondGeneratedObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(json);
    assertEquals(1234567, downloadList.get(1).getSize(), 0);
  }

  @Test
  public void testURLSecondGeneratedObject() throws FileNotFoundException {
    List<Download> downloadList = MetaParser.parse(json);
    assertEquals("https://10.10.10.10:8640/share/354363462/def.pdf",
        downloadList.get(1).getUrl().toString());
  }

  @Test
  public void testFriendlyNameThirdGeneratedObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(json);
    assertEquals("Elvis", downloadList.get(2).getFriendlyName());
  }

  @Test
  public void testFileNameThirdGeneratedObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(json);
    assertEquals("ghi.png", downloadList.get(2).getFileName());
  }

  @Test
  public void testSizeThirdGeneratedObject() throws IOException {
    List<Download> downloadList = MetaParser.parse(json);
    assertEquals(98876, downloadList.get(2).getSize(), 0);
  }

  @Test
  public void testURLThirdGeneratedObject() throws FileNotFoundException {
    List<Download> downloadList = MetaParser.parse(json);
    assertEquals("https://10.10.10.10:8640/share/354363462/ghi.png",
        downloadList.get(2).getUrl().toString());
  }

}