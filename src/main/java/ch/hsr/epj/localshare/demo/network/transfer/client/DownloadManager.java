package ch.hsr.epj.localshare.demo.network.transfer.client;

import ch.hsr.epj.localshare.demo.network.transfer.utils.SelfSignedSSL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class DownloadManager {

  private static final Logger logger = Logger.getLogger(DownloadManager.class.getName());

  public DownloadManager() {
    // Create all-trusting host name verifier
    HostnameVerifier allHostsValid = (hostname, session) -> true;
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
  }

  private ExecutorService executorService = Executors.newFixedThreadPool(10);

  public void addDownload(HTTPDownloader httpDownloader) {
    executorService.execute(httpDownloader);
  }

  public void addMetaDownload(HTTPMetaDownloader httpMetaDownloader) {
    executorService.execute(httpMetaDownloader);
  }

  public void addNotifyTask(HTTPNotifier httpNotifier) {
    executorService.execute(httpNotifier);
  }

  public void addAvailabilityTask(HTTPPeerChecker httpPeerChecker) {
    executorService.execute(httpPeerChecker);
  }


}
