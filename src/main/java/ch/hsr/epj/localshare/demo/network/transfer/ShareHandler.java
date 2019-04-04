package ch.hsr.epj.localshare.demo.network.transfer;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;

public class ShareHandler implements HttpHandler {

  private List<File> files;

  public ShareHandler(List<File> files) {
    this.files = files;
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {

    Headers headers = httpExchange.getResponseHeaders();
    try {
      File fileToSend = requestDispatcher(httpExchange.getRequestURI());
      headers.add("Content-Type", getMIMEType(fileToSend));
      fileDelivery(httpExchange, fileToSend);
    } catch (IllegalArgumentException e) {
      httpExchange.sendResponseHeaders(404, 0);
    } finally {
      httpExchange.close();
    }
  }

  private File requestDispatcher(URI uri) {
    String uriPath = uri.getPath();

    if (uriPath.equals("/")) {
      // serve index aka meta file
    }

    for (File f : files) {
      if (uriPath.endsWith(f.getName())) {
        return f;
      }
    }

    throw new IllegalArgumentException("Error: No URI to file match found");
  }

  private String getMIMEType(File file) {
    String MIMEType = "";
    try {
      MIMEType = Files.probeContentType(file.toPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return MIMEType;
  }

  private void fileDelivery(HttpExchange httpExchange, File file) throws IOException {
    InputStream inputStream = new FileInputStream(file);
    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

    OutputStream outputStream = httpExchange.getResponseBody();
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

    httpExchange.setStreams(bufferedInputStream, bufferedOutputStream);
    httpExchange.sendResponseHeaders(200, file.length());

    byte[] buffer = new byte[1024];
    while (bufferedInputStream.read(buffer) != -1) {
      bufferedOutputStream.write(buffer);
    }
    bufferedOutputStream.flush();
  }
}
