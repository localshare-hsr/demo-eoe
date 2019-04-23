package ch.hsr.epj.localshare.demo.network.transfer.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HTTPDownloader {

  private static final int BUFFER_SIZE = 1024;
  private static final int EOF = -1;

  private URL url;
  private BufferedOutputStream bufferedOutputStream;

  public HTTPDownloader(URL url, BufferedOutputStream bufferedOutputStream) {
    this.url = url;
    this.bufferedOutputStream = bufferedOutputStream;
  }

  public void startDownload() throws IOException {
    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setDoOutput(true);
    connection.connect();
    int status = connection.getResponseCode();

    InputStream inputStream = connection.getInputStream();
    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

    byte[] buffer = new byte[BUFFER_SIZE];
    int byteRead;
    while ((byteRead = bufferedInputStream.read()) != EOF) {
      try {
        bufferedOutputStream.write(buffer);

      } catch (IOException e) {
        System.err.println("Error: Problem with output stream occurred");

        break;
      }
    }
    bufferedOutputStream.flush();
  }

}
