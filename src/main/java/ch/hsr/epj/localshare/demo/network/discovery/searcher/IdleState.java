package ch.hsr.epj.localshare.demo.network.discovery.searcher;

import ch.hsr.epj.localshare.demo.network.discovery.IPResource;
import java.util.logging.Level;
import java.util.logging.Logger;

class IdleState extends Statemachine {

  private static final String STATE_NAME = "IDLE";
  private static Logger logger = Logger.getLogger(IdleState.class.getName());

  IdleState() {
    logger.fine("change state:  ===> " + STATE_NAME);
    try {
      waitTillDiscoveryByNextPeer();
    } catch (InterruptedException e) {
      logger.log(Level.WARNING, "This thread is not really sleepy", e);
      Thread.currentThread().interrupt();
    }
  }

  private void waitTillDiscoveryByNextPeer() throws InterruptedException {

    while (!IPResource.getInstance().hasNextPeer()) {
      Thread.sleep(10000);
    }

    state = new UpdateState();
  }
}
