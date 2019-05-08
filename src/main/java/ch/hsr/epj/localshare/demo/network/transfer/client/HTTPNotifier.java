package ch.hsr.epj.localshare.demo.network.transfer.client;

import ch.hsr.epj.localshare.demo.logic.Transfer;
import ch.hsr.epj.localshare.demo.network.transfer.utils.SelfSignedSSL;
import ch.hsr.epj.localshare.demo.network.transfer.utils.UrlFactory;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

public class HTTPNotifier {

  private static final Logger logger = Logger.getLogger(HTTPNotifier.class.getName());

  public void sendNotification(Transfer transfer) throws IOException {
    // Create all-trusting host name verifier
    HostnameVerifier allHostsValid = new HostnameVerifier() {
      public boolean verify(String hostname, SSLSession session) {
        return true;
      }
    };
    try {
      SSLContext sslContext = SSLContext.getInstance("TLS");
      TrustManager[] gullible = new TrustManager[]{new SelfSignedSSL()};
      sslContext.init(null, gullible, null);
      HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
      // Install the all-trusting host verifier
      HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      logger.log(Level.SEVERE, "TLS algorithm not available", e);
    }
    URL url = UrlFactory.generateNotifyUrl(transfer.getPeerAddress());
    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
    connection.setRequestMethod("PUT");
    connection.setRequestProperty("Connection", "close");
    connection.setRequestProperty("X-Resource", transfer.getFileUri());
    connection.connect();
    int status = connection.getResponseCode();
    if (status == 200) {
      logger.log(Level.INFO, "Send notification and received HTTP 200 OK");
    } else {
      logger.log(Level.WARNING, "Send notification but received {0}", status);
    }
    connection.disconnect();
  }
}
