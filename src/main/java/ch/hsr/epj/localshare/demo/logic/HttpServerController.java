package ch.hsr.epj.localshare.demo.logic;

import ch.hsr.epj.localshare.demo.network.transfer.LsHttpServer;

public class HttpServerController {

  public HttpServerController() {
    startHttpServer();
  }

  private void startHttpServer() {
    server = new LsHttpServer();
    server.run();
  }

  public void shareChannel(String filePath, String channelName) {
    server.serveFileInChannel(filePath, channelName);
  }

  public void sharePrivate(String filePath) {
      server.serveFileInPrivate(filePath);
  }

  private LsHttpServer server;
}
