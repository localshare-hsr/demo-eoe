package ch.hsr.epj.localshare.demo.logic;

import ch.hsr.epj.localshare.demo.network.transfer.LsHttpServer;

public class TransferController {

  public TransferController() {
    startHttpServer();
  }

  private void startHttpServer() {
    Thread httpServerThread = new Thread(new LsHttpServer());
    httpServerThread.setDaemon(true);
    httpServerThread.start();
  }
}
