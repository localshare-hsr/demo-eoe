package ch.hsr.epj.localshare.demo.network.discovery.searcher;

import ch.hsr.epj.localshare.demo.network.discovery.IPResource;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

class UpdateState extends Statemachine {

  private static final String STATE_NAME = "UPDATE";
  private static final int PORT = 8640;
    private static Logger logger = Logger.getLogger(UpdateState.class.getName());

  UpdateState() {
      logger.fine("change state:  ===> " + STATE_NAME);
    try {
      getUpdateFromNextPeer();
    } catch (InterruptedException e) {
        logger.log(Level.WARNING, "This thread is not really sleepy", e);
        Thread.currentThread().interrupt();
    }
  }

    private void getUpdateFromNextPeer() throws InterruptedException {
    while (IPResource.getInstance().hasNextPeer()) {

      try (DatagramSocket datagramSocket = new DatagramSocket(0)) {
          sendResponseToNextPeer(datagramSocket);

      } catch (IOException e) {
          logger.log(Level.WARNING, "Unable to create new DatagramSocket", e);
      }

        Thread.sleep(9000);
    }

        state = new IdleState();
    }

    private void sendResponseToNextPeer(DatagramSocket datagramSocket) throws IOException {
        String nextPeer = IPResource.getInstance().getNextPeer();
        byte[] buffer = "U".getBytes();
        InetAddress targetAddress = InetAddress.getByName(nextPeer);
        DatagramPacket request = new DatagramPacket(buffer, buffer.length, targetAddress, PORT);
        datagramSocket.send(request);
  }
}
