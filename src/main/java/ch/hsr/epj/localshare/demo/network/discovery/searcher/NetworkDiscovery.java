package ch.hsr.epj.localshare.demo.network.discovery.searcher;

import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;

public class NetworkDiscovery {

  public void startSearchProcess() {
    String[] listOfIPsToProbe =
        IPAddressUtil.generateIPsInNetmask(IPAddressUtil.getLocalIPAddress());

    Statemachine discovery = new Statemachine();
    Statemachine.addListOfIPsToScan(listOfIPsToProbe);
    discovery.run();
  }
}
