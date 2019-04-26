package ch.hsr.epj.localshare.demo.network.transfer.server;

import ch.hsr.epj.localshare.demo.logic.Transfer;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.InetAddress;

public class NotifyHandler implements HttpHandler {

  private HTTPServer httpServer;
  private static final int HTTP_OK = 200;
  private static final int HTTP_METHOD_NOT_ALLOWED = 405;
  private static final int NO_RESPONSE_BODY = -1;

  NotifyHandler(HTTPServer httpServer) {
    this.httpServer = httpServer;
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    String method = httpExchange.getRequestMethod();
    if (!method.equals("PUT")) {
      httpExchange.sendResponseHeaders(HTTP_METHOD_NOT_ALLOWED, NO_RESPONSE_BODY);
    } else {
      InetAddress peerAddress = httpExchange.getRemoteAddress().getAddress();
      Headers headers = httpExchange.getRequestHeaders();
      String fileUri = headers.getFirst("X-Resource");
      Transfer transfer = new Transfer(peerAddress, fileUri);
      httpServer.receivedNotification(transfer);
      httpExchange.sendResponseHeaders(HTTP_OK, NO_RESPONSE_BODY);
    }
    httpExchange.close();
  }
}
