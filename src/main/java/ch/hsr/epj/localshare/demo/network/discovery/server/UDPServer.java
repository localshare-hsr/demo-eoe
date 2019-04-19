package ch.hsr.epj.localshare.demo.network.discovery.server;

import ch.hsr.epj.localshare.demo.network.discovery.IPResource;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class UDPServer implements Runnable {

  private static Logger logger = Logger.getLogger(UDPServer.class.getName());
  private final int bufferSize;
  private final int port;

  private UDPServer(int port, int bufferSize) {
    this.bufferSize = bufferSize;
    this.port = port;
  }

  UDPServer(int port) {
    this(port, 512);
  }

  @Override
  public void run() {
    try {
      InetAddress myIP = InetAddress.getByName(IPResource.getInstance().getIdentity());
      startUDPServer(myIP);
    } catch (UnknownHostException e) {
      logger.log(Level.WARNING, "Unable to start UDP Server", e);
    }
  }

  public abstract void respond(DatagramSocket socket, DatagramPacket request) throws IOException;

  private void startUDPServer(InetAddress myIP) {
    byte[] buffer = new byte[bufferSize];
    try (DatagramSocket socket = new DatagramSocket(port, myIP)) {
      logger.log(
          Level.INFO, "Listen on " + socket.getLocalAddress() + " port " + socket.getLocalPort());
      boolean isRunning = true;
      while (isRunning) {
        DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
        isRunning = processIncomingMessage(incoming, socket);
      }
    } catch (SocketException e) {
      logger.log(Level.WARNING, "Unable to start UDP Server", e);
    }
  }

  private boolean processIncomingMessage(DatagramPacket incoming, DatagramSocket socket) {
    boolean isRunning = true;
    try {
      socket.receive(incoming);
      this.respond(socket, incoming);
    } catch (IOException e) {
      logger.log(Level.WARNING, "UDP Server crashed", e);
      isRunning = false;
    }

    return isRunning;
  }
}
