package ch.hsr.epj.localshare.demo.logic;

import ch.hsr.epj.localshare.demo.network.transfer.HTTPProgress;
import ch.hsr.epj.localshare.demo.network.transfer.HTTPServer;
import java.io.File;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class HttpServerController implements Observer {

  private HTTPServer httpServer;

  public HttpServerController() {
    startHttpServer();
  }

  private void startHttpServer() {
    httpServer = new HTTPServer();
  }

  public void stopHTTPServer() {
    httpServer.stopHTTPServer();
  }

  public void shareChannel(String filePath, long fileSize, String channelName) {
    //server.serveFileInChannel(filePath, channelName);
  }

  public void sharePrivate(String filePath, List<File> files) {
    SecureRandom secureRandom = new SecureRandom();
    byte key[] = new byte[16];
    secureRandom.nextBytes(key);
    String privatePath = new String(Base64.getUrlEncoder().encode(key));
    System.out.println("private path for share is: " + privatePath);
    HTTPProgress httpProgress = httpServer.createNewShare(privatePath, files);
    httpProgress.addObserver(this);
  }

  @Override
  public void update(Observable o, Object arg) {
    int percent = (int) arg;
    System.out.println("Upload completeness = " + percent + "%");
  }
}
