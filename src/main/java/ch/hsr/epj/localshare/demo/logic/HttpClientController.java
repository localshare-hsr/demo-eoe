package ch.hsr.epj.localshare.demo.logic;

public class HttpClientController {

  void sendNotification(Transfer transfer) {
    // TODO: pass the transfer on to the HTTPClient, which needs to send a PUT
    System.out.println("private path for share is: " + transfer.getFileUri());
    System.out.println("peer IP address is: " + transfer.getFileUri());
  }

  void getMetadataFromPeer(Transfer transfer) {
    // stub
  }
}
