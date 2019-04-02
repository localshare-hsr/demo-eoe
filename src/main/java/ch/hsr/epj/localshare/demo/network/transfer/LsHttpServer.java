package ch.hsr.epj.localshare.demo.network.transfer;

import static ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil.getLocalIPAddress;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class LsHttpServer implements Runnable {

    @Override
    public void run() {
        try {
            InetAddress myIP = getLocalIPAddress();
            InetSocketAddress myAddress = new InetSocketAddress(myIP, 8640);
            HttpServer server = HttpServer.create(myAddress, 0);
            server.createContext("/info", new InfoHandler());
            server.createContext("/get", new GetHandler());
            server.setExecutor(null); // create a default executor
            server.start();
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
}