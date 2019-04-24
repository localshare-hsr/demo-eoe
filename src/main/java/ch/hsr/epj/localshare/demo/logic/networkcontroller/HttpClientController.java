package ch.hsr.epj.localshare.demo.logic.networkcontroller;

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

public class HttpClientController {

  private DownloadManager downloadManager;
  private HTTPNotifier httpNotifier;

  public HttpClientController() {
    this.downloadManager = new DownloadManager();
    httpNotifier = new HTTPNotifier();
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
    // stub
  }
}
