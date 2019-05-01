package ch.hsr.epj.localshare.demo.network.transfer.server;

import ch.hsr.epj.localshare.demo.logic.Transfer;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.HttpServerController;
import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HTTPServer {

  private static final Logger logger = Logger.getLogger(HTTPServer.class.getName());

  private static final int PORT = 8640;
  private static final String CONTEXT_SHARE = "/share";
  private static final String CONTEXT_CHANNEL = "/channel";
  private static final String CONTEXT_NOTIFY = "/notify";
  private HttpServer webServer;
  private HttpServerController httpServerController;

  public HTTPServer(HttpServerController httpServerController) {
    this.httpServerController = httpServerController;
    InetAddress myIPAddress = IPAddressUtil.getLocalIPAddress();
    InetSocketAddress socket = new InetSocketAddress(myIPAddress, 8640);
    logger.log(Level.FINE, "Start HTTP Web Server on " + myIPAddress.getHostAddress() + ":" + PORT);
    try {
      this.webServer = HttpServer.create(socket, 0);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Could not create HTTP server instance", e);
    }
    webServer.createContext(CONTEXT_NOTIFY, new NotifyHandler(this));
    webServer.setExecutor(Executors.newFixedThreadPool(10));
    webServer.start();
  }

  public void stopHTTPServer() {
    webServer.stop(0);
  }

  public void createNewShare(String path, List<File> files) {
    if (path == null || files == null) {
      throw new IllegalArgumentException("Error: Path and files must not be null");
    }

    webServer
        .createContext(CONTEXT_SHARE + "/" + path, new ShareHandler(files, path));
  }

  synchronized void receivedNotification(Transfer transfer) {
    httpServerController.receivedNotification(transfer);
  }
}
