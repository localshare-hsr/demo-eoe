package ch.hsr.epj.localshare.demo.gui.presentation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class PeerTest {

  @Test
  public void testEqualPeersSameName() {
    Peer p1 = new Peer("192.168.100.100", "Peer 1", "", "aasd98asdas8d7");
    Peer p2 = new Peer("192.168.100.100", "Peer 1", "", "aasd98asdas8d7");

    assertTrue(p1.equals(p2));
  }

  @Test
  public void testEqualPeersDifferentName() {
    Peer p1 = new Peer("192.168.100.100", "Peer 1", "", "aasd98asdas8d7");
    Peer p2 = new Peer("192.168.100.100", "Peer 2", "", "aasd98asdas8d7");

    assertTrue(p1.equals(p2));
  }

  @Test
  public void testNonEqualPeersDifferentName() {
    Peer p1 = new Peer("192.168.100.100", "Peer 1", "", "aasd98asdas8d7");
    Peer p2 = new Peer("192.168.100.101", "Peer 2", "", "aasd98asdas8d7");

    assertFalse(p1.equals(p2));
  }

  @Test
  public void testListContains() {
    Peer p1 = new Peer("192.168.100.100", "Peer 1", "", "aasd98asdas8d7");
    Peer p2 = new Peer("192.168.100.101", "Peer 2", "", "aasd98asdas8d7");
    Peer p3 = new Peer("192.168.100.100", "Peer 3", "", "aasd98asdas8d7");
    List<Peer> listOfPeers = new ArrayList<>();
    listOfPeers.add(p1);
    listOfPeers.add(p2);

    assertTrue(listOfPeers.contains(p3));
  }

  @Test
  public void testListContainsNot() {
    Peer p1 = new Peer("192.168.100.100", "Peer 1", "", "aasd98asdas8d7");
    Peer p2 = new Peer("192.168.100.101", "Peer 2", "", "aasd98asdas8d7");
    Peer p3 = new Peer("192.168.100.99", "Peer 3", "", "aasd98asdas8d7");
    List<Peer> listOfPeers = new ArrayList<>();
    listOfPeers.add(p1);
    listOfPeers.add(p2);

    assertFalse(listOfPeers.contains(p3));
  }
}
