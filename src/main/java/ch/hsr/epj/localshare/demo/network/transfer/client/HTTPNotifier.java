package ch.hsr.epj.localshare.demo.network.transfer.client;

import ch.hsr.epj.localshare.demo.logic.Transfer;
import ch.hsr.epj.localshare.demo.network.transfer.utils.UrlFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPNotifier {

  public void sendNotification(Transfer transfer) throws IOException {
    URL url = UrlFactory.generateNotifyUrl(transfer.getPeerAddress());
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("PUT");
    connection.setRequestProperty("X-Resource", transfer.getFileUri());
    connection.connect();

    int status = connection.getResponseCode();
    System.out.println(status);

    connection.disconnect();
  }
}
