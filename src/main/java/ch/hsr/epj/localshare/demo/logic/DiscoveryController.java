package ch.hsr.epj.localshare.demo.logic;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
import ch.hsr.epj.localshare.demo.network.discovery.IPResource;
import ch.hsr.epj.localshare.demo.network.discovery.searcher.NetworkDiscovery;
import ch.hsr.epj.localshare.demo.network.discovery.server.OuroborosUDPServer;
import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class DiscoveryController implements Observer {

  private ObservableList<Peer> peerObservableList;

  public DiscoveryController(ObservableList<Peer> peerObservableList) {
    this.peerObservableList = peerObservableList;
    IPResource.getInstance().addObserver(this);
  }

  public void startServer() {
    startDaemonTask(new Task<Void>() {
      @Override
      protected Void call() {
        IPResource.getInstance().setIdentity(IPAddressUtil.getLocalIPAddress().getHostAddress());
        new OuroborosUDPServer().run();
        return null;
      }
    });
  }

  public void startSearcher() {
    startDaemonTask(new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        NetworkDiscovery networkDiscovery = new NetworkDiscovery();
        networkDiscovery.startSearchProcess();
        return null;
      }
    });
  }

  private void startDaemonTask(Runnable runnable) {
    Thread discoverySearcherThread = new Thread(runnable);
    discoverySearcherThread.setDaemon(true);
    discoverySearcherThread.start();
  }

  @Override
  public void update(Observable o, Object arg) {
    System.out.println("update called: " + Arrays.toString((String[]) arg));
    List<Peer> newPeerList = new ArrayList<>();
    String[] event = (String[]) arg;
    for (String ip : event) {
      newPeerList.add(new Peer(ip, "LS user", "", "aasd98asdas8d7"));
    }

    for (Peer p : peerObservableList) {
      if (!newPeerList.contains(p)) {
        Platform.runLater(() -> peerObservableList.remove(p));
      }
    }

    for (Peer p : newPeerList) {
      if (!peerObservableList.contains(p)) {
        Platform.runLater(() -> peerObservableList.add(p));
      }
    }
  }
}
