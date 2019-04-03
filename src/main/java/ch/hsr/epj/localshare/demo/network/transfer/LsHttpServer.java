package ch.hsr.epj.localshare.demo.network.transfer;

import static ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil.getLocalIPAddress;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CountDownLatch;

public class LsHttpServer implements Runnable {

    @Override
    public void run() {
        try {
            InetAddress myIP = getLocalIPAddress();
            InetSocketAddress myAddress = new InetSocketAddress(myIP, 8640);
            server = HttpServer.create(myAddress, 0);
            server.createContext("/info", new InfoHandler());
            server.createContext("/get", new GetHandler());
            server.setExecutor(null); // create a default executor
            server.start();
            startedLatch.countDown();
            System.out.println("HTTP server started");
        } catch (IOException e) {
            e.printStackTrace();
        }

      // let the thread run in the background
      try {
        synchronized (this) {
          while (true) {
            this.wait();
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    public void serveFileInChannel(String filePath, String channelName) {
        // TODO: some checks would be nice
        File f = new File(filePath);
        String channel = "/" + channelName + "/" + f.getName();

        fileName = filePath;

        // wait until the server is started
        try {
            startedLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("starting context: " + fileName + " in channel " + channel);
        server.createContext(channel, new DynamicHandler());
    }

    public void serveFileInPrivate(String filePath) {
        try {
            // generate SHA256 of file and use that as channel name
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            File f = new File(filePath);
            byte[] digest = md.digest(Files.readAllBytes(f.toPath()));
            String sha256 = DatatypeConverter.printHexBinary(digest).toLowerCase();
            System.out.println(sha256);
            serveFileInChannel(filePath, sha256);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

  static class InfoHandler implements HttpHandler {

    public void handle(HttpExchange t) throws IOException {
      String response = "Use /get to download a PDF";
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      System.out.println("/info was requested");
      os.close();
    }
  }

  static class GetHandler implements HttpHandler {

    public void handle(HttpExchange t) throws IOException {

      // add the required response header for a PDF file
      Headers h = t.getResponseHeaders();
      h.add("Content-Type", "application/pdf");

      // serve the PDF
      File file = new File("test.pdf");
      byte[] bytearray = new byte[(int) file.length()];
      FileInputStream fis = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(fis);
      bis.read(bytearray, 0, bytearray.length);

      // send the response
      t.sendResponseHeaders(200, file.length());
      OutputStream os = t.getResponseBody();
      os.write(bytearray, 0, bytearray.length);
      System.out.println("/get was requested");
      os.close();
    }
  }

  static class DynamicHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            // add the required response header for a JPG file
            Headers h = t.getResponseHeaders();

            // TODO: may not be an image in the future, probably use the BYTE type
            h.add("Content-Type", "image/jpeg");

            // serve the file
            File file = new File(fileName);
            byte[] bytearray = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(bytearray, 0, bytearray.length);

            // send the response
            t.sendResponseHeaders(200, file.length());
            OutputStream os = t.getResponseBody();
            os.write(bytearray, 0, bytearray.length);
            System.out.println(fileName + " was requested");
            os.close();
        }
  }

  // TODO: please find a better way to do this
  private static String fileName;
  private HttpServer server;
  private CountDownLatch startedLatch = new CountDownLatch(1);
}