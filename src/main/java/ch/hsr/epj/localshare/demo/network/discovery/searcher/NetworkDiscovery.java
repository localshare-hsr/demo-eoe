package ch.hsr.epj.localshare.demo.network.discovery.searcher;

import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;

public class NetworkDiscovery {

    public NetworkDiscovery() {
    }

    public void startSearchProcess() {
        System.out.println("Starting Network DiscoveryManager");
        String[] listOfIPsToProbe =
                IPAddressUtil.generateIPsInNetmask(IPAddressUtil.getLocalIPAddress());

        Statemachine discovery = new Statemachine();
        discovery.addListOfIPsToScan(listOfIPsToProbe);
        discovery.run();
/*    Thread discoveryThread = new Thread(discovery);
    discoveryThread.setDaemon(true);
    discoveryThread.start();*/
    }

}
