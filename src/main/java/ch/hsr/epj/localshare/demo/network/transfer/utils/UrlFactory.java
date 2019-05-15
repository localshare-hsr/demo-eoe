package ch.hsr.epj.localshare.demo.network.transfer.utils;

import ch.hsr.epj.localshare.demo.logic.networkcontroller.Publisher;
import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UrlFactory {

  private static final Logger logger = Logger.getLogger(UrlFactory.class.getName());

  private static final String PROTOCOL = "https://";
  private static final String PORT = "8640";

  UrlFactory() {
  }

  public static URL generateNotifyUrl(final InetAddress ipAddress) throws MalformedURLException {
    return new URL(PROTOCOL + ipAddress.getHostAddress() + ":" + PORT + "/notify/");
  }

  public static URL generateMetaDataUrl(final Publisher publisher) throws MalformedURLException {
    String ip = getIPAddress(publisher);
    return new URL(PROTOCOL + ip + ":" + PORT + "/share/" + publisher.getFileUri() + "/");
  }

  public static String generateDownloadableURL(final String randomToken, File file)
      throws UnsupportedEncodingException {
    String fileUri = URLEncoder.encode(file.getName(), "UTF-8")
        .replaceAll("\\+", "%20");
    logger.log(Level.INFO, "notifying for file: {0}", fileUri);
    return PROTOCOL + IPAddressUtil.getLocalIPAddress().getHostAddress() + ":" + PORT + "/share/"
        + randomToken + "/" + fileUri;
  }

  private static String getIPAddress(final Publisher publisher) {
    return publisher.getPeerAddress().getHostAddress();
  }
}
