package ch.hsr.epj.localshare.demo.logic;

import ch.hsr.epj.localshare.demo.network.transfer.client.DownloadManager;
import ch.hsr.epj.localshare.demo.network.transfer.client.HTTPDownloader;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class HttpClientController {

  private DownloadManager downloadManager;

  public HttpClientController() {
    this.downloadManager = new DownloadManager();
  }

  public void downloadFileFromPeer(FileTransfer transfer) throws FileNotFoundException {
    File file = new File(ConfigManager.getInstance().getDownloadPath() + "test.pdf");

    OutputStream outputStream = new FileOutputStream(file);

    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
    downloadManager.addDownload(
        new HTTPDownloader(transfer.getPath(), bufferedOutputStream, transfer.getProgress()));
  }

  void sendNotification(Transfer transfer) {
    // TODO: pass the transfer on to the HTTPClient, which needs to send a PUT
    System.out.println("private path for share is: " + transfer.getFileUri());
    System.out.println("peer IP address is: " + transfer.getPeerAddress());
  }

  public void getMetadataFromPeer(Transfer transfer) {
    // stub
  }
}
