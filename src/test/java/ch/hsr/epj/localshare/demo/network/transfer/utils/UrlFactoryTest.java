package ch.hsr.epj.localshare.demo.network.transfer.utils;

import static ch.hsr.epj.localshare.demo.network.transfer.utils.UrlFactory.generateMetaDataUrl;
import static ch.hsr.epj.localshare.demo.network.transfer.utils.UrlFactory.generateNotifyUrl;
import static org.junit.Assert.assertEquals;

import ch.hsr.epj.localshare.demo.logic.networkcontroller.Publisher;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import org.junit.Test;

public class UrlFactoryTest {

  @Test
  public void testGenerateNotifyUrl() throws UnknownHostException, MalformedURLException {
    InetAddress ipAddress = InetAddress.getByName("192.168.1.1");
    URL url = generateNotifyUrl(ipAddress);
    assertEquals("https://192.168.1.1:8640/notify/", url.toString());
  }

  @Test
  public void testGenerateMetaDataUrl() throws UnknownHostException, MalformedURLException {
    InetAddress ipAddress = InetAddress.getByName("192.168.1.1");
    Publisher publisher = new Publisher(ipAddress, "index");
    URL url = generateMetaDataUrl(publisher);
    assertEquals("https://192.168.1.1:8640/share/index/", url.toString());
  }

  @Test(expected = NullPointerException.class)
  public void testInvalidGenerateMetaDataUrl() throws MalformedURLException {
    Publisher publisher = new Publisher(null, "");
    generateMetaDataUrl(publisher);
  }
}