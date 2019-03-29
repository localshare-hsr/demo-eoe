package ch.hsr.epj.localshare.demo.gui.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PeerTest {

  @Test
  public void testEqualPeersSameName() {
    Peer p1 = new Peer("192.168.100.100", "Peer 1");
    Peer p2 = new Peer("192.168.100.100", "Peer 1");

    assertTrue(p1.equals(p2));
  }

  @Test
  public void testEqualPeersDifferentName() {
    Peer p1 = new Peer("192.168.100.100", "Peer 1");
    Peer p2 = new Peer("192.168.100.100", "Peer 2");

    assertTrue(p1.equals(p2));
  }

  @Test
  public void testNonEqualPeersDifferentName() {
    Peer p1 = new Peer("192.168.100.100", "Peer 1");
    Peer p2 = new Peer("192.168.100.101", "Peer 2");

    assertFalse(p1.equals(p2));
  }

}