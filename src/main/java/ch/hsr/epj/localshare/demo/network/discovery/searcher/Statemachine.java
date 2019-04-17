package ch.hsr.epj.localshare.demo.network.discovery.searcher;

class Statemachine {

  Statemachine state;
  static String[] listOfIps;

  static void addListOfIPsToScan(String[] listOfIps) {
    Statemachine.listOfIps = listOfIps;
  }

  void run() {
    state = new SearchingState();
  }
}
