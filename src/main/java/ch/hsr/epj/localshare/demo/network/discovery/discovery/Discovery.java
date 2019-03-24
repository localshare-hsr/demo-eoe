package ch.hsr.epj.localshare.demo.network.discovery.discovery;

import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;

public class Discovery {

  NetworkDiscovery networkDiscovery;

  public Discovery() {
    DiscoveredIPList.getInstance().setIdentity(IPAddressUtil.getLocalIPAddress().getHostAddress());
    startServer();
  }

  private void startServer() {
    System.out.println("Starting Server");

    Thread serverThread = new Thread(new OuroborosUDPServer());
    serverThread.setDaemon(true);
    serverThread.start();
  }

  public void startSearchingProcess() {
    System.out.println("Starting Searching Process");
    networkDiscovery = new NetworkDiscovery();
    Thread searchingThread = new Thread(networkDiscovery);
    searchingThread.setDaemon(true);
    searchingThread.start();
  }
}
