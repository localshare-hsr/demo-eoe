package ch.hsr.epj.localshare.demo.network.transfer.utils;

import ch.hsr.epj.localshare.demo.logic.Transfer;
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

  public static URL generateMetaDataUrl(final Transfer transfer) throws MalformedURLException {
    String ip = getIPAddress(transfer);
    return new URL(PROTCOL + ip + ":" + PORT + "/share/" + transfer.getFileUri() + "/");
  }

  public static String generateDownloadableURL(final String randomToken, File file) {
    return PROTCOL + IPAddressUtil.getLocalIPAddress().getHostAddress() + ":" + PORT + "/share/"
        + randomToken + "/"
        + file.getName();
  }

  private static String getIPAddress(final Transfer transfer) {
    return transfer.getPeerAddress().getHostAddress();
  }
}
