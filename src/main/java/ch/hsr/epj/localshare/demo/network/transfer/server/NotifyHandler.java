package ch.hsr.epj.localshare.demo.network.transfer.server;

import ch.hsr.epj.localshare.demo.logic.Transfer;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.InetAddress;

public class NotifyHandler implements HttpHandler {

  private HTTPServer httpServer;

  NotifyHandler(HTTPServer httpServer) {
    this.httpServer = httpServer;
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    String method = httpExchange.getRequestMethod();
    if (!method.equals("PUT")) {
      return;
    }
    InetAddress peerAddress = httpExchange.getRemoteAddress().getAddress();
    Headers headers = httpExchange.getRequestHeaders();
    String fileUri = headers.getFirst("X-Resource");
    Transfer transfer = new Transfer(peerAddress, fileUri);
    System.out.println("Received PUT:");
    System.out.println("X-Resource is: " + fileUri);
    System.out.println("IP-Address is: " + peerAddress);
    httpServer.receivedNotification(transfer);
  }
}
