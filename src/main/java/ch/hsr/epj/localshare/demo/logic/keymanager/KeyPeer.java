package ch.hsr.epj.localshare.demo.logic.keymanager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

public class KeyPeer {

  private static final String CN_KEY = "CN=";
  private static final char DELIMITER = ',';
  private static final String HASH_ALGORITHM = "SHA-256";
  private static final Logger logger = Logger.getLogger(KeyPeer.class.getName());

  private String friendlyName;
  private String fingerprint;
  private X509Certificate certificate;

  KeyPeer(final X509Certificate certificate) {
    checkInputCertificate(certificate);
    this.friendlyName = parseFriendlyName(certificate);
    this.fingerprint = parseFingerprint(certificate);
    this.certificate = certificate;
  }

  public String getFriendlyName() {
    return friendlyName;
  }

  String getFingerprintNoFormat() {
    return fingerprint;
  }

  String getFingerprintSpaces() {
    if (!fingerprint.equals("")) {
      return addSpace(fingerprint);
    } else {
      return fingerprint;
    }
  }

  String getFingerprintColons() {
    if (!fingerprint.equals("")) {
      return addColon(fingerprint);
    } else {
      return fingerprint;
    }
  }

  X509Certificate getCertificate() {
    return certificate;
  }

  private String parseFriendlyName(final X509Certificate certificate) {
    checkInputCertificate(certificate);
    return getFriendlyName(certificate);
  }

  private String getFriendlyName(final X509Certificate certificate) {
    checkInputCertificate(certificate);
    Principal principal = certificate.getSubjectDN();
    return getCN(principal);
  }

  private String getCN(final Principal principal) {
    if (principal == null) {
      throw new IllegalArgumentException("Principal must not be null");
    }
    int start = principal.getName().indexOf(CN_KEY) + CN_KEY.length();
    int end = principal.getName().indexOf(DELIMITER);
    if (end == -1) {
      end = principal.getName().length();
    }
    return principal.getName().substring(start, end).trim();
  }

  private String parseFingerprint(final X509Certificate certificate) {
    return getFingerprintNoFormat(certificate);
  }

  private String getFingerprintNoFormat(final X509Certificate certificate) {
    String fp = "";

    try {
      byte[] certificateDER = certificate.getEncoded();
      fp =
          DatatypeConverter.printHexBinary(
              MessageDigest.getInstance(HASH_ALGORITHM).digest(certificateDER))
              .toUpperCase();
    } catch (NoSuchAlgorithmException | CertificateEncodingException e) {
      logger.log(Level.SEVERE, "Encoding issue");
    }

    return fp;
  }

  private String addSpace(final String string) {
    return formatString(string, " ", 4);
  }

  private String addColon(final String string) {
    return formatString(string, ":", 2);
  }

  private String formatString(final String string, final String delimiter, final int blockLength) {
    StringBuilder sb = new StringBuilder();
    int begin = 0;
    for (int i = blockLength; i < string.length(); i += blockLength) {
      sb.append(string, begin, i).append(delimiter);
      begin = i;
    }
    sb.append(string, begin, begin + blockLength);
    return sb.toString().trim();
  }

  private void checkInputCertificate(final X509Certificate certificate) {
    if (certificate == null) {
      throw new IllegalArgumentException("Certificate must not be null");
    }
  }
}
