package ch.hsr.epj.localshare.demo.network.transfer.client;

import ch.hsr.epj.localshare.demo.logic.Transfer;
import ch.hsr.epj.localshare.demo.network.transfer.utils.UrlFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HTTPNotifier {

  private static final Logger logger = Logger.getLogger(HTTPNotifier.class.getName());

  public void sendNotification(Transfer transfer) throws IOException {
    URL url = UrlFactory.generateNotifyUrl(transfer.getPeerAddress());
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("PUT");
    connection.setRequestProperty("Connection", "close");
    connection.setRequestProperty("X-Resource", transfer.getFileUri());
    connection.connect();
    int status = connection.getResponseCode();
    if (status == 200) {
      logger.log(Level.INFO, "Send notification and received HTTP 200 OK");
    } else {
      logger.log(Level.WARNING, "Send notification but received {0}", status);
    }
    connection.disconnect();
  }
}
