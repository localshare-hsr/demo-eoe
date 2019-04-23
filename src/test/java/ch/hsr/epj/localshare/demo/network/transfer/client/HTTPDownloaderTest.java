package ch.hsr.epj.localshare.demo.network.transfer.client;

import static org.junit.Assert.assertTrue;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


public class HTTPDownloaderTest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Test
  public void testSimpleDownload() throws IOException {

    File tempfile = temporaryFolder.newFile("testfile");

    OutputStream outputStream = new FileOutputStream(tempfile);
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

    URL url = new URL("https://www.example.com");

    HTTPDownloader testdownload = new HTTPDownloader(url, bufferedOutputStream, null);

    testdownload.startDownload();

    assertTrue(tempfile.length() > 0);

  }

}