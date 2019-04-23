package ch.hsr.epj.localshare.demo.logic;

import ch.hsr.epj.localshare.demo.network.transfer.client.HTTPDownloader;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class HttpClientController {

  private String downldoadPath;

  public HttpClientController(String downloadPath) {
    this.downldoadPath = downloadPath;
  }

  public void downloadFileFromPeer(Transfer transfer) throws FileNotFoundException {
    File file = new File(downldoadPath + "test.pdf");

    OutputStream outputStream = new FileOutputStream(file);

    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
    HTTPDownloader downloader = new HTTPDownloader(transfer.getURL(), bufferedOutputStream);


  }




}
