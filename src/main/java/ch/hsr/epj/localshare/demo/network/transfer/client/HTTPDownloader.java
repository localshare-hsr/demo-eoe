package ch.hsr.epj.localshare.demo.network.transfer.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPDownloader implements Runnable {

  private static final int BUFFER_SIZE = 1024;
  private static final int EOF = -1;

  private URL url;
  private BufferedOutputStream bufferedOutputStream;

  public HTTPDownloader(URL url, BufferedOutputStream bufferedOutputStream) {
    this.url = url;
    this.bufferedOutputStream = bufferedOutputStream;
  }

  public void startDownload() throws IOException {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setDoOutput(true);
    connection.connect();
    int status = connection.getResponseCode();

    long contentlength = Long.parseLong(connection.getHeaderField("Content-Length"));
    InputStream inputStream = connection.getInputStream();
    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

    byte[] buffer = new byte[BUFFER_SIZE];
    int byteRead;
    long totalbyteread = 0;
    while ((byteRead = bufferedInputStream.read(buffer)) != EOF) {
      try {
        bufferedOutputStream.write(buffer);
        totalbyteread += byteRead;
        System.out.println(totalbyteread);

      } catch (IOException e) {
        System.err.println("Error: Problem with output stream occurred");
        break;
      }
    }
    bufferedOutputStream.flush();
    bufferedInputStream.close();
    bufferedOutputStream.close();
    connection.disconnect();
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
