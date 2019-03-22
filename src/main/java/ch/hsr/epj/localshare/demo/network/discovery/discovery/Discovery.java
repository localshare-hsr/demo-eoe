package ch.hsr.epj.localshare.demo.network.discovery.discovery;

import ch.hsr.epj.localshare.demo.network.discovery.statemachine.NetworkDiscovery;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Discovery implements Runnable {

  private File path;

  public Discovery() {
    System.out.println("Hello Ouroboros");
    ClassLoader classLoader = getClass().getClassLoader();
    File path = new File(
        Objects.requireNonNull(classLoader.getResource("ip_lists/ip.list")).getFile());
    System.out.println("Read file " + path);
    this.path = path;

  }

  public String[] getIPasList() {
    return DiscoveredIPList.getInstance().getArray();
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

  private String[] readIPfile(File filename) throws IOException {
    FileReader fileReader = new FileReader(filename);
    BufferedReader bufferedReader = new BufferedReader(fileReader);
    List<String> lines = new ArrayList<>();
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      lines.add(line);
    }
    bufferedReader.close();
    return lines.toArray(new String[0]);
  }

  @Override
  public void run() {
    startOuroboros();

  }

  private void startOuroboros() {
    String[] listOfIPsToProbe = null;
    try {
      listOfIPsToProbe = readIPfile(path);
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
