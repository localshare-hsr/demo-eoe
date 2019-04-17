package ch.hsr.epj.localshare.demo.network.discovery.searcher;

class Statemachine {

  Statemachine state;
  String[] listOfIps;

  void addListOfIPsToScan(String[] listOfIps) {
    this.listOfIps = listOfIps;
  }

  void run() {
    state = new SearchingState();
  }
}
