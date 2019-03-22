package ch.hsr.epj.localshare.demo.logic;

import ch.hsr.epj.localshare.demo.gui.application.PeerUpdaterIF;
import ch.hsr.epj.localshare.demo.network.discovery.discovery.DiscoveredIPList;
import ch.hsr.epj.localshare.demo.network.discovery.discovery.Discovery;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DiscoveryController {

  private final Set<PeerUpdaterIF> observers = Collections
      .newSetFromMap(new ConcurrentHashMap<PeerUpdaterIF, Boolean>(0));
  Discovery discovery;

  public DiscoveryController(PeerUpdaterIF observer) {
    observers.add(observer);
    DiscoveredIPList.getInstance().addDiscoveryController(this);
    startDiscovery();
  }

  public void notifyObservers(String[] event) {
    observers.forEach(observer -> observer.update(event));
  }

  private void startDiscovery() {
    discovery = new Discovery();
    Thread thread = new Thread(discovery);
    thread.setDaemon(true);
    thread.start();
  }


}
