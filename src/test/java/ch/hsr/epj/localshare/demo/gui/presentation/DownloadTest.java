package ch.hsr.epj.localshare.demo.gui.presentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ch.hsr.epj.localshare.demo.gui.presentation.Download.DownloadState;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Test;

public class DownloadTest {

  @Test
  public void testDownloadStateDefault() throws MalformedURLException {
    URL url = new URL("https://127.0.0.1/");
    Download download = new Download("Foo", 42, "bar.txt", url);
    assertEquals(DownloadState.WAITING, download.getDownloadState());
  }

  @Test
  public void testDownloadStateRunning() throws MalformedURLException {
    URL url = new URL("https://127.0.0.1/");
    Download download = new Download("Foo", 42, "bar.txt", url);
    download.setDownloadState(DownloadState.RUNNING);
    assertEquals(DownloadState.RUNNING, download.getDownloadState());
  }

  @Test
  public void testDownloadStateFinished() throws MalformedURLException {
    URL url = new URL("https://127.0.0.1/");
    Download download = new Download("Foo", 42, "bar.txt", url);
    download.setDownloadState(DownloadState.FINISHED);
    assertEquals(DownloadState.FINISHED, download.getDownloadState());
  }

  @Test
  public void testFriendlyName() throws MalformedURLException {
    URL url = new URL("https://127.0.0.1/");
    Download download = new Download("Foo", 42, "bar.txt", url);
    assertEquals("Foo", download.getFriendlyName());
  }

  @Test
  public void testSize() throws MalformedURLException {
    URL url = new URL("https://127.0.0.1/");
    Download download = new Download("Foo", 42, "bar.txt", url);
    assertEquals(42, download.getSize(), 0);
  }

  @Test
  public void testFileSize() throws MalformedURLException {
    URL url = new URL("https://127.0.0.1/");
    Download download = new Download("Foo", 42, "bar.txt", url);
    assertEquals("42.0 B", download.getFileSize());
  }

  @Test
  public void testFileName() throws MalformedURLException {
    URL url = new URL("https://127.0.0.1/");
    Download download = new Download("Foo", 42, "bar.txt", url);
    assertEquals("bar.txt", download.getFileName());
  }

  @Test
  public void testUrl() throws MalformedURLException {
    URL url = new URL("https://127.0.0.1/");
    Download download = new Download("Foo", 42, "bar.txt", url);
    assertEquals("https://127.0.0.1/", download.getUrl().toString());
  }

  @Test
  public void testValidUIProgressSetting() throws MalformedURLException {
    UIProgress uiProgress = new UIProgress(null, null, null, null);
    URL url = new URL("https://127.0.0.1/");
    Download download = new Download("Foo", 42, "bar.txt", url);
    boolean result = download.setUiProgress(uiProgress);
    assertTrue(result);
  }

  @Test
  public void testInvalidUIProgressSetting() throws MalformedURLException {
    URL url = new URL("https://127.0.0.1/");
    Download download = new Download("Foo", 42, "bar.txt", url);
    boolean result = download.setUiProgress(null);
    assertFalse(result);
  }

}