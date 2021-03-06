package ch.hsr.epj.localshare.demo.network.transfer.client;

import ch.hsr.epj.localshare.demo.logic.networkcontroller.FileTransfer;
import ch.hsr.epj.localshare.demo.network.transfer.HTTPProgress;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

public class HTTPDownloader implements Runnable {

  private static final Logger logger = Logger.getLogger(HTTPDownloader.class.getName());

  private static final int BUFFER_SIZE = 8192;
  private static final int EOF = -1;

  private URL url;
  private BufferedOutputStream bufferedOutputStream;
  private HTTPProgress httpProgress;
  private volatile boolean isCanceled;

  public HTTPDownloader(URL url, BufferedOutputStream bufferedOutputStream, FileTransfer transfer) {
    this.url = url;
    this.bufferedOutputStream = bufferedOutputStream;
    this.httpProgress = new HTTPProgress(transfer);
    this.isCanceled = false;
  }

  /**
   * Shutdown a running download
   */
  public void shutdownDownload() {
    isCanceled = true;
  }

  private void startDownload() throws IOException {
    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setDoOutput(true);
    connection.setRequestProperty("Connection", "close");
    connection.connect();
    int status = connection.getResponseCode();
    if (status == 200) {
      long totalFileLength = Long.parseLong(connection.getHeaderField("Content-Length"));
      httpProgress.setTotalByteLength(totalFileLength);
      InputStream inputStream = connection.getInputStream();
      BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

      byte[] buffer = new byte[BUFFER_SIZE];
      int byteRead;
      while ((byteRead = bufferedInputStream.read(buffer)) != EOF) {
        if (isCanceled) {
          break;
        }
        bufferedOutputStream.write(buffer, 0, byteRead);
        bufferedOutputStream.flush();
        httpProgress.updateProgress(byteRead);

      }
      if (!isCanceled) {
        httpProgress.setFinished();
      }
      bufferedInputStream.close();
      bufferedOutputStream.close();
      connection.disconnect();
    } else {
      logger.log(Level.SEVERE, "HTTP status code not 200 OK but {0}", status);
    }
  }

  @Override
  public void run() {
    try {
      startDownload();
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Could not run download", e);
    }
  }
}
