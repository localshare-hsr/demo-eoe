package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import ch.hsr.epj.localshare.demo.gui.presentation.Download;
import ch.hsr.epj.localshare.demo.logic.Transfer;
import ch.hsr.epj.localshare.demo.logic.environment.ConfigManager;
import ch.hsr.epj.localshare.demo.network.transfer.client.DownloadManager;
import ch.hsr.epj.localshare.demo.network.transfer.client.HTTPDownloader;
import ch.hsr.epj.localshare.demo.network.transfer.client.HTTPNotifier;
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
import javafx.collections.ObservableList;

public class HttpClientController {

  private DownloadManager downloadManager;
  private HTTPNotifier httpNotifier;
  private ObservableList<Download> downloadObservableList;

  public HttpClientController(ObservableList<Download> downloadObservableList) {
    this.downloadManager = new DownloadManager();
    httpNotifier = new HTTPNotifier();
    this.downloadObservableList = downloadObservableList;
  }

  public void downloadFileFromPeer(FileTransfer transfer) throws FileNotFoundException {
    File file = new File(ConfigManager.getInstance().getDownloadPath() + "test.pdf");

    OutputStream outputStream = new FileOutputStream(file);

    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
    downloadManager.addDownload(
        new HTTPDownloader(transfer.getPath(), bufferedOutputStream, transfer.getProgress()));
  }

  void sendNotification(Transfer transfer) {
    System.out.println("private path for share is: " + transfer.getFileUri());
    System.out.println("peer IP address is: " + transfer.getPeerAddress());
    try {
      httpNotifier.sendNotification(transfer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void getMetadataFromPeer(Transfer transfer) {
    // TODO Transfer what?
    //fake some more ;)
    System.out.println("arrived in getMetadata function");
    List<Download> downloadList = new ArrayList<>();
    URL url = null;
    try {
      url = new URL("http://www.42.tf/");
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    System.out.println("url faked");
    Download fakeDownload = new Download("marco", 4096, "owl.png", url);
    Download fakeDownload2 = new Download("nobody", 1, "bad.txt", url);
    downloadList.add(fakeDownload);
    downloadList.add(fakeDownload2);
    System.out.println("fake list done");

    for (Download d : downloadList) {
      downloadObservableList.add(d);
    }
    System.out.println("fake list added");

    //URL url = null;
    /*
    try {
      url = UrlFactory.generateMetaDataUrl(transfer);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    */
    //downloadManager.addDownload(new HTTPMetaDownloader(url, downloadList));
  }
}
