package ch.hsr.epj.localshare.demo.network.discovery.searcher;

import ch.hsr.epj.localshare.demo.network.discovery.IPResource;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

class SearchingState extends Statemachine {

  private static final String STATE_NAME = "SEARCHING";
  private static final int PORT = 8640;
    private static Logger logger = Logger.getLogger(SearchingState.class.getName());

  SearchingState() {
      logger.fine("change state:  ===> " + STATE_NAME);
    try {
      searchNetwork();
    } catch (InterruptedException e) {
        logger.log(Level.WARNING, "This thread is not really sleepy", e);
        Thread.currentThread().interrupt();
    }
  }

  private void searchNetwork() throws InterruptedException {
    try {
      startNetworkScan();
    } catch (IOException e) {
        logger.log(Level.WARNING, "Unable to start network search scan", e);
    }

    boolean foundOtherPeer = IPResource.getInstance().hasNextPeer();

    if (foundOtherPeer) {
      state = new UpdateState();
    } else {
      state = new IdleState();
    }
  }

  private void startNetworkScan() throws IOException, InterruptedException {

    Thread.sleep(200); // small delay to start up the listening server first
    long startTimer = System.currentTimeMillis();
      try (DatagramSocket datagramSocket = new DatagramSocket(0)) {
          byte[] buffer = "D".getBytes();

          for (String s : startIP()) {

              InetAddress targetAddress = InetAddress.getByName(s);
              DatagramPacket request = new DatagramPacket(buffer, buffer.length, targetAddress, PORT);
              datagramSocket.send(request);
              Thread.sleep(100);

              if (IPResource.getInstance().hasNextPeer()) {
                  return;
              }
          }
      }
    long endTimer = System.currentTimeMillis();

      logger.log(Level.INFO, "IP Ranges scaned in {0} ms", (endTimer - startTimer));
  }

  private String[] startIP() {

    String[] newIPList = new String[listOfIps.length];
    int positionOfMyAddress = 0;

    for (int i = 0; i < listOfIps.length; i++) {
      if (listOfIps[i].equals(IPResource.getInstance().getIdentity())) {
        positionOfMyAddress = i;
        break;
      }
    }

    int firstObject = positionOfMyAddress + 1;
    int length = listOfIps.length - firstObject;

    System.arraycopy(listOfIps, firstObject, newIPList, 0, length);
    System.arraycopy(listOfIps, 0, newIPList, length, newIPList.length - length);

    return newIPList;
  }
}
