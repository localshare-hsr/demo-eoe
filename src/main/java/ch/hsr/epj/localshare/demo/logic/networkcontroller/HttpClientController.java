package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import ch.hsr.epj.localshare.demo.logic.environment.ConfigManager;
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


}
