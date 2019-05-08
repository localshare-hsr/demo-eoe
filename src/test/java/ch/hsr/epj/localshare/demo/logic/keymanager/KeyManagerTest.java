package ch.hsr.epj.localshare.demo.logic.keymanager;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class KeyManagerTest {

  private static final String TEST_USER = "UnitTest";
  private static final String TEST_USER_2 = "TestDummy";
  private static final String TEST_USER_CERTIFICATE = "-----BEGIN CERTIFICATE-----\n"
      + "MIIBJzCBzaADAgECAgRasvw1MAoGCCqGSM49BAMCMBMxETAPBgNVBAMMCFVuaXRU\n"
      + "ZXN0MB4XDTE5MDUwNzA4Mzk1MloXDTE5MDUwNzE3MTIzNFowEzERMA8GA1UEAwwI\n"
      + "VW5pdFRlc3QwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAARf8XYvYnW7OzWpoGWd\n"
      + "CwBbR599dgkJH5tD5SzXsAuF3GVeLr0bU5cUObL3OK3jbwEh723R1obgtDuAtfdS\n"
      + "Xy/low8wDTALBgNVHQ8EBAMCAbYwCgYIKoZIzj0EAwIDSQAwRgIhAI41s/QJU78U\n"
      + "O+thVyVlV4EgmhUhrS66FUyRPadkkrZoAiEAoihhG9Vi2TjOI/RV6/4cIyCSQMRi\n"
      + "dJ5ryhpVDZDUtLo=\n"
      + "-----END CERTIFICATE-----";
  private static final String TEST_USER_2_CERTIFICATE = "-----BEGIN CERTIFICATE-----\n"
      + "MIIBKDCBz6ADAgECAgT3zYWdMAoGCCqGSM49BAMCMBQxEjAQBgNVBAMMCVRlc3RE\n"
      + "dW1teTAeFw0xOTA1MDcyMDE4MDRaFw0xOTA1MDgwNDUwNDZaMBQxEjAQBgNVBAMM\n"
      + "CVRlc3REdW1teTBZMBMGByqGSM49AgEGCCqGSM49AwEHA0IABFtZIMtyH8Boc1tO\n"
      + "JiysLsWcH82uUmwlSXHc06vBPQSGmNhH7+Pr8w6aF+J2uFz/HqKeWGUNsJvSYeBe\n"
      + "NH25lSSjDzANMAsGA1UdDwQEAwIBtjAKBggqhkjOPQQDAgNIADBFAiEA65G4IKXD\n"
      + "6JMLgtz87RbdW+nEEU/EGngvvxb4dmHj+N8CIGSDz5vcuZtM6ImBFG7S+YeRSeJs\n"
      + "acptSYdw3zYv1WiD\n"
      + "-----END CERTIFICATE-----";
  private static final String STORE = "keystore.p12";

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();
  private X509Certificate testCertificate;
  private X509Certificate testCertificate2;

  @Before
  public void prepareCertificate()
      throws CertificateException {
    CertificateFactory factory = CertificateFactory.getInstance("X.509");
    testCertificate = (X509Certificate) factory
        .generateCertificate(new ByteArrayInputStream(TEST_USER_CERTIFICATE.getBytes()));
    testCertificate2 = (X509Certificate) factory
        .generateCertificate(new ByteArrayInputStream(TEST_USER_2_CERTIFICATE.getBytes()));

    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
  }

  @Test
  public void testDefaultUserFriendlyName() throws IOException {
    String store = tempFolder.newFolder("config").getAbsolutePath();
    KeyManager keyManager = new KeyManager(store, STORE, TEST_USER);
    String result = keyManager.getUser().getFriendlyName();
    assertEquals(TEST_USER, result);
  }

  @Test
  public void testAddTrustedPeerCertificate() throws IOException {
    String store = tempFolder.newFolder("config").getAbsolutePath();
    KeyManager keyManager = new KeyManager(store, STORE, "FooBar");
    boolean result = keyManager.addTrustedCertificate(testCertificate);
    assertTrue(result);
  }

  @Test
  public void testGetTrustedPeerCertificate() throws IOException, KeyStoreException {
    String store = tempFolder.newFolder("config").getAbsolutePath();
    KeyManager keyManager = new KeyManager(store, STORE, "FooBar");
    keyManager.addTrustedCertificate(testCertificate);
    X509Certificate result = keyManager.getTrustedCertificate(TEST_USER);
    assertEquals(testCertificate, result);
  }

  @Test
  public void testGetTrustedPeersList() throws IOException {
    String store = tempFolder.newFolder("config").getAbsolutePath();
    KeyManager keyManager = new KeyManager(store, STORE, "FooBar");
    keyManager.addTrustedCertificate(testCertificate);
    keyManager.addTrustedCertificate(testCertificate2);
    TestCase.assertEquals(2, keyManager.getPeerList().size());
  }

  @Test
  public void testGetTrustedPeersListContainsTestUser1() throws IOException {
    String store = tempFolder.newFolder("config").getAbsolutePath();
    KeyManager keyManager = new KeyManager(store, STORE, "FooBar");
    keyManager.addTrustedCertificate(testCertificate);
    keyManager.addTrustedCertificate(testCertificate2);

    assertEquals(TEST_USER, keyManager.getPeerList().get(0).getFriendlyName());
  }

  @Test
  public void testGetTrustedPeersListContainsTestUser2() throws IOException {
    String store = tempFolder.newFolder("config").getAbsolutePath();
    KeyManager keyManager = new KeyManager(store, STORE, "FooBar");
    keyManager.addTrustedCertificate(testCertificate);
    keyManager.addTrustedCertificate(testCertificate2);
    assertEquals(TEST_USER_2, keyManager.getPeerList().get(1).getFriendlyName());
  }

  @Test
  public void testKeyStoreObject() throws IOException {
    String store = tempFolder.newFolder("config").getAbsolutePath();
    KeyManager keyManager = new KeyManager(store, STORE, TEST_USER);
    KeyStore result = keyManager.getKeyStore();
    assertNotNull(result);
  }

  @Test
  public void testKeyStoreCertificate() throws IOException, KeyStoreException {
    String store = tempFolder.newFolder("config").getAbsolutePath();
    KeyManager keyManager = new KeyManager(store, STORE, TEST_USER);
    X509Certificate result = (X509Certificate) keyManager.getKeyStore().getCertificate(TEST_USER);
    assertNotNull(result);
  }

  @Test
  public void testKeyStorePrivateKey()
      throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
    String store = tempFolder.newFolder("config").getAbsolutePath();
    KeyManager keyManager = new KeyManager(store, STORE, TEST_USER);
    PrivateKey result = (PrivateKey) keyManager.getKeyStore()
        .getKey(TEST_USER, getEncodedPassword());
    assertNotNull(result);
  }

  private char[] getEncodedPassword() {
    String encoded = "" + 0x66 + 0x6F + 0x6F + 0x62 + 0x61 + 0x72;
    return encoded.toCharArray();
  }

}
