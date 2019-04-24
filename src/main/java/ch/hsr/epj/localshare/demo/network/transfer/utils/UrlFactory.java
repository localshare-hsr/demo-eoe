package ch.hsr.epj.localshare.demo.network.transfer.utils;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlFactory {

  private UrlFactory() {
  }

  public static URL generateUrl(InetAddress ipAddress) throws MalformedURLException {
    return new URL("http://" + ipAddress.toString() + "/");
  }

  public static URL generateNotifyUrl(InetAddress ipAddress) throws MalformedURLException {
    return new URL("http://" + ipAddress.getHostAddress() + ":8640/notify/");
  }
}
