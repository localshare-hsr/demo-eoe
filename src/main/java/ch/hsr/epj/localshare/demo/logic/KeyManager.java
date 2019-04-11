package ch.hsr.epj.localshare.demo.logic;

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class KeyManager {

  private static final String MY_ALIAS = "ME";
  private static final String PATH = "keys/";
  private static final String KEYSTORE_FILE = "/keystore.p12";
  private static final char[] EMPTY_PASSWORD = "foobar".toCharArray();

  private File filePath;
  private KeyStore keyStore;

  public KeyManager() {
    createFileStructure();
    filePath = new File(PATH).getAbsoluteFile();
    this.keyStore = loadKeyStore();
  }

  public void generateNewCertificate(String friendlyName) {
    if (friendlyName.equals("") || friendlyName.length() > 16) {
      throw new IllegalArgumentException("Error: Friendly name invalid");
    }

    if (!hasAlias(MY_ALIAS)) {
      System.out.println("Nein, einen Alias ME gibt es noch nicht. Mach weiter");
      try {
        createAndSafeNewCertificate(friendlyName);
      } catch (KeyStoreException
          | InvalidKeyException
          | NoSuchProviderException
          | NoSuchAlgorithmException
          | IOException
          | CertificateException
          | SignatureException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Stopp, es existiert bereits ein Zertifikat mit ME im KeyStore");
    }
  }

  private KeyStore loadKeyStore() {
    KeyStore keyStore = null;
    try {
      keyStore = KeyStore.getInstance("pkcs12");

      try {
        keyStore.load(new FileInputStream(filePath + KEYSTORE_FILE), EMPTY_PASSWORD);
      } catch (FileNotFoundException e) {
        keyStore = createNewKeyStore();
        safeKeyStore(keyStore);
      } catch (IOException | CertificateException | NoSuchAlgorithmException e) {
        e.printStackTrace();
      }

    } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
      e.printStackTrace();
    }

    return keyStore;
  }

  private void createFileStructure() {
    filePath = new File(PATH);
    if (!filePath.exists()) {
      filePath.mkdir();
    }
  }

  private KeyStore createNewKeyStore()
      throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
    KeyStore keyStore = KeyStore.getInstance("pkcs12");
    keyStore.load(null, EMPTY_PASSWORD);
    return keyStore;
  }

  private void safeKeyStore(KeyStore keyStore) {
    FileOutputStream fos;
    try {
      fos = new FileOutputStream(filePath + KEYSTORE_FILE);
      keyStore.store(fos, EMPTY_PASSWORD);
    } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
      e.printStackTrace();
    }
  }

  private void createAndSafeNewCertificate(final String friendlyName)
      throws KeyStoreException, InvalidKeyException, NoSuchProviderException,
      NoSuchAlgorithmException, IOException, CertificateException, SignatureException {
    CertAndKeyGen certGen = new CertAndKeyGen("EC", "SHA256withECDSA", null);
    certGen.generate(256);

    long validSecs = (long) 356 * 24 * 3600;
    X509Certificate cert =
        certGen.getSelfCertificate(new X500Name("CN=" + friendlyName), validSecs);

    X509Certificate[] chain = new X509Certificate[1];
    chain[0] = cert;
    keyStore.setKeyEntry(MY_ALIAS, certGen.getPrivateKey(), EMPTY_PASSWORD, chain);
    safeKeyStore(keyStore);
  }

  private String readFingerprint(final String alias)
      throws KeyStoreException, CertificateEncodingException, NoSuchAlgorithmException {
    X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);

    return DatatypeConverter.printHexBinary(
        MessageDigest.getInstance("SHA-256").digest(certificate.getEncoded()))
        .toLowerCase();
  }

  private boolean hasAlias(final String alias) {
    boolean suchACertificateExists;

    File pkcs12File = new File(PATH + KEYSTORE_FILE);
    if (pkcs12File.exists()) {
      try {
        X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
        suchACertificateExists = certificate != null;
      } catch (KeyStoreException e) {
        suchACertificateExists = false;
      }
    } else {
      suchACertificateExists = false;
    }

    return suchACertificateExists;
  }

  public String getFingerprint() {
    String fingerprint = "";

    if (hasAlias(MY_ALIAS)) {
      try {
        fingerprint = readFingerprint(MY_ALIAS);
      } catch (KeyStoreException | CertificateEncodingException | NoSuchAlgorithmException e) {
        e.printStackTrace();
      }
    }

    return fingerprint;
  }

  public X509Certificate getCertificate(final String alias) throws KeyStoreException {
    if (hasAlias(alias)) {
      return (X509Certificate) keyStore.getCertificate(alias);
    } else {
      throw new IllegalArgumentException("Error: Alias does not exist");
    }
  }

  public Key getPrivateKey()
      throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
    if (hasAlias(MY_ALIAS)) {
      return keyStore.getKey(MY_ALIAS, EMPTY_PASSWORD);
    } else {
      throw new IllegalArgumentException("Error: Alias does not exist");
    }
  }
}
