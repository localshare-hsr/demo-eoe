package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import ch.hsr.epj.localshare.demo.network.transfer.server.HTTPServer;
import java.io.File;
import java.net.InetAddress;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

public class HttpServerController {

  private HTTPServer httpServer;
  private HttpClientController httpClientController;

  public HttpServerController(KeyStore keystore) {
    startHttpServer(keystore);
  }

  private void startHttpServer(KeyStore keystore) {
    httpServer = new HTTPServer(this, keystore);
  }

  public void connectClientController(HttpClientController httpClientController) {
    this.httpClientController = httpClientController;
  }

  public void stopHTTPServer() {
    httpServer.stopHTTPServer();
  }

  public void sharePrivate(InetAddress peer, List<File> files) {
    SecureRandom secureRandom = new SecureRandom();
    byte[] key = new byte[16];
    secureRandom.nextBytes(key);
    String privatePath = new String(Base64.getUrlEncoder().encode(key));
    httpServer.createNewShare(privatePath, files);
    Publisher publisher = new Publisher(peer, privatePath);
    httpClientController.sendNotification(publisher);
  }

  public void receivedNotification(Publisher publisher) {
    httpClientController.getMetadataFromPeer(publisher);
  }
}
