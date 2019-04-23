package ch.hsr.epj.localshare.demo.network.transfer.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPNotifier {

  private URL clientUrl;


  public HTTPNotifier(URL clientUrl) {
    this.clientUrl = clientUrl;
  }

  public void putNotification() throws IOException {
    HttpURLConnection connection = (HttpURLConnection) clientUrl.openConnection();
    connection.setRequestMethod("PUT");
    connection.connect();

    int status = connection.getResponseCode();
    System.out.println(status);

    connection.disconnect();
  }
}
