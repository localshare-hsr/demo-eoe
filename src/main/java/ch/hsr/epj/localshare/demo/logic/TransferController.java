package ch.hsr.epj.localshare.demo.logic;

import ch.hsr.epj.localshare.demo.network.transfer.LsHttpServer;

public class TransferController {

  private LsHttpServer server;

  public TransferController() {
    startHttpServer();
  }

  private void startHttpServer() {
    server = new LsHttpServer();
    Thread serverThread = new Thread(server);
    serverThread.setDaemon(true);
    serverThread.start();
  }

  public void serveFileInChannel(String filePath, String channelName) {
    server.serveFileInChannel(filePath, channelName);
  }

  public void serveFileInPrivate(String filePath) {
    server.serveFileInPrivate(filePath);
  }
}
