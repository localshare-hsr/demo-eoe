package ch.hsr.epj.localshare.demo.network.discovery;

import ch.hsr.epj.localshare.demo.logic.DiscoveryController;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class IPResource {

  private static IPResource instance;
  private SortedSet<String> setOfDiscoveredIPs;
  private String myIPAddress;
  private DiscoveryController discoveryController = null;

  private IPResource() {
    setOfDiscoveredIPs = new TreeSet<>(Comparator.comparing(this::toNumeric));
  }

  public static synchronized IPResource getInstance() {
    if (instance == null) {
      instance = new IPResource();
    }
    return instance;
  }

  /**
   * Set ip address of current instance.
   */
  public synchronized void setIdentity(final String myIPAddress) {
    this.myIPAddress = myIPAddress;
    setOfDiscoveredIPs.add(myIPAddress);
  }

  /**
   * Get ip address of current instance.
   *
   * @return
   */
  public synchronized String getIdentity() {
    return this.myIPAddress;
  }

  /**
   * Add a discovery controller to update the GUI
   */
  public void addDiscoveryController(DiscoveryController discoveryController) {
    this.discoveryController = discoveryController;
  }

  /**
   * Update list of discovered ip addresses.
   *
   * <p>All previous known ip addresses will be removed. List of known ip addresses will be
   * repopulated with newIPlist entries. This method will be called during the periodic update
   * process.
   *
   * @param newIPList List of known ip addresses
   */
  public synchronized void updateCompleteIPList(final String[] newIPList) {
    Collections.addAll(setOfDiscoveredIPs, newIPList);
    if (discoveryController != null) {
      discoveryController.notifyObservers(getArray());
    }
  }

  /**
   * Remove all entries from the newIPAddress till my identity address.
   *
   * @param newIPAddress
   */
  public synchronized void removeAllEntriesFromTillMyIdentity(final String newIPAddress) {
    if (newIPAddress.equals(myIPAddress)) {
      return;
    }
    setOfDiscoveredIPs.add(newIPAddress);
    Iterator<String> positionOfIP = findPositionOfIP(newIPAddress);
    removeIPRangeFrom(positionOfIP);
  }

  /**
   * Check if there is a next peer known
   *
   * @return True if there is a next peer
   */
  public synchronized boolean hasNextPeer() {
    boolean hasNextPeer;
    hasNextPeer = (size() != 1) || !setOfDiscoveredIPs.contains(myIPAddress);

    return hasNextPeer;
  }

  /**
   * Get the next peer from all know ip addresses
   *
   * <p>This returns the next peer relative to own identity.
   *
   * @return IP address as String
   */
  public synchronized String getNextPeer() {
    String currentIP = myIPAddress;
    if (hasNextPeer()) {

      Iterator<String> it = setOfDiscoveredIPs.iterator();
      while (it.hasNext()) {
        currentIP = it.next();

        if (currentIP.equals(myIPAddress)) {
          break;
        }
      }

      if (it.hasNext()) {
        return it.next();
      } else {
        return setOfDiscoveredIPs.iterator().next();
      }
    }

    return currentIP;
  }

  /**
   * Add a single ip address to the list of discovered ip addresses
   *
   * @param newIPAddress New ip address string
   */
  public synchronized void add(final String newIPAddress) {
    setOfDiscoveredIPs.add(newIPAddress);
    if (discoveryController != null) {
      discoveryController.notifyObservers(getArray());
    }
  }

  /**
   * Remove the next peer in the list of all known ip addresses
   */
  public synchronized void removeNextPeer() {
    if (hasNextPeer()) {

      String nextPeer = getNextPeer();

      if (!nextPeer.equals(getIdentity())) {
        setOfDiscoveredIPs.remove(nextPeer);
      }
    }
  }

  /**
   * Get all discovered ip addresses as array of strings
   *
   * @return Array of strings with all currently discovered ip addresses
   */
  public synchronized String[] getArray() {
    return setOfDiscoveredIPs.toArray(new String[0]);
  }

  private Long toNumeric(final String ip) {
    Scanner sc = new Scanner(ip).useDelimiter("\\.");
    return (sc.nextLong() << 24) + (sc.nextLong() << 16) + (sc.nextLong() << 8) + (sc.nextLong());
  }

  private Iterator<String> findPositionOfIP(final String IPToFind) {
    Iterator<String> it = setOfDiscoveredIPs.iterator();
    String currentIP;

    while (it.hasNext()) {
      currentIP = it.next();

      if (currentIP.equals(IPToFind)) {
        break;
      }
    }

    return it;
  }

  private void removeIPRangeFrom(Iterator<String> fromIPPosition) {
    Iterator<String> it = fromIPPosition;
    String currentIP;
    if (it.hasNext()) {
      currentIP = it.next();
    } else {
      it = setOfDiscoveredIPs.iterator();
      currentIP = it.next();
    }
    while (!currentIP.equals(myIPAddress)) {

      it.remove();

      if (!it.hasNext()) {
        it = setOfDiscoveredIPs.iterator();
      }

      currentIP = it.next();
    }
  }

  private synchronized int size() {
    return setOfDiscoveredIPs.size();
  }
}
