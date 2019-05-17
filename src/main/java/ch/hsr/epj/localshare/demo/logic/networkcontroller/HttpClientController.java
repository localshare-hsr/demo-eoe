package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import ch.hsr.epj.localshare.demo.gui.presentation.Download;
import ch.hsr.epj.localshare.demo.gui.presentation.Peer;
import ch.hsr.epj.localshare.demo.logic.environment.ConfigManager;
import ch.hsr.epj.localshare.demo.network.transfer.client.DownloadManager;
import ch.hsr.epj.localshare.demo.network.transfer.client.HTTPDownloader;
import ch.hsr.epj.localshare.demo.network.transfer.client.HTTPMetaDownloader;
import ch.hsr.epj.localshare.demo.network.transfer.client.HTTPNotifier;
import ch.hsr.epj.localshare.demo.network.transfer.client.HTTPPeerChecker;
import ch.hsr.epj.localshare.demo.network.transfer.utils.UrlFactory;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;

public class HttpClientController implements Observer {

  private static final Logger logger = Logger.getLogger(HttpClientController.class.getName());

  private DownloadManager downloadManager;
  private ObservableList<Download> downloadObservableList;
  private ObservableList<Peer> peerObservableList;

  public HttpClientController(ObservableList<Download> downloadObservableList,
      ObservableList<Peer> peerObservableList) {
    this.downloadManager = new DownloadManager();
    this.downloadObservableList = downloadObservableList;
    this.peerObservableList = peerObservableList;
  }

  public void downloadFileFromPeer(FileTransfer transfer) throws FileNotFoundException {
    String path = transfer.getPath().getFile();
    String uriname = path.substring(path.lastIndexOf('/') + 1);
    String filename = null;
    try {
      filename = URLDecoder.decode(uriname.replaceAll("%20", "\\+"), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      logger.log(Level.SEVERE, "UTF-8 encoding not available on this system", e);
    }
    File file = new File(ConfigManager.getInstance().getDownloadPath() + filename);

    OutputStream outputStream = new FileOutputStream(file);

    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
    HTTPDownloader httpDownloader = new HTTPDownloader(transfer.getPath(), bufferedOutputStream,
        transfer);
    transfer.setHttpDownloader(httpDownloader);
    downloadManager.addDownload(httpDownloader);
  }

  public ObservableList<Download> getDownloadObservableList() {
    return downloadObservableList;
  }

  void sendNotification(Publisher publisher) {
    HTTPNotifier httpNotifier = new HTTPNotifier(publisher);
    downloadManager.addNotifyTask(httpNotifier);
  }

  public void checkPeerAvailability(Peer peer) throws IOException {
    HTTPPeerChecker httpPeerChecker = new HTTPPeerChecker(peer, peerObservableList);
    downloadManager.addAvailabilityTask(httpPeerChecker);
  }

  void getMetadataFromPeer(Publisher publisher) {
    List<Download> downloadList = new ArrayList<>();
    URL url = null;
    try {
      url = UrlFactory.generateMetaDataUrl(publisher);
    } catch (MalformedURLException e) {
      logger.log(Level.WARNING, "URL is invalid", e);
    }
    HTTPMetaDownloader httpMetaDownloader = new HTTPMetaDownloader(url, downloadList, this);
    downloadManager.addMetaDownload(httpMetaDownloader);
  }


  @Override
  public void update(Observable o, Object arg) {
    Platform.runLater(
        () -> {
          List<Download> downloadList = (List<Download>) arg;
          downloadObservableList.addAll(downloadList);
        }
    );
  }
}
