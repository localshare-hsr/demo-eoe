package ch.hsr.epj.localshare.demo.network.transfer.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPMetaDownloader {


  private URL metaUrl;


  public void getMetaDataFromPeer() throws IOException {
    HttpURLConnection connection = (HttpURLConnection) metaUrl.openConnection();
    connection.setRequestMethod("GET");
    connection.setDoOutput(true);
    connection.connect();
  }
}

