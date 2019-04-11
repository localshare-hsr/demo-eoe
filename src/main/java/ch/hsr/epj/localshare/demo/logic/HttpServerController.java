package ch.hsr.epj.localshare.demo.logic;

import ch.hsr.epj.localshare.demo.network.transfer.HTTPProgress;
import ch.hsr.epj.localshare.demo.network.transfer.HTTPServer;

import java.io.File;
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
    HTTPProgress httpProgress = httpServer.createNewShare(filePath, files);
    httpProgress.addObserver(this);
  }

  @Override
  public void update(Observable o, Object arg) {
    int percent = (int) arg;
    System.out.println("Upload completeness = " + percent + "%");
  }
}
