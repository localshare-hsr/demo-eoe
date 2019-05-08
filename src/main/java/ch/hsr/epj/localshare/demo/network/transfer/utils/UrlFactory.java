package ch.hsr.epj.localshare.demo.network.transfer.utils;

import ch.hsr.epj.localshare.demo.logic.networkcontroller.Publisher;
import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;
import java.io.File;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlFactory {

  private static final String PROTCOL = "http://";
  private static final String PORT = "8640";

  private UrlFactory() {
  }

  public static URL generateNotifyUrl(final InetAddress ipAddress) throws MalformedURLException {
    return new URL(PROTCOL + ipAddress.getHostAddress() + ":" + PORT + "/notify/");
  }

  public static URL generateMetaDataUrl(final Publisher publisher) throws MalformedURLException {
    String ip = getIPAddress(publisher);
    return new URL(PROTCOL + ip + ":" + PORT + "/share/" + publisher.getFileUri() + "/");
  }

  public static String generateDownloadableURL(final String randomToken, File file) {
    return PROTCOL + IPAddressUtil.getLocalIPAddress().getHostAddress() + ":" + PORT + "/share/"
        + randomToken + "/"
        + file.getName();
  }

  private static String getIPAddress(final Publisher publisher) {
    return publisher.getPeerAddress().getHostAddress();
  }
}
