package ch.hsr.epj.localshare.demo.logic;

import ch.hsr.epj.localshare.demo.gui.application.PeerUpdaterIF;
import ch.hsr.epj.localshare.demo.network.discovery.discovery.DiscoveredIPList;
import ch.hsr.epj.localshare.demo.network.discovery.discovery.Discovery;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javafx.application.Platform;

public class DiscoveryController {

  private final Set<PeerUpdaterIF> observers =
      Collections.newSetFromMap(new ConcurrentHashMap<>(0));
  private Discovery discovery;

  public DiscoveryController(PeerUpdaterIF observer) {
    discovery = new Discovery();
    observers.add(observer);
    DiscoveredIPList.getInstance().addDiscoveryController(this);
  }

  public void notifyObservers(String[] event) {
    Platform.runLater(() -> observers.forEach(observer -> observer.update(event)));
  }

  public void startSearch() {
    discovery.startSearchingProcess();
  }
}
