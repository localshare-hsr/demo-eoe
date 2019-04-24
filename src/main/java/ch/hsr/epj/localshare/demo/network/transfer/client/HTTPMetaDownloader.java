package ch.hsr.epj.localshare.demo.network.transfer.client;

import ch.hsr.epj.localshare.demo.gui.presentation.Download;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HTTPMetaDownloader implements Runnable {

  private static final int BUFFER_SIZE = 1024;
  private static final int EOF = -1;


  private URL metaUrl;
  private long totalFileLength;

  public HTTPMetaDownloader(URL url, List<Download> downloadList) {
    this.metaUrl = url;
  }

  @Override
  public void run() {
    try {
      startDownload();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void startDownload() throws IOException {
    HttpURLConnection connection = (HttpURLConnection) metaUrl.openConnection();
    connection.setRequestMethod("GET");
    connection.setDoOutput(true);
    connection.connect();
    int status = connection.getResponseCode();

    totalFileLength = Long.parseLong(connection.getHeaderField("Content-Length"));
    InputStream inputStream = connection.getInputStream();
    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

    File jsonFile = new File("index.json");

    FileOutputStream outputStream = new FileOutputStream(jsonFile);
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

    byte[] buffer = new byte[BUFFER_SIZE];
    int byteRead;
    while ((byteRead = bufferedInputStream.read(buffer)) != EOF) {
      try {
        bufferedOutputStream.write(buffer, 0, byteRead);
        bufferedOutputStream.flush();

      } catch (IOException e) {
        System.err.println("Error: Problem with output stream occurred");
        break;
      }
    }
    bufferedInputStream.close();
    bufferedOutputStream.close();
    connection.disconnect();
  }
}

