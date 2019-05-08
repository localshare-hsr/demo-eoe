package ch.hsr.epj.localshare.demo.network.transfer.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

public class ChannelHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    // Channel implementation will follow up in another iteration
  }
}
