package ch.hsr.epj.localshare.demo.network.transfer.client;

import ch.hsr.epj.localshare.demo.logic.networkcontroller.FileTransfer;
import ch.hsr.epj.localshare.demo.network.transfer.HTTPProgress;
import ch.hsr.epj.localshare.demo.network.transfer.utils.SelfSignedSSL;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

public class HTTPDownloader implements Runnable {

  private static final Logger logger = Logger.getLogger(HTTPDownloader.class.getName());

  private static final int BUFFER_SIZE = 1024;
  private static final int EOF = -1;

  private URL url;
  private BufferedOutputStream bufferedOutputStream;
  private HTTPProgress httpProgress;
  private volatile boolean isRunning;

  public HTTPDownloader(URL url, BufferedOutputStream bufferedOutputStream, FileTransfer transfer) {
    this.url = url;
    this.bufferedOutputStream = bufferedOutputStream;
    this.httpProgress = new HTTPProgress(transfer);
    this.isRunning = true;
  }

  /**
   * Shutdown a running download
   */
  public void shutdownDownload() {
    isRunning = false;
  }

  private void startDownload() throws IOException {
    // Create all-trusting host name verifier
    HostnameVerifier allHostsValid = new HostnameVerifier() {
      public boolean verify(String hostname, SSLSession session) {
        return true;
      }
    };
    try {
      SSLContext sslContext = SSLContext.getInstance("TLS");
      TrustManager[] gullible = new TrustManager[]{new SelfSignedSSL()};
      sslContext.init(null, gullible, null);
      HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
      // Install the all-trusting host verifier
      HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      logger.log(Level.SEVERE, "TLS algorithm not available", e);
    }
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
        if (!isRunning) {
          break;
        }
        bufferedOutputStream.write(buffer, 0, byteRead);
        bufferedOutputStream.flush();
        httpProgress.updateProgress(byteRead);

      }
      bufferedInputStream.close();
      bufferedOutputStream.close();
      connection.disconnect();
    } else {
      logger.log(Level.INFO, "HTTP status code not 200 OK but {0}", status);
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
