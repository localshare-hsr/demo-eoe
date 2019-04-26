package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import ch.hsr.epj.localshare.demo.logic.Transfer;
import ch.hsr.epj.localshare.demo.network.transfer.HTTPProgress;
import ch.hsr.epj.localshare.demo.network.transfer.server.HTTPServer;
import java.io.File;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpServerController implements Observer {

  private static final Logger logger = Logger.getLogger(HttpServerController.class.getName());

  private HTTPServer httpServer;
  private HttpClientController httpClientController;

  public HttpServerController() {
    startHttpServer();
  }

  private void startHttpServer() {
    httpServer = new HTTPServer(this);
  }

  public void connectClientController(HttpClientController httpClientController) {
    this.httpClientController = httpClientController;
  }

  public void stopHTTPServer() {
    httpServer.stopHTTPServer();
  }

  public void shareChannel(String filePath, long fileSize, String channelName) {
    // server.serveFileInChannel(filePath, channelName);
  }

  public void sharePrivate(InetAddress peer, List<File> files) {
    SecureRandom secureRandom = new SecureRandom();
    byte[] key = new byte[16];
    secureRandom.nextBytes(key);
    String privatePath = new String(Base64.getUrlEncoder().encode(key));
    HTTPProgress httpProgress = httpServer.createNewShare(privatePath, files);
    httpProgress.addObserver(this);
    Transfer transfer = new Transfer(peer, privatePath);
    httpClientController.sendNotification(transfer);
  }

  @Override
  public void update(Observable o, Object arg) {
    int percent = (int) arg;
    if (percent % 20 == 0) {
      logger.log(Level.INFO, "Upload completeness = {0}%", percent);
    }
  }

  public void receivedNotification(Transfer transfer) {
    httpClientController.getMetadataFromPeer(transfer);
  }
}
