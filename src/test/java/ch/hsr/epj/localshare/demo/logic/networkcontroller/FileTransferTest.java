package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import ch.hsr.epj.localshare.demo.gui.presentation.Peer;
import ch.hsr.epj.localshare.demo.gui.presentation.UIProgress;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Test;

public class FileTransferTest {

  @Test
  public void testGetPeer() throws MalformedURLException {
    Peer dummy = new Peer("127.0.0.1", "Foo", "Bar", "Baz");
    URL url = new URL("https://127.0.0.1/");
    UIProgress uiProgress = new UIProgress(null, null, null, null);
    FileTransfer fileTransfer = new FileTransfer(dummy, url, uiProgress);
    assertEquals("Foo", fileTransfer.getPeer().getFriendlyName());
  }

  @Test
  public void testGetURL() throws MalformedURLException {
    Peer dummy = new Peer("127.0.0.1", "Foo", "Bar", "Baz");
    String expected = "https://127.0.0.1/";
    URL url = new URL(expected);
    UIProgress uiProgress = new UIProgress(null, null, null, null);
    FileTransfer fileTransfer = new FileTransfer(dummy, url, uiProgress);
    assertEquals(expected, fileTransfer.getPath().toString());
  }

  @Test
  public void testUpdatePgrogressBar() throws MalformedURLException {
    Peer dummy = new Peer("127.0.0.1", "Foo", "Bar", "Baz");
    String expected = "https://127.0.0.1/";
    URL url = new URL(expected);
    UIProgress uiProgress = new UIProgress(null, null, null, null);
    FileTransfer fileTransfer = new FileTransfer(dummy, url, uiProgress);
    assertFalse(fileTransfer.updateProgressBar(0.5, false));
  }

  @Test
  public void testUpdateTransferSpeed() throws MalformedURLException {
    Peer dummy = new Peer("127.0.0.1", "Foo", "Bar", "Baz");
    String expected = "https://127.0.0.1/";
    URL url = new URL(expected);
    UIProgress uiProgress = new UIProgress(null, null, null, null);
    FileTransfer fileTransfer = new FileTransfer(dummy, url, uiProgress);
    assertFalse(fileTransfer.updateTransferSpeed(42));
  }

  @Test
  public void testUpdateTimeToGo() throws MalformedURLException {
    Peer dummy = new Peer("127.0.0.1", "Foo", "Bar", "Baz");
    String expected = "https://127.0.0.1/";
    URL url = new URL(expected);
    UIProgress uiProgress = new UIProgress(null, null, null, null);
    FileTransfer fileTransfer = new FileTransfer(dummy, url, uiProgress);
    assertFalse(fileTransfer.updateTimeToGo(42));
  }

  @Test
  public void testUpdateTransferedBytes() throws MalformedURLException {
    Peer dummy = new Peer("127.0.0.1", "Foo", "Bar", "Baz");
    String expected = "https://127.0.0.1/";
    URL url = new URL(expected);
    UIProgress uiProgress = new UIProgress(null, null, null, null);
    FileTransfer fileTransfer = new FileTransfer(dummy, url, uiProgress);
    assertFalse(fileTransfer.updateTransferBytes(42));
  }

}