package ch.hsr.epj.localshare.demo.persistence;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class KeyContainerTest {

  private static final String TEST_USER = "UnitTest";
  private static final String TEST_USER_CERTIFICATE = "-----BEGIN CERTIFICATE-----\n"
      + "MIIBJzCBzaADAgECAgRasvw1MAoGCCqGSM49BAMCMBMxETAPBgNVBAMMCFVuaXRU\n"
      + "ZXN0MB4XDTE5MDUwNzA4Mzk1MloXDTE5MDUwNzE3MTIzNFowEzERMA8GA1UEAwwI\n"
      + "VW5pdFRlc3QwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAARf8XYvYnW7OzWpoGWd\n"
      + "CwBbR599dgkJH5tD5SzXsAuF3GVeLr0bU5cUObL3OK3jbwEh723R1obgtDuAtfdS\n"
      + "Xy/low8wDTALBgNVHQ8EBAMCAbYwCgYIKoZIzj0EAwIDSQAwRgIhAI41s/QJU78U\n"
      + "O+thVyVlV4EgmhUhrS66FUyRPadkkrZoAiEAoihhG9Vi2TjOI/RV6/4cIyCSQMRi\n"
      + "dJ5ryhpVDZDUtLo=\n"
      + "-----END CERTIFICATE-----";
  private static final byte[] TEST_USER_PRIVATE_KEY = new byte[]{0x30, (byte) 0x81, (byte) 0x87,
      (byte) 0x02, (byte) 0x01, (byte) 0x00, (byte) 0x30, (byte) 0x13, (byte) 0x06, (byte) 0x07,
      (byte) 0x2A, (byte) 0x86, (byte) 0x48, (byte) 0xCE, (byte) 0x3D, (byte) 0x02, (byte) 0x01,
      (byte) 0x06, (byte) 0x08, (byte) 0x2A, (byte) 0x86, (byte) 0x48, (byte) 0xCE, (byte) 0x3D,
      (byte) 0x03, (byte) 0x01, (byte) 0x07, (byte) 0x04, (byte) 0x6D, (byte) 0x30, (byte) 0x6B,
      (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0x04, (byte) 0x20, (byte) 0xFE, (byte) 0xC5,
      (byte) 0xEB, (byte) 0x8C, (byte) 0x40, (byte) 0x43, (byte) 0xCF, (byte) 0x83, (byte) 0xBA,
      (byte) 0x7F, (byte) 0x8C, (byte) 0xE5, (byte) 0xE4, (byte) 0xC6, (byte) 0x74, (byte) 0x73,
      (byte) 0x7B, (byte) 0x92, (byte) 0xF5, (byte) 0x8F, (byte) 0xF5, (byte) 0x5F, (byte) 0x5B,
      (byte) 0x90, (byte) 0x49, (byte) 0x83, (byte) 0xB7, (byte) 0x0A, (byte) 0xE0, (byte) 0xDB,
      (byte) 0x8E, (byte) 0x01, (byte) 0xA1, (byte) 0x44, (byte) 0x03, (byte) 0x42, (byte) 0x00,
      (byte) 0x04, (byte) 0x5F, (byte) 0xF1, (byte) 0x76, (byte) 0x2F, (byte) 0x62, (byte) 0x75,
      (byte) 0xBB, (byte) 0x3B, (byte) 0x35, (byte) 0xA9, (byte) 0xA0, (byte) 0x65, (byte) 0x9D,
      (byte) 0x0B, (byte) 0x00, (byte) 0x5B, (byte) 0x47, (byte) 0x9F, (byte) 0x7D, (byte) 0x76,
      (byte) 0x09, (byte) 0x09, (byte) 0x1F, (byte) 0x9B, (byte) 0x43, (byte) 0xE5, (byte) 0x2C,
      (byte) 0xD7, (byte) 0xB0, (byte) 0x0B, (byte) 0x85, (byte) 0xDC, (byte) 0x65, (byte) 0x5E,
      (byte) 0x2E, (byte) 0xBD, (byte) 0x1B, (byte) 0x53, (byte) 0x97, (byte) 0x14, (byte) 0x39,
      (byte) 0xB2, (byte) 0xF7, (byte) 0x38, (byte) 0xAD, (byte) 0xE3, (byte) 0x6F, (byte) 0x01,
      (byte) 0x21, (byte) 0xEF, (byte) 0x6D, (byte) 0xD1, (byte) 0xD6, (byte) 0x86, (byte) 0xE0,
      (byte) 0xB4, (byte) 0x3B, (byte) 0x80, (byte) 0xB5, (byte) 0xF7, (byte) 0x52, (byte) 0x5F,
      (byte) 0x2F, (byte) 0xE5};
  private static final String STORE = "keystore.p12";
  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();
  private X509Certificate testCertificate;
  private PrivateKey testPrivateKey;

  @Before
  public void prepareCertificate()
      throws CertificateException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
    CertificateFactory factory = CertificateFactory.getInstance("X.509");
    testCertificate = (X509Certificate) factory
        .generateCertificate(new ByteArrayInputStream(TEST_USER_CERTIFICATE.getBytes()));

    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

    KeyFactory keyFactory = KeyFactory.getInstance("ECDH", "BC");
    testPrivateKey = keyFactory
        .generatePrivate(new PKCS8EncodedKeySpec(TEST_USER_PRIVATE_KEY));
  }


  @Test(expected = NullPointerException.class)
  public void testConstructorMissingPath() {
    new KeyContainer(null, null);
  }

  @Test
  public void testConstructorMissingFileExtension() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, "keystore");
    assertTrue(keyContainer.getFilePath().getName().endsWith(STORE));
  }

  @Test
  public void testConstructorMissingWrongExtension() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, "keystore.txt");
    assertTrue(keyContainer.getFilePath().getName().endsWith("keystore.txt.p12"));
  }

  @Test
  public void testConstructorPFXExtension() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, "keystore.pfx");
    assertTrue(keyContainer.getFilePath().getName().endsWith("keystore.pfx"));
  }

  @Test
  public void testConstructorP12Extension() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    assertTrue(keyContainer.getFilePath().getName().endsWith(STORE));
  }

  @Test
  public void testAddPeer() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    keyContainer.addPeerCertificate(TEST_USER, testCertificate);
    assertTrue(keyContainer.addPeerCertificate(TEST_USER, testCertificate));
  }

  @Test
  public void testAddMultiplePeer() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    keyContainer.addPeerCertificate("User1", testCertificate);
    keyContainer.addPeerCertificate("User2", testCertificate);
    keyContainer.addPeerCertificate("User3", testCertificate);
    Map<String, X509Certificate> allPeers = keyContainer.allPeerCertificates();
    assertFalse(allPeers.isEmpty());
  }

  @Test
  public void testCountAllMultiplePeer() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    keyContainer.addPeerCertificate("User1", testCertificate);
    keyContainer.addPeerCertificate("User2", testCertificate);
    keyContainer.addPeerCertificate("User3", testCertificate);
    Map<String, X509Certificate> allPeers = keyContainer.allPeerCertificates();
    assertEquals(3, allPeers.size());
  }

  @Test
  public void testAllMultiplePeerGetFirst() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    keyContainer.addPeerCertificate("User1", testCertificate);
    keyContainer.addPeerCertificate("User2", testCertificate);
    keyContainer.addPeerCertificate("User3", testCertificate);
    Map<String, X509Certificate> allPeers = keyContainer.allPeerCertificates();
    assertTrue(allPeers.containsKey("user1"));
  }

  @Test
  public void testRemovePeer() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    keyContainer.addPeerCertificate(TEST_USER, testCertificate);
    keyContainer.removeEntry(TEST_USER);
    boolean result = keyContainer.existsCertificate(TEST_USER);
    assertFalse(result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPeerNullFriendlyName() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    keyContainer.addPeerCertificate(null, testCertificate);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPeerMissingFriendlyName() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    keyContainer.addPeerCertificate("", testCertificate);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPeerMissingCertificate() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    keyContainer.addPeerCertificate(TEST_USER, null);
  }

  @Test
  public void testExistsCertificateInEmptyKeyStore() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    assertFalse(keyContainer.existsCertificate(TEST_USER));
  }

  @Test
  public void testExistsCertificate() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    keyContainer.addPeerCertificate(TEST_USER, testCertificate);
    assertTrue(keyContainer.existsCertificate(TEST_USER));
  }

  @Test
  public void testGetPeerCertificate() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    keyContainer.addPeerCertificate(TEST_USER, testCertificate);
    X509Certificate result = keyContainer.getCertificate(TEST_USER);
    assertEquals(testCertificate, result);
  }

  @Test
  public void testAddKeingMaterial() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    boolean result = keyContainer.addUserKeyingMaterial(TEST_USER, testCertificate, testPrivateKey);
    assertTrue(result);
  }

  @Test
  public void testExistsKeingMaterial() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    keyContainer.addUserKeyingMaterial(TEST_USER, testCertificate, testPrivateKey);
    boolean result = keyContainer.existsUserKeyingMaterial(TEST_USER);
    assertTrue(result);
  }

  @Test
  public void testGetKeingMaterialCertificate() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    keyContainer.addUserKeyingMaterial(TEST_USER, testCertificate, testPrivateKey);
    X509Certificate result = keyContainer.getCertificate(TEST_USER);
    assertEquals(testCertificate, result);
  }

  @Test
  public void testGetKeingMaterialPrivateKey() throws IOException {
    File store = tempFolder.newFolder("config");
    KeyContainer keyContainer = new KeyContainer(store, STORE);
    keyContainer.addUserKeyingMaterial(TEST_USER, testCertificate, testPrivateKey);
    PrivateKey result = keyContainer.getUserPrivateKey(TEST_USER);
    assertArrayEquals(testPrivateKey.getEncoded(), result.getEncoded());
  }

}