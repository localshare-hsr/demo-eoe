package ch.hsr.epj.localshare.demo.network.transfer.client;

import ch.hsr.epj.localshare.demo.logic.networkcontroller.Publisher;
import ch.hsr.epj.localshare.demo.network.transfer.utils.UrlFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

public class HTTPPeerChecker implements Runnable {

  private static final Logger logger = Logger.getLogger(HTTPPeerChecker.class.getName());

  private Publisher publisher;
  private URL url;

  public HTTPPeerChecker(Publisher publisher) {
    this.publisher = publisher;
    try {
      url = UrlFactory.generateNotifyUrl(publisher.getPeerAddress());
    } catch (MalformedURLException e) {
      logger.log(Level.WARNING, "URL malcormed");
    }
  }

  @Override
  public void run() {
    try {
      startDownload();
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Could not run download", e);
    }
  }

  private void startDownload() throws IOException {
    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
    connection.setRequestMethod("HEAD");
    connection.setRequestProperty("Connection", "close");
    connection.connect();
    int status = connection.getResponseCode();
    if (status == 200) {
      logger.log(Level.INFO, "Sent notification");
    } else {
      logger.log(Level.SEVERE, "HTTP status code not 200 OK but {0}", status);
    }
    connection.disconnect();
  }

}
