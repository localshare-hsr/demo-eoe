package ch.hsr.epj.localshare.demo.network.transfer.server;

import ch.hsr.epj.localshare.demo.network.transfer.HTTPProgress;
import ch.hsr.epj.localshare.demo.network.transfer.utils.MetaParser;
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

public class ShareHandler implements HttpHandler {

  private static final int BUFFER_SIZE = 1024;
  private static final int EOF = -1;
  private static final int EMPTY_RESPONSE_BODY = 0;

  private static final int HTTP_OK = 200;
  private static final int HTTP_NOT_FOUND = 404;

  private static final String HEADER_CONTENT_TYPE = "Content-Type";

  private static final String INDEX_FOLDER = "/";
  private static final String INDEX_FILE = "index.json";

  private List<File> filePool;
  private HTTPProgress httpProgress;
  private List<DownloadFile> downloadableFiles;

  ShareHandler(List<File> filePool, HTTPProgress httpProgress) {
    this.filePool = filePool;
    this.httpProgress = httpProgress;
    downloadableFiles = generateFileContext(filePool);
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {

    Headers headers = httpExchange.getResponseHeaders();
    try {
      File fileToSend = requestDispatcher(httpExchange.getRequestURI());
      try {
        headers.add(HEADER_CONTENT_TYPE, getMIMEType(fileToSend));
      } catch (IOException e) {
        System.err.println("Error: Unable to set MIME Type for file " + fileToSend.getName());
      }
      deliverFileToClient(httpExchange, fileToSend);
    } catch (FileNotFoundException e) {
      httpExchange.sendResponseHeaders(HTTP_NOT_FOUND, EMPTY_RESPONSE_BODY);
      e.printStackTrace();
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
    return new DownloadFile("ownerTODO", file.getName(), file.length(), "urlTODO");
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
      System.out.println("f = " + f.getName());
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
        System.err.println("Error: Problem with output stream occurred");
        httpProgress.reset();
        break;
      }
    }
    bufferedInputStream.close();
    bufferedOutputStream.close();
    httpExchange.close();
  }
}
