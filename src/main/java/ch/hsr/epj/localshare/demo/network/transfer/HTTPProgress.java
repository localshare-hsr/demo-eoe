package ch.hsr.epj.localshare.demo.network.transfer;

import java.util.Observable;

public class HTTPProgress extends Observable {

  private long totalByteLength;
  private long currentBytes;

  public void setTotalByteLength(long totalByteLength) {
    this.totalByteLength = totalByteLength;
  }

  public void addBytesToCurrent(long addBytes) {
    this.currentBytes += addBytes;
    setChanged();
    notifyObservers(getProgressInPercent());
  }

  private int getProgressInPercent() {
    return (int) (currentBytes * 100 / totalByteLength);
  }

  public void reset() {
    this.currentBytes = 0;
  }
}
