package ch.hsr.epj.localshare.demo.gui.presentation;

import static org.junit.Assert.assertEquals;
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

  @Test
  public void testGetIP() {
    String expteced = "192.168.100.100";
    Peer p1 = new Peer(expteced, "Peer 1", "", "aasd98asdas8d7");
    assertEquals(expteced, p1.getIP());
  }

  @Test
  public void testGetFriendlyName() {
    String expteced = "Peer 1";
    Peer p1 = new Peer("192.168.100.100", expteced, "", "aasd98asdas8d7");
    assertEquals(expteced, p1.getFriendlyName());
  }

  @Test
  public void testSetFriendlyName() {
    String expteced = "Peer 1";
    Peer p1 = new Peer("192.168.100.100", "", "", "aasd98asdas8d7");
    p1.setFriendlyName(expteced);
    assertEquals(expteced, p1.getFriendlyName());
  }

  @Test
  public void testGetDisplayName() {
    String expteced = "Foo";
    Peer p1 = new Peer("192.168.100.100", "Peer 1", "Foo", "aasd98asdas8d7");
    assertEquals(expteced, p1.getDisplayName());
  }

  @Test
  public void testSetDisplayName() {
    String expteced = "Foo";
    Peer p1 = new Peer("192.168.100.100", "Peer 1", "", "aasd98asdas8d7");
    p1.setDisplayName(expteced);
    assertEquals(expteced, p1.getDisplayName());
  }

  @Test
  public void testGetFingerprint() {
    String expteced = "AFFE AFFE AFFE AFFE";
    Peer p1 = new Peer("192.168.100.100", "Peer 1", "Foo", "AFFE AFFE AFFE AFFE");
    assertEquals(expteced, p1.getFingerPrint());
  }

  @Test
  public void testSetFingerprint() {
    String expteced = "AFFE AFFE AFFE AFFE";
    Peer p1 = new Peer("192.168.100.100", "Peer 1", "Foo", "");
    p1.setFingerPrint(expteced);
    assertEquals(expteced, p1.getFingerPrint());
  }

  @Test
  public void testGetFingerprintShort() {
    String expteced = "AFFE AFFE";
    Peer p1 = new Peer("192.168.100.100", "Peer 1", "Foo", "AFFE AFFE AFFE AFFE");
    assertEquals(expteced, p1.getFingerPrintShort());
  }

  @Test
  public void testGetTrustStateFalse() {
    Peer p1 = new Peer("192.168.100.100", "Peer 1", "Foo", "AFFE AFFE AFFE AFFE");
    assertFalse(p1.getTrustState());
  }

  @Test
  public void testGetTrustStateTrue() {
    Peer p1 = new Peer("192.168.100.100", "Peer 1", "Foo", "AFFE AFFE AFFE AFFE");
    p1.setTrustState(true);
    assertTrue(p1.getTrustState());
  }

  @Test
  public void testPeerToString() {
    String expeted = "192.168.100.100 Peer 1";
    Peer p1 = new Peer("192.168.100.100", "Peer 1", "Foo", "AFFE AFFE AFFE AFFE");
    assertEquals(expeted, p1.toString());
  }
}
