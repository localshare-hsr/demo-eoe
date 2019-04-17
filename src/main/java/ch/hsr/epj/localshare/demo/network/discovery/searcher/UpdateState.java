package ch.hsr.epj.localshare.demo.network.discovery.searcher;

import ch.hsr.epj.localshare.demo.network.discovery.IPResource;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

class UpdateState extends Statemachine {

  private static final String STATE_NAME = "UPDATE";
  private static final int PORT = 8640;

  UpdateState() {
    System.out.println("change state:  ===> " + STATE_NAME);
    try {
      getUpdateFromNextPeer();
    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    }
  }

  private void getUpdateFromNextPeer() throws IOException, InterruptedException {
    String nextPeer;
    while (IPResource.getInstance().hasNextPeer()) {
      nextPeer = IPResource.getInstance().getNextPeer();
      System.out.println("  - get updates from " + nextPeer);

      try (DatagramSocket datagramSocket = new DatagramSocket(0)) {
        byte[] buffer = "U".getBytes();

        InetAddress targetAddress = InetAddress.getByName(nextPeer);
        DatagramPacket request = new DatagramPacket(buffer, buffer.length, targetAddress, PORT);
        datagramSocket.send(request);

      } catch (SocketException ignored) {
      }

      Thread.sleep(9000);
    }

    state = new IdleState();
  }
}
