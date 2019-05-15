package ch.hsr.epj.localshare.demo.network.transfer.server;

import ch.hsr.epj.localshare.demo.logic.networkcontroller.Publisher;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.InetAddress;

public class NotifyHandler implements HttpHandler {

  private static final int HTTP_OK = 200;
  private static final int HTTP_METHOD_NOT_ALLOWED = 405;
  private static final int NO_RESPONSE_BODY = -1;
  private HTTPServer httpServer;

  NotifyHandler(HTTPServer httpServer) {
    this.httpServer = httpServer;
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    String method = httpExchange.getRequestMethod();
    if (method.equals("PUT") || method.equals("GET")) {
      InetAddress peerAddress = httpExchange.getRemoteAddress().getAddress();
      Headers headers = httpExchange.getRequestHeaders();
      String fileUri = headers.getFirst("X-Resource");
      if (fileUri != null) {
        Publisher publisher = new Publisher(peerAddress, fileUri);
        httpServer.receivedNotification(publisher);
      }
      httpExchange.sendResponseHeaders(HTTP_OK, NO_RESPONSE_BODY);
    } else {
      httpExchange.sendResponseHeaders(HTTP_METHOD_NOT_ALLOWED, NO_RESPONSE_BODY);
    }
    httpExchange.close();
  }
}
