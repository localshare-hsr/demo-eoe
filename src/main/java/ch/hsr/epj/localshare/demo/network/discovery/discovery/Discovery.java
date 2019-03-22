package ch.hsr.epj.localshare.demo.network.discovery.discovery;

import ch.hsr.epj.localshare.demo.network.discovery.statemachine.NetworkDiscovery;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import org.apache.commons.net.util.SubnetUtils;

public class Discovery implements Runnable {

  public Discovery() {
    System.out.println("Hello Ouroboros");
  }

  private InetAddress findMyIPAddress() {
    InetAddress myIP = null;
    Socket socket = new Socket();
    try {
      socket.connect(new InetSocketAddress("example.com", 80));
      myIP = InetAddress.getByName(socket.getLocalAddress().getHostAddress());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return myIP;
  }

  private String findMySubnetmask(InetAddress myIPAddress) throws SocketException {
    NetworkInterface networkInterface = NetworkInterface.getByInetAddress(myIPAddress);
    return Integer.toString(
        networkInterface.getInterfaceAddresses().get(1).getNetworkPrefixLength());
  }

  private String[] generateListOfAllIPsInSubnet(String ip, String subnetmask) {
    SubnetUtils utils = new SubnetUtils(ip + "/" + subnetmask);
    return utils.getInfo().getAllAddresses();
  }

  @Override
  public void run() {
    startOuroboros();
  }

  private void startOuroboros() {
    String[] listOfIPsToProbe = null;
    try {
      /*      listOfIPsToProbe = generateListOfAllIPsInSubnet(path);*/
      InetAddress myIP = findMyIPAddress();
      listOfIPsToProbe = generateListOfAllIPsInSubnet(myIP.getHostAddress(),
          findMySubnetmask(myIP));
      DiscoveredIPList.getInstance().setIdentity(findMyIPAddress().getHostAddress());

    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }

    try {
      Thread serverThread = new Thread(new OuroborosUDPServer());
      serverThread.setDaemon(true);
      serverThread.start();
      NetworkDiscovery discovery = new NetworkDiscovery();
      discovery.addListOfIPsToScan(listOfIPsToProbe);
      Thread discoveryThread = new Thread(discovery);
      discoveryThread.setDaemon(true);
      discoveryThread.start();

      // block main thread
      serverThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
