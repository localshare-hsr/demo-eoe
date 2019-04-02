package ch.hsr.epj.localshare.demo.network.discovery.searcher;

public class Statemachine {

    Statemachine state;
    static String[] listOfIps;

    public Statemachine() {
    }

    public void addListOfIPsToScan(String[] listOfIps) {
        Statemachine.listOfIps = listOfIps;
    }

    public void run() {
        state = new SearchingState();
    }
}
