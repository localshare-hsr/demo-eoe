package ch.hsr.epj.localshare.demo.network.discovery.searcher;

import ch.hsr.epj.localshare.demo.network.discovery.IPResource;

class IdleState extends Statemachine {

  private static final String STATE_NAME = "IDLE";

  IdleState() {
    System.out.println("change state:  ===> " + STATE_NAME);
    try {
      waitTillDiscoveryByNextPeer();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void waitTillDiscoveryByNextPeer() throws InterruptedException {

    while (!IPResource.getInstance().hasNextPeer()) {
      Thread.sleep(10000);
    }

    state = new UpdateState();
  }
}
