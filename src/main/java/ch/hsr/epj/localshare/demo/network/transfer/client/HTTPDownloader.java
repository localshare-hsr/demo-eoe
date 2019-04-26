package ch.hsr.epj.localshare.demo.network.transfer.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

public class HTTPDownloader implements Runnable {

  private static final Logger logger = Logger.getLogger(HTTPDownloader.class.getName());

  private static final int BUFFER_SIZE = 1024;
  private static final int EOF = -1;

  private URL url;
  private BufferedOutputStream bufferedOutputStream;
  private ProgressBar progressBar;
  private long totalFileLength;

  public HTTPDownloader(URL url, BufferedOutputStream bufferedOutputStream,
      ProgressBar progressBar) {
    this.url = url;
    this.bufferedOutputStream = bufferedOutputStream;
    this.progressBar = progressBar;
  }

  private void startDownload() throws IOException {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setDoOutput(true);
    connection.setRequestProperty("Connection", "close");
    connection.connect();
    int status = connection.getResponseCode();
    if (status == 200) {
      totalFileLength = Long.parseLong(connection.getHeaderField("Content-Length"));
      InputStream inputStream = connection.getInputStream();
      BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

      byte[] buffer = new byte[BUFFER_SIZE];
      int byteRead;
      long totalbyteread = 0;
      while ((byteRead = bufferedInputStream.read(buffer)) != EOF) {
        try {
          bufferedOutputStream.write(buffer, 0, byteRead);
          bufferedOutputStream.flush();
          totalbyteread += byteRead;
          updateProgress(totalbyteread);

        } catch (IOException e) {
          logger.log(Level.WARNING, "Problem with output stream occurred", e);
          break;
        }
      }
      bufferedInputStream.close();
      bufferedOutputStream.close();
      connection.disconnect();
    } else {
      logger.log(Level.INFO, "HTTP status code not 200 OK but {0}", status);
    }
  }

  private void updateProgress(long progress) {
    Platform.runLater(
        () -> {
          double progressPercent = (double) 1 / totalFileLength * progress;
          progressBar.setProgress(progressPercent);
        }
    );

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
