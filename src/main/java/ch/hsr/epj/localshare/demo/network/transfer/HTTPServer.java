package ch.hsr.epj.localshare.demo.network.transfer;

import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Executors;

public class HTTPServer {

  private static final String CONTEXT_SHARE = "/share";
  private static final String CONTEXT_CHANNEL = "/channel";
  private static final String CONTEXT_NOTIFY = "/notify";
  private HttpServer httpServer;


  public HTTPServer() {
    System.out.println("Start HTTP Web Server on Port 8640");
    InetAddress myIPAddress = IPAddressUtil.getLocalIPAddress();
    InetSocketAddress socket = new InetSocketAddress(myIPAddress, 8640);
    try {
      this.httpServer = HttpServer.create(socket, 0);
    } catch (IOException e) {
      e.printStackTrace();
    }
    httpServer.createContext(CONTEXT_NOTIFY, new NotifyHandler());
    httpServer.setExecutor(Executors.newFixedThreadPool(10));
    httpServer.start();
  }

  public void stopHTTPServer() {
    httpServer.stop(0);
  }

  public HTTPProgress createNewShare(String path, List<File> files) {
    if (path == null || files == null) {
      throw new IllegalArgumentException("Error: Path and files must not be null");
    }

    HTTPProgress httpProgress = new HTTPProgress();
    httpServer.createContext(CONTEXT_SHARE + "/" + path, new ShareHandler(files, httpProgress));
    return httpProgress;
  }

  private void deleteShare(String path) {
    if (path == null) {
      throw new IllegalArgumentException("Error: Path mustno be null");
    }

    httpServer.removeContext(CONTEXT_SHARE + "/" + path);
  }


}
