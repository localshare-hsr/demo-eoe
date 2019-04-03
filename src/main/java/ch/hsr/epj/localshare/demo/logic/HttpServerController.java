package ch.hsr.epj.localshare.demo.logic;

import ch.hsr.epj.localshare.demo.network.transfer.LsHttpServer;

import java.net.InetAddress;

public class HttpServerController {

  public HttpServerController() {
    startHttpServer();
  }

  private void startHttpServer() {
    server = new LsHttpServer();
    server.run();
  }

  public void shareChannel(String filePath, long fileSize, String channelName) {
    server.serveFileInChannel(filePath, channelName);
  }

  public void sharePrivate(String filePath, long fileSize, InetAddress peerIP) {
      server.serveFileInPrivate(filePath);
  }

  private LsHttpServer server;
}
