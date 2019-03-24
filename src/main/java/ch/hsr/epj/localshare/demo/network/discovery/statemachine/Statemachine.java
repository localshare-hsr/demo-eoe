package ch.hsr.epj.localshare.demo.network.discovery.statemachine;

public class Statemachine implements Runnable {

  Statemachine state;
  static String[] listOfIps;

  public Statemachine() {
  }

  public void addListOfIPsToScan(String[] listOfIps) {
    Statemachine.listOfIps = listOfIps;
  }

  @Override
  public void run() {
    state = new SearchingState();
  }
}
