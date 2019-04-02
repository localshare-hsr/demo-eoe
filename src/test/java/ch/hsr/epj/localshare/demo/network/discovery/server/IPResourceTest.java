package ch.hsr.epj.localshare.demo.network.discovery.server;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ch.hsr.epj.localshare.demo.network.discovery.IPResource;
import java.lang.reflect.Field;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class IPResourceTest {

  @Before
  public void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
      Field instance = IPResource.class.getDeclaredField("instance");
    instance.setAccessible(true);
    instance.set(null, null);
  }

    @Test
    public void testAddDoubleEntries() {
        IPResource ipList = IPResource.getInstance();
        ipList.setIdentity("192.168.100.141");
        ipList.add("192.168.100.42");
        ipList.add("192.168.100.42");
        String[] expected = {"192.168.100.42"};
        assertArrayEquals(expected, ipList.getArray());
    }

  @Test
  public void testInsertOneNewIPBelow() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");
    ipList.add("192.168.42.40");
      String[] expected = {"192.168.42.40"};
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testInsertTwoDescNewIPBelow() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");
    ipList.add("192.168.42.40");
    ipList.add("192.168.42.35");
      String[] expected = {"192.168.42.35", "192.168.42.40"};
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testInsertTwoAscNewIPBelow() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");
    ipList.add("192.168.42.35");
    ipList.add("192.168.42.40");
      String[] expected = {"192.168.42.35", "192.168.42.40"};
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testInsertThreeDescNewIPBelow() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");
    ipList.add("192.168.42.40");
    ipList.add("192.168.42.35");
    ipList.add("192.168.42.30");
      String[] expected = {"192.168.42.30", "192.168.42.35", "192.168.42.40"};
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testInsertThreeAscNewIPBelow() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");
    ipList.add("192.168.42.30");
    ipList.add("192.168.42.35");
    ipList.add("192.168.42.40");
      String[] expected = {"192.168.42.30", "192.168.42.35", "192.168.42.40"};
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testInsertThreeMixedNewIPBelow() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");
    ipList.add("192.168.42.35");
    ipList.add("192.168.42.30");
    ipList.add("192.168.42.40");
      String[] expected = {"192.168.42.30", "192.168.42.35", "192.168.42.40"};
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testInsertOneNewIPAbove() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");
    ipList.add("192.168.42.45");
      String[] expected = {"192.168.42.45"};
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testInsertTwoDescNewIPAbove() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");
    ipList.add("192.168.42.45");
    ipList.add("192.168.42.50");
      String[] expected = {"192.168.42.45", "192.168.42.50"};
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testInsertTwoAscNewIPAbove() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");
    ipList.add("192.168.42.50");
    ipList.add("192.168.42.45");
      String[] expected = {"192.168.42.45", "192.168.42.50"};
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testInsertThreeDescNewIPAbove() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");
    ipList.add("192.168.42.45");
    ipList.add("192.168.42.50");
    ipList.add("192.168.42.55");
      String[] expected = {"192.168.42.45", "192.168.42.50", "192.168.42.55"};
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testInsertThreeAscNewIPAbove() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");
    ipList.add("192.168.42.55");
    ipList.add("192.168.42.50");
    ipList.add("192.168.42.45");
      String[] expected = {"192.168.42.45", "192.168.42.50", "192.168.42.55"};
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testInsertThreeMixedNewIPAbove() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");
    ipList.add("192.168.42.50");
    ipList.add("192.168.42.55");
    ipList.add("192.168.42.45");
      String[] expected = {"192.168.42.45", "192.168.42.50", "192.168.42.55"};
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testInsertDiverseIPs() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");
    ipList.add("192.168.42.5");
    ipList.add("192.168.41.50");
    ipList.add("192.168.42.250");
    ipList.add("192.168.42.55");
    ipList.add("192.168.43.45");
    String[] expected = {
        "192.168.41.50",
        "192.168.42.5",
        "192.168.42.55",
        "192.168.42.250",
        "192.168.43.45"
    };
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testUpdateIPListTwoEntries() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");

    String[] updateData = {"192.168.42.40", "192.168.42.42"};

    ipList.updateCompleteIPList(updateData);
      String[] expected = {"192.168.42.40"};
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testUpdateIPListOneEntries() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");

    String[] updateData = {"192.168.42.40"};

    ipList.updateCompleteIPList(updateData);
      String[] expected = {"192.168.42.40"};
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testRangeUpdateFromSmallerIP() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");

    String[] updateData = {
        "192.168.42.30",
        "192.168.42.36",
        "192.168.42.37",
        "192.168.42.39",
        "192.168.42.40",
        "192.168.42.42",
        "192.168.42.44",
        "192.168.42.50",
        "192.168.42.67"
    };
    ipList.updateCompleteIPList(updateData);

      ipList.removeAllEntriesFromTillMyIdentity("192.168.42.36");

    String[] expected = {
        "192.168.42.30",
        "192.168.42.36",
        "192.168.42.44",
        "192.168.42.50",
        "192.168.42.67"
    };
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testRangeUpdateFromBiggerIP() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");

    String[] updateData = {
        "192.168.42.30",
        "192.168.42.36",
        "192.168.42.37",
        "192.168.42.39",
        "192.168.42.40",
        "192.168.42.42",
        "192.168.42.44",
        "192.168.42.50",
        "192.168.42.67"
    };
    ipList.updateCompleteIPList(updateData);

      ipList.removeAllEntriesFromTillMyIdentity("192.168.42.50");

    String[] expected = {
            "192.168.42.44", "192.168.42.50",
    };
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testRangeUpdateFromSameIP() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");

    String[] updateData = {
        "192.168.42.42",
    };
    ipList.updateCompleteIPList(updateData);

      ipList.removeAllEntriesFromTillMyIdentity("192.168.42.42");

      assertFalse(ipList.hasNextPeer());
  }

  @Test
  public void testRangeUpdateFromBiggerIPInSmallSet() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.100.137");

    String[] updateData = {
        "192.168.100.100", "192.168.100.137",
    };
    ipList.updateCompleteIPList(updateData);

      ipList.removeAllEntriesFromTillMyIdentity("192.168.100.140");

      String[] expected = {"192.168.100.140"};
    assertArrayEquals(expected, ipList.getArray());
  }

  @Test
  public void testHasNextPeerNo() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.100.100");

    assertFalse(ipList.hasNextPeer());
  }

  @Test
  public void testHasNextPeerSelfReference() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.100.100");

    ipList.add("192.168.100.100");

    assertFalse(ipList.hasNextPeer());
  }

  @Test
  public void testHasNextPeerSmaller() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.100.100");

    ipList.add("192.168.100.50");

    assertTrue(ipList.hasNextPeer());
  }

  @Test
  public void testHasNextPeerBigger() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.100.100");

    ipList.add("192.168.100.150");

    assertTrue(ipList.hasNextPeer());
  }

    @Test(expected = NoSuchElementException.class)
  public void testGetNextPeerNo() {
        IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.100.100");

        ipList.getNextPeer();
  }

  @Test
  public void testGetNextPeerSmaller() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.100.100");

    String peerIP = "192.168.100.50";

    ipList.add(peerIP);

    assertEquals(peerIP, ipList.getNextPeer());
  }

  @Test
  public void testGetNextPeerBigger() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.100.100");

    String peerIP = "192.168.100.150";

    ipList.add(peerIP);

    assertEquals(peerIP, ipList.getNextPeer());
  }

  @Test
  public void testGetNextPeerFromListAbove() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.42");

    String[] updateData = {
        "192.168.42.30",
        "192.168.42.36",
        "192.168.42.37",
        "192.168.42.39",
        "192.168.42.40",
        "192.168.42.42",
        "192.168.42.44",
        "192.168.42.50",
        "192.168.42.67"
    };
    ipList.updateCompleteIPList(updateData);

    assertEquals("192.168.42.44", ipList.getNextPeer());
  }

  @Test
  public void testGetNextPeerFromList() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.42.67");

    String[] updateData = {
        "192.168.42.30",
        "192.168.42.36",
        "192.168.42.37",
        "192.168.42.39",
        "192.168.42.40",
        "192.168.42.42",
        "192.168.42.44",
        "192.168.42.50",
        "192.168.42.67"
    };
    ipList.updateCompleteIPList(updateData);

    assertEquals("192.168.42.30", ipList.getNextPeer());
  }

  @Test
  public void testGetNextPeerFromListMixed1() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.100.100");

    String[] updateData = {
        "192.168.100.100", "192.168.100.140", "192.168.100.141",
    };
    ipList.updateCompleteIPList(updateData);

    assertEquals("192.168.100.140", ipList.getNextPeer());
  }

  @Test
  public void testGetNextPeerFromListMixed2() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.100.140");

    String[] updateData = {
        "192.168.100.100", "192.168.100.140", "192.168.100.141",
    };
    ipList.updateCompleteIPList(updateData);

    assertEquals("192.168.100.141", ipList.getNextPeer());
  }

  @Test
  public void testGetNextPeerFromListMixed3() {
      IPResource ipList = IPResource.getInstance();
    ipList.setIdentity("192.168.100.141");

    String[] updateData = {
        "192.168.100.100", "192.168.100.140", "192.168.100.141",
    };
    ipList.updateCompleteIPList(updateData);

    assertEquals("192.168.100.100", ipList.getNextPeer());
  }

  @Test
  public void testGetIdentity() {
      IPResource ipList = IPResource.getInstance();
      ipList.setIdentity("192.168.100.141");
      assertEquals("192.168.100.141", ipList.getIdentity());
  }

    @Test(expected = NoSuchElementException.class)
    public void testRemovePeerInEmptyList() {
        IPResource ipList = IPResource.getInstance();
        ipList.setIdentity("192.168.100.141");
        ipList.removeNextPeer();
    }

    @Test
    public void testRemovePeerInNonEmptyList() {
        IPResource ipList = IPResource.getInstance();
        ipList.setIdentity("192.168.100.141");
        ipList.add("192.168.100.42");
        ipList.removeNextPeer();
        assertFalse(ipList.hasNextPeer());
    }

}