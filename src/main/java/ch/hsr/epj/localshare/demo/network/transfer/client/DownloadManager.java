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
    HostnameVerifier allHostsValid = (hostname, session) -> {
      // bit annoying
      //logger.log(Level.INFO, "Hostname {0}", hostname);
      return hostname != null;
    };
    try {
      SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
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

  public void addDownload(Runnable downloadTask) {
    executorService.execute(downloadTask);
  }

  public void addMetaDownload(Runnable downloadTask) {
    executorService.execute(downloadTask);
  }

  public void addNotifyTask(Runnable notifyTask) {
    executorService.execute(notifyTask);
  }

  public void addAvailabilityTask(Runnable checkTask) {
    executorService.execute(checkTask);
  }


}
