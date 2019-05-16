package ch.hsr.epj.localshare.demo.network.transfer.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

public class SelfSignedSSL implements X509TrustManager {

  @Override
  public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
      throws CertificateException {
    // accept all client certificates
    if (x509Certificates.length == 0) {
      throw new CertificateException("Client certificate length is zero");
    }
  }

  @Override
  public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
      throws CertificateException {
    // accept all server certificates
    if (x509Certificates.length == 0) {
      throw new CertificateException("Server certificate length is zero");
    }
  }

  @Override
  public X509Certificate[] getAcceptedIssuers() {
    return new X509Certificate[0];
  }


}
