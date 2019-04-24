package ch.hsr.epj.localshare.demo.network.transfer.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManager {

  private ExecutorService executorService = Executors.newFixedThreadPool(10);

  public void addDownload(HTTPDownloader httpDownloader) {
    executorService.execute(httpDownloader);
  }

}
