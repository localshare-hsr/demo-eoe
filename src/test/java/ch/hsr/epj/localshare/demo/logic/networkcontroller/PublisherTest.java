package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import static org.junit.Assert.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.Test;

public class PublisherTest {

  @Test
  public void testIPSettingAndGetting() throws UnknownHostException {
    InetAddress ip = InetAddress.getByName("127.0.0.1");
    String fileURI = "/";
    Publisher publisher = new Publisher(ip, fileURI);
    assertEquals(ip, publisher.getPeerAddress());
  }

  @Test
  public void testFileURISettingAndGetting() throws UnknownHostException {
    InetAddress ip = InetAddress.getByName("127.0.0.1");
    String fileURI = "/";
    Publisher publisher = new Publisher(ip, fileURI);
    assertEquals(fileURI, publisher.getFileUri());
  }

}