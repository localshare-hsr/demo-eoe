package ch.hsr.epj.localshare.demo.network.transfer.server;

import ch.hsr.epj.localshare.demo.logic.environment.User;
import ch.hsr.epj.localshare.demo.network.transfer.HTTPProgress;
import ch.hsr.epj.localshare.demo.network.transfer.utils.MetaParser;
import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShareHandler implements HttpHandler {

  private static final Logger logger = Logger.getLogger(ShareHandler.class.getName());

  private static final int BUFFER_SIZE = 1024;
  private static final int EOF = -1;
  private static final int NO_RESPONSE_BODY = -1;

  private static final int HTTP_OK = 200;
  private static final int HTTP_NOT_FOUND = 404;

  private static final String HEADER_CONTENT_TYPE = "Content-Type";

  private static final String INDEX_FOLDER = "/";

  private List<File> filePool;
  private HTTPProgress httpProgress;
  private List<DownloadFile> downloadableFiles;
  private String path;

  ShareHandler(List<File> filePool, HTTPProgress httpProgress, String path) {
    this.filePool = filePool;
    this.httpProgress = httpProgress;
    downloadableFiles = generateFileContext(filePool);
    this.path = path;
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {

    Headers headers = httpExchange.getResponseHeaders();
    try {
      File fileToSend = requestDispatcher(httpExchange.getRequestURI());
      try {
        headers.add(HEADER_CONTENT_TYPE, getMIMEType(fileToSend));
      } catch (IOException e) {
        logger.log(Level.WARNING, "Unable to set MIME Type for file", e);
      }
      deliverFileToClient(httpExchange, fileToSend);
    } catch (FileNotFoundException e) {
      httpExchange.sendResponseHeaders(HTTP_NOT_FOUND, NO_RESPONSE_BODY);
      logger.log(Level.WARNING, "File does not exist", e);
    } finally {
      httpExchange.close();
    }
  }

  private List<DownloadFile> generateFileContext(final List<File> filePool) {
    List<DownloadFile> list = new LinkedList<>();
    for (File f : filePool) {
      list.add(generateDownloadFile(f));
    }
    return list;
  }

  private DownloadFile generateDownloadFile(final File file) {
    String ownerFriendlyName = User.getInstance().getFriendlyName();
    String url =
        "http://" + IPAddressUtil.getLocalIPAddress() + ":/share/" + path + "/" + file.getName();
    return new DownloadFile(ownerFriendlyName, file.getName(), file.length(), url);
  }

  private File requestDispatcher(URI uri) throws IOException {
    String uriPath = uri.getPath();

    if (uriPath.endsWith(INDEX_FOLDER)) {
      return MetaParser.generateMeta(downloadableFiles);
    } else {
      return getFileFromPool(uriPath);
    }
  }

  private File getFileFromPool(String fileName) throws FileNotFoundException {
    for (File f : filePool) {
      if (fileName.endsWith(f.getName())) {
        return f;
      }
    }

    throw new FileNotFoundException("Error: File " + fileName + " does not exist");
  }

  private String getMIMEType(File file) throws IOException {
    return Files.probeContentType(file.toPath());
  }

  private void deliverFileToClient(HttpExchange httpExchange, File file) throws IOException {
    InputStream inputStream = new FileInputStream(file);
    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

    OutputStream outputStream = httpExchange.getResponseBody();
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

    httpExchange.setStreams(bufferedInputStream, bufferedOutputStream);
    long totalLength = file.length();
    httpExchange.sendResponseHeaders(HTTP_OK, totalLength);
    httpProgress.setTotalByteLength(totalLength);

    byte[] buffer = new byte[BUFFER_SIZE];
    int byteRead;
    while ((byteRead = bufferedInputStream.read(buffer)) != EOF) {
      try {
        bufferedOutputStream.write(buffer, 0, byteRead);
        bufferedOutputStream.flush();
        httpProgress.addBytesToCurrent(byteRead);
      } catch (IOException e) {
        logger.log(Level.WARNING, "Problem with output stream occurred", e);
        httpProgress.reset();
        break;
      }
    }
    bufferedInputStream.close();
    bufferedOutputStream.close();
    httpExchange.close();
  }
}
