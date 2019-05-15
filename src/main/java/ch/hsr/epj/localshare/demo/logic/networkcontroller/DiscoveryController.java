package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import ch.hsr.epj.localshare.demo.gui.presentation.Peer;
import ch.hsr.epj.localshare.demo.network.discovery.IPResource;
import ch.hsr.epj.localshare.demo.network.discovery.searcher.NetworkDiscovery;
import ch.hsr.epj.localshare.demo.network.discovery.server.OuroborosUDPServer;
import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;

public class DiscoveryController implements Observer {

  private static final Logger logger = Logger.getLogger(DiscoveryController.class.getName());

  private HttpClientController httpClientController;

  public DiscoveryController(HttpClientController httpClientController) {
    this.httpClientController = httpClientController;
    IPResource.getInstance().addObserver(this);
  }

  public void startServer() {
    startDaemonTask(
        new Task<Void>() {
          @Override
          protected Void call() {
            IPResource.getInstance()
                .setIdentity(IPAddressUtil.getLocalIPAddress().getHostAddress());
            new OuroborosUDPServer().run();
            return null;
          }
        });
  }

  public void startSearcher() {
    startDaemonTask(
        new Task<Void>() {

          @Override
          protected Void call() {
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
    String[] event = (String[]) arg;
    for (String ip : event) {
      Peer peer = new Peer(ip, "LS User", "", "1337");
      try {
        httpClientController.checkPeerAvailability(peer);
      } catch (IOException e) {
        logger.log(Level.WARNING, "Unable to check peer availability");
      }
    }
  }
}
