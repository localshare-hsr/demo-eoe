package ch.hsr.epj.localshare.demo.network.transfer.client;

import ch.hsr.epj.localshare.demo.logic.networkcontroller.Publisher;
import ch.hsr.epj.localshare.demo.network.transfer.utils.UrlFactory;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HTTPNotifier {

  private static final Logger logger = Logger.getLogger(HTTPNotifier.class.getName());

  public void sendNotification(Publisher publisher) throws IOException {
    URL url = UrlFactory.generateNotifyUrl(publisher.getPeerAddress());
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("PUT");
    connection.setRequestProperty("Connection", "close");
    connection.setRequestProperty("X-Resource", publisher.getFileUri());
    connection.connect();
    int status = connection.getResponseCode();
    if (status == 200) {
      logger.log(Level.INFO, "Send notification and received HTTP 200 OK");
    } else {
      logger.log(Level.WARNING, "Send notification but received {0}", status);
    }
    connection.disconnect();
  }

  public void checkPeerAvailability(Publisher publisher) throws IOException {

    try {
      URL url = UrlFactory.generateNotifyUrl(publisher.getPeerAddress());
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("HEAD");
      connection.connect();
      connection.disconnect();
    } catch (ConnectException e) {
      logger.log(Level.WARNING, "Peer " + publisher.getPeerAddress() + " not available");
      throw e;
    }
  }
}
