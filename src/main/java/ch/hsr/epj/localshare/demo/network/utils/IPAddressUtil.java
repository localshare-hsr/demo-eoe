package ch.hsr.epj.localshare.demo.network.utils;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.util.SubnetUtils;

public class IPAddressUtil {

  private static final Logger logger = Logger.getLogger(IPAddressUtil.class.getName());

  private IPAddressUtil() {
  }

  public static InetAddress getLocalIPAddress() {
    InetAddress myIP = null;
    try (Socket socket = new Socket()) {
      socket.connect(new InetSocketAddress("www.hsr.ch", 80));
      myIP = InetAddress.getByName(socket.getLocalAddress().getHostAddress());
    } catch (IOException e) {
      logger.log(Level.INFO, "No Internet connection available");
    }
    return myIP;
  }

  public static String[] generateIPsInNetmask(InetAddress myIPAddress) {
    String netmask = "";
    try {
      netmask = getLocalNetmask(myIPAddress);
    } catch (SocketException e) {
      logger.log(Level.INFO, "Unable to calculate netmask", e);
    }
    SubnetUtils utils = new SubnetUtils(myIPAddress.getHostAddress() + "/" + netmask);
    return utils.getInfo().getAllAddresses();
  }

  private static String getLocalNetmask(InetAddress myIPAddress) throws SocketException {
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

    return Integer.toString(subnetmask);
  }

}
