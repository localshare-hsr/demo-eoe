package ch.hsr.epj.localshare.demo.network.transfer.server;

import ch.hsr.epj.localshare.demo.logic.networkcontroller.HttpServerController;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.Publisher;
import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

public class HTTPServer {

  private static final Logger logger = Logger.getLogger(HTTPServer.class.getName());

  private static final int PORT = 8640;
  private static final String CONTEXT_SHARE = "/share";
  private static final String CONTEXT_CHANNEL = "/channel";
  private static final String CONTEXT_NOTIFY = "/notify";
  private HttpsServer webServer;
  private HttpServerController httpServerController;

  public HTTPServer(HttpServerController httpServerController, KeyStore keystore) {
    this.httpServerController = httpServerController;
    InetAddress myIPAddress = IPAddressUtil.getLocalIPAddress();
    InetSocketAddress socket = new InetSocketAddress(myIPAddress, 8640);
    logger
        .log(Level.FINE, "Start HTTPS Web Server on " + myIPAddress.getHostAddress() + ":" + PORT);
    try {
      this.webServer = HttpsServer.create(socket, 0);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Could not create HTTPS server instance", e);
    }
    try {
      SSLContext sslContext = SSLContext.getInstance("TLS");
      KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
      String encoded = "" + 0x66 + 0x6F + 0x6F + 0x62 + 0x61 + 0x72;
      keyManagerFactory.init(keystore, encoded.toCharArray());
      sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
      HttpsConfigurator httpsConfigurator = new HttpsConfigurator(sslContext);
      webServer.setHttpsConfigurator(httpsConfigurator);
    } catch (NoSuchAlgorithmException | KeyManagementException | UnrecoverableKeyException | KeyStoreException e) {
      logger.log(Level.SEVERE, "TLS algorithm or user certificate not available", e);
    }
    assert webServer != null;
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

  synchronized void receivedNotification(Publisher publisher) {
    httpServerController.receivedNotification(publisher);
  }
}
