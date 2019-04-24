package ch.hsr.epj.localshare.demo.network.transfer.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

public class HTTPDownloader implements Runnable {

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

  void startDownload() throws IOException {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setDoOutput(true);
    connection.connect();
    int status = connection.getResponseCode();

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
        System.err.println("Error: Problem with output stream occurred");
        break;
      }
    }
    bufferedInputStream.close();
    bufferedOutputStream.close();
    connection.disconnect();
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
      e.printStackTrace();
    }
  }
}
