package ch.hsr.epj.localshare.demo.network.utils;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import org.apache.commons.net.util.SubnetUtils;

public class IPAddressUtil {

  public static InetAddress getLocalIPAddress() {
    InetAddress myIP = null;
    Socket socket = new Socket();
    try {
      socket.connect(new InetSocketAddress("www.hsr.ch", 80));
      myIP = InetAddress.getByName(socket.getLocalAddress().getHostAddress());
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("ip is " + myIP.getHostAddress());
    return myIP;
  }

  public static String getLocalNetmask(InetAddress myIPAddress) throws SocketException {
    NetworkInterface networkInterface = NetworkInterface.getByInetAddress(myIPAddress);

    short subnetmask = 32;

    if (myIPAddress instanceof Inet4Address) {
      for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
        subnetmask = (short) Math.min(subnetmask, interfaceAddress.getNetworkPrefixLength());
      }
    } else {
      for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
        subnetmask = (short) Math.max(subnetmask, interfaceAddress.getNetworkPrefixLength());
      }
    }

    System.out.println("netmask is " + subnetmask);
    return Integer.toString(subnetmask);
  }

  public static String[] generateIPsInNetmask(InetAddress myIPAddress) {
    String netmask = "";
    try {
      netmask = getLocalNetmask(myIPAddress);
    } catch (SocketException e) {
      e.printStackTrace();
    }
    SubnetUtils utils = new SubnetUtils(myIPAddress.getHostAddress() + "/" + netmask);
    return utils.getInfo().getAllAddresses();
  }
}
