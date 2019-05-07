package ch.hsr.epj.localshare.demo.logic.keymanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import org.junit.Before;
import org.junit.Test;

public class KeyPeerTest {

  private static final String CERTIFICATE = "-----BEGIN CERTIFICATE-----\n"
      + "MIIBIDCBx6ADAgECAgSg4L6+MAoGCCqGSM49BAMCMBAxDjAMBgNVBAMMBUVsdmlz\n"
      + "MB4XDTE5MDQxOTIwMjY1MloXDTE5MDQyMDA0NTkzNFowEDEOMAwGA1UEAwwFRWx2\n"
      + "aXMwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAAQmMAgD7dhM7qLFJRkfaH44uE4I\n"
      + "mPRqGUI+DZk6KosZ4K0zr59fKgXRftqI45KKOOHo+Bp0K/h+rIkGJLavCr8wow8w\n"
      + "DTALBgNVHQ8EBAMCAbYwCgYIKoZIzj0EAwIDSAAwRQIgQSdoDTb/MpROqL6puxlm\n"
      + "nt7HjYY4807OBQRPJSMUoLoCIQD1enHxeStCp6K6GULXCsMlod4RC5mnbmm5ESa3\n"
      + "4HB3IQ==\n"
      + "-----END CERTIFICATE-----";

  private X509Certificate testCertificate;

  @Before
  public void prepareCertificate() throws CertificateException {
    CertificateFactory factory = CertificateFactory.getInstance("X.509");
    testCertificate = (X509Certificate) factory
        .generateCertificate(new ByteArrayInputStream(CERTIFICATE.getBytes()));
  }

  @Test
  public void testSimpleUserFriendlyName() {
    KeyPeer keyPeer = new KeyPeer();
    assertEquals("", keyPeer.getFriendlyName());
  }

  @Test
  public void testSimpleUserFingerprint() {
    KeyPeer keyPeer = new KeyPeer();
    assertEquals("", keyPeer.getFingerprintNoFormat());
  }

  @Test
  public void testSimpleUserFingerprintSpaces() {
    KeyPeer keyPeer = new KeyPeer();
    assertEquals("", keyPeer.getFingerprintSpaces());
  }

  @Test
  public void testSimpleUserFingerprintColons() {
    KeyPeer keyPeer = new KeyPeer();
    assertEquals("", keyPeer.getFingerprintColons());
  }

  @Test
  public void testSimpleUserCertificate() {
    KeyPeer keyPeer = new KeyPeer();
    assertNull(keyPeer.getCertificate());
  }

  @Test
  public void testCertificateUserFriendlyName() {
    KeyPeer keyPeer = new KeyPeer(testCertificate);
    assertEquals("Elvis", keyPeer.getFriendlyName());
  }

  @Test
  public void testCertificateUserFingerprintNoFormat() {
    KeyPeer keyPeer = new KeyPeer(testCertificate);
    assertEquals("CE9B8D3BFA07BA6887DDC873393E7EB30A279CF4815699E91CE0C99B88CEAFFB",
        keyPeer.getFingerprintNoFormat());
  }

  @Test
  public void testCertificateUserFingerprintSpaces() {
    KeyPeer keyPeer = new KeyPeer(testCertificate);
    assertEquals("CE9B 8D3B FA07 BA68 87DD C873 393E 7EB3 0A27 9CF4 8156 99E9 1CE0 C99B 88CE AFFB",
        keyPeer.getFingerprintSpaces());
  }

  @Test
  public void testCertificateUserFingerprintColons() {
    KeyPeer keyPeer = new KeyPeer(testCertificate);
    assertEquals(
        "CE:9B:8D:3B:FA:07:BA:68:87:DD:C8:73:39:3E:7E:B3:0A:27:9C:F4:81:56:99:E9:1C:E0:C9:9B:88:CE:AF:FB",
        keyPeer.getFingerprintColons());
  }

  @Test
  public void testCertificateUserCertificate() {
    KeyPeer keyPeer = new KeyPeer(testCertificate);
    assertEquals(testCertificate, keyPeer.getCertificate());
  }
}