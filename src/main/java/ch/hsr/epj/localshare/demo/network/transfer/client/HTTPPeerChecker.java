package ch.hsr.epj.localshare.demo.network.transfer.client;

import ch.hsr.epj.localshare.demo.gui.presentation.Peer;
import ch.hsr.epj.localshare.demo.logic.keymanager.KeyPeer;
import ch.hsr.epj.localshare.demo.network.transfer.utils.UrlFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javax.net.ssl.HttpsURLConnection;

public class HTTPPeerChecker implements Runnable {

  private static final Logger logger = Logger.getLogger(HTTPPeerChecker.class.getName());

  private Peer peer;
  private URL url;
  private ObservableList<Peer> observableList;

  public HTTPPeerChecker(Peer peer, ObservableList<Peer> observableList) {
    this.peer = peer;
    this.observableList = observableList;
    try {
      InetAddress ip = InetAddress.getByName(peer.getIP());
      url = UrlFactory.generateNotifyUrl(ip);
    } catch (MalformedURLException e) {
      logger.log(Level.WARNING, "URL malcormed");
    } catch (UnknownHostException e) {
      logger.log(Level.WARNING, "Invalid peer ip address");
    }
  }

  @Override
  public void run() {
    try {
      startDownload();
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Could not run download", e);
    }
  }

  private void startDownload() throws IOException {
    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setRequestProperty("Connection", "close");
    connection.connect();
    KeyPeer keyPeer = new KeyPeer((X509Certificate) connection.getServerCertificates()[0]);
    update(keyPeer);
    peer.setFriendlyName(keyPeer.getFriendlyName());
    peer.setFingerPrint(keyPeer.getFingerprintSpaces());
    connection.disconnect();
  }

  private void update(KeyPeer keyPeer) {
    Platform.runLater(
        () -> {
          Peer newPeer = new Peer(peer.getIP(), keyPeer.getFriendlyName(), "",
              keyPeer.getFingerprintSpaces());

          if (!observableList.contains(newPeer)) {
            observableList.add(newPeer);
          }
        });
  }

}
