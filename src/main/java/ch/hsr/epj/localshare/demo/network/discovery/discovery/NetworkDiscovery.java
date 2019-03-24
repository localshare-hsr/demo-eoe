package ch.hsr.epj.localshare.demo.network.discovery.discovery;

import ch.hsr.epj.localshare.demo.network.discovery.statemachine.Statemachine;
import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;

public class NetworkDiscovery implements Runnable {

  public NetworkDiscovery() {
  }

  public void startSearchProcess() {
    System.out.println("Starting Network Discovery");
    String[] listOfIPsToProbe =
        IPAddressUtil.generateIPsInNetmask(IPAddressUtil.getLocalIPAddress());

    Statemachine discovery = new Statemachine();
    discovery.addListOfIPsToScan(listOfIPsToProbe);
    Thread discoveryThread = new Thread(discovery);
    discoveryThread.setDaemon(true);
    discoveryThread.start();
  }

  @Override
  public void run() {
    startSearchProcess();
  }
}
