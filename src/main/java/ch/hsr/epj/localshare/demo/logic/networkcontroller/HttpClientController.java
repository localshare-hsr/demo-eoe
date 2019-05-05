package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import ch.hsr.epj.localshare.demo.gui.presentation.Download;
import ch.hsr.epj.localshare.demo.logic.Transfer;
import ch.hsr.epj.localshare.demo.logic.environment.ConfigManager;
import ch.hsr.epj.localshare.demo.network.transfer.client.DownloadManager;
import ch.hsr.epj.localshare.demo.network.transfer.client.HTTPDownloader;
import ch.hsr.epj.localshare.demo.network.transfer.client.HTTPMetaDownloader;
import ch.hsr.epj.localshare.demo.network.transfer.client.HTTPNotifier;
import ch.hsr.epj.localshare.demo.network.transfer.utils.UrlFactory;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
  private HTTPNotifier httpNotifier;
  private ObservableList<Download> downloadObservableList;

  public HttpClientController(ObservableList<Download> downloadObservableList) {
    this.downloadManager = new DownloadManager();
    httpNotifier = new HTTPNotifier();
    this.downloadObservableList = downloadObservableList;
  }

  public void downloadFileFromPeer(FileTransfer transfer) throws FileNotFoundException {
    String path = transfer.getPath().getFile();
    String filename = path.substring(path.lastIndexOf('/') + 1);
    File file = new File(ConfigManager.getInstance().getDownloadPath() + filename);

    OutputStream outputStream = new FileOutputStream(file);

    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
    HTTPDownloader httpDownloader = new HTTPDownloader(transfer.getPath(), bufferedOutputStream,
        transfer);
    transfer.setHttpDownloader(httpDownloader);
    downloadManager.addDownload(httpDownloader);
  }

  void sendNotification(Transfer transfer) {
    try {
      httpNotifier.sendNotification(transfer);
    } catch (IOException e) {
      logger.log(Level.WARNING, "IO problem sending notification", e);
    }
  }

  void getMetadataFromPeer(Transfer transfer) {
    List<Download> downloadList = new ArrayList<>();
    URL url = null;
    try {
      url = UrlFactory.generateMetaDataUrl(transfer);
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
