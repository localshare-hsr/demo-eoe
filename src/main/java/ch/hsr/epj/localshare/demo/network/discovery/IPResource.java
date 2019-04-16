package ch.hsr.epj.localshare.demo.network.discovery;

import java.util.*;

public class IPResource extends Observable {

  private static IPResource instance;
  private SortedSet<String> setOfDiscoveredIPs;
  private String ipAddressOfThisPeerInstance;

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
     * Get ip address of current instance.
     */
  public synchronized String getIdentity() {
    return this.ipAddressOfThisPeerInstance;
  }

    /** Set ip address of current instance. */
  public synchronized void setIdentity(final String myIPAddress) {
    this.ipAddressOfThisPeerInstance = myIPAddress;
    addIPResource(myIPAddress);
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
    for (String s : newIPList) {
      addIPResource(s);
    }
    setChanged();
    notifyObservers(getArray());
  }

    /**
     * Remove all entries from the newIPAddress till my identity address.
     */
    synchronized void removeAllEntriesFromTillMyIdentity(final String newIPAddress) {
    if (newIPAddress.equals(ipAddressOfThisPeerInstance)) {
      return;
    }
    addIPResource(newIPAddress);
    Iterator<String> positionOfIP = findPositionOfIP(newIPAddress);
    removeIPRangeFrom(positionOfIP);
    setChanged();
    notifyObservers(getArray());
  }

  /**
   * Check if there is a next peer known
   *
   * @return True if there is a next peer
   */
  public synchronized boolean hasNextPeer() {
    return sizeOfIPResource() != 0;
  }

  /**
   * Get the next peer from all know ip addresses
   *
   * <p>This returns the next peer relative to own identity.
   *
   * @return IP address as String
   */
  public synchronized String getNextPeer() {
    if (hasNextPeer()) {
      String nextPeer;

      Iterator<String> it = setOfDiscoveredIPs.iterator();
      while (it.hasNext()) {
        nextPeer = it.next();

        if (nextPeer.equals(ipAddressOfThisPeerInstance)) {
          break;
        }
      }

      if (it.hasNext()) {
        return it.next();
      } else {
        return setOfDiscoveredIPs.iterator().next();
      }
    } else {
      throw new NoSuchElementException("Error: Peer list is empty");
    }
  }

  /**
   * Add a single ip address to the list of discovered ip addresses
   *
   * @param newIPAddress New ip address string
   */
  public synchronized void add(final String newIPAddress) {
    addIPResource(newIPAddress);
    setChanged();
    notifyObservers(getArray());
  }

    /**
     * Remove the next peer in the list of all known ip addresses
     */
    synchronized void removeNextPeer() {
    removeIPResource(getNextPeer());
    setChanged();
    notifyObservers(getArray());
  }

  /**
   * Get all discovered ip addresses as array of strings
   *
   * @return Array of strings with all currently discovered ip addresses
   */
  public synchronized String[] getArray() {
    return arrayOfIPResources();
  }

  private Long toNumeric(final String ip) {
      long number;
      try (Scanner sc = new Scanner(ip).useDelimiter("\\.")) {
          number =
                  (sc.nextLong() << 24) + (sc.nextLong() << 16) + (sc.nextLong() << 8) + (sc.nextLong());
    }
    return number;
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
    while (!currentIP.equals(ipAddressOfThisPeerInstance)) {

      it.remove();

      if (!it.hasNext()) {
        it = setOfDiscoveredIPs.iterator();
      }

      currentIP = it.next();
    }
  }

  private void addIPResource(String newIPAddress) {
    setOfDiscoveredIPs.add(newIPAddress);
  }

  private void removeIPResource(String removeIPAddress) {
    setOfDiscoveredIPs.remove(removeIPAddress);
  }

  private int sizeOfIPResource() {
    return setOfDiscoveredIPs.size() - 1;
  }

  private String[] arrayOfIPResources() {
    List<String> allKnownPeers = new LinkedList<>();

    for (String s : setOfDiscoveredIPs) {
      if (!s.equals(ipAddressOfThisPeerInstance)) {
        allKnownPeers.add(s);
      }
    }

    return allKnownPeers.toArray(new String[0]);
  }
}
