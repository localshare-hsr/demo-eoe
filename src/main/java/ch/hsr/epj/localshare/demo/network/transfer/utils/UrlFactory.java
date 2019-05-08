package ch.hsr.epj.localshare.demo.network.transfer.utils;

import ch.hsr.epj.localshare.demo.logic.Transfer;
import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class UrlFactory {

  private static final String PROTOCOL = "https://";
  private static final String PORT = "8640";

  private UrlFactory() {
  }

  public static URL generateNotifyUrl(final InetAddress ipAddress) throws MalformedURLException {
    return new URL(PROTOCOL + ipAddress.getHostAddress() + ":" + PORT + "/notify/");
  }

  public static URL generateMetaDataUrl(final Transfer transfer) throws MalformedURLException {
    String ip = getIPAddress(transfer);
    return new URL(PROTOCOL + ip + ":" + PORT + "/share/" + transfer.getFileUri() + "/");
  }

  public static String generateDownloadableURL(final String randomToken, File file)
      throws UnsupportedEncodingException {
    return PROTOCOL + IPAddressUtil.getLocalIPAddress().getHostAddress() + ":" + PORT + "/share/"
        + randomToken + "/"
        + URLEncoder.encode(file.getName(), "UTF-8");
  }

  private static String getIPAddress(final Transfer transfer) {
    return transfer.getPeerAddress().getHostAddress();
  }
}
