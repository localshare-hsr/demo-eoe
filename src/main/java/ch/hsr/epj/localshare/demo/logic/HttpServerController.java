package ch.hsr.epj.localshare.demo.logic;

import ch.hsr.epj.localshare.demo.network.transfer.HTTPServer;
import java.io.File;
import java.util.List;

public class HttpServerController {

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
    httpServer.createNewShare(filePath, files);
  }
}
