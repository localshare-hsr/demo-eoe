package ch.hsr.epj.localshare.demo.network.transfer.client;

import ch.hsr.epj.localshare.demo.gui.presentation.Download;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.HttpClientController;
import ch.hsr.epj.localshare.demo.network.transfer.utils.MetaParser;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

public class HTTPMetaDownloader extends Observable implements Runnable {

  private static final Logger logger = Logger.getLogger(HTTPMetaDownloader.class.getName());

  private static final int BUFFER_SIZE = 1024;
  private static final int EOF = -1;
  private URL metaUrl;
  private List<Download> downloadList;

  public HTTPMetaDownloader(URL url, List<Download> downloadList,
      HttpClientController httpClientController) {
    this.metaUrl = url;
    this.downloadList = downloadList;
    addObserver(httpClientController);
  }

  @Override
  public void run() {
    try {
      startDownload();
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Could not run download", e);
    }
  }

  private void startDownload() throws IOException {
    HttpsURLConnection connection = (HttpsURLConnection) metaUrl.openConnection();
    logger.log(Level.INFO, "starting metadata download: {0}", metaUrl);
    connection.setRequestMethod("GET");
    connection.setDoOutput(true);
    connection.setRequestProperty("Connection", "close");

    InputStream inputStream = connection.getInputStream();
    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

    int status = connection.getResponseCode();
    if (status == 200) {
      File jsonFile = new File("index.json");

      FileOutputStream outputStream = new FileOutputStream(jsonFile);
      try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
        byte[] buffer = new byte[BUFFER_SIZE];
        int byteRead;
        while ((byteRead = bufferedInputStream.read(buffer)) != EOF) {
          try {
            bufferedOutputStream.write(buffer, 0, byteRead);
            bufferedOutputStream.flush();

          } catch (IOException e) {
            logger.log(Level.WARNING, "Problem with output stream occurred", e);
            break;
          }
        }
        downloadList = MetaParser.parse(jsonFile);
        setChanged();
        notifyObservers(downloadList);
      }
    } else {
      logger.log(Level.SEVERE, "HTTP status code not 200 OK but {0}", status);
    }
    bufferedInputStream.close();
    connection.disconnect();
  }
}

