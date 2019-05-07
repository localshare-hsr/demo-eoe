package ch.hsr.epj.localshare.demo.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyContainer {

  private static final String CONTAINER_FORMAT = "pkcs12";
  private static final Logger logger = Logger.getLogger(KeyContainer.class.getName());
  private static final String KEYSTORE_FAILURE = "KeyStore failure";


  private KeyStore keyStore;
  private File filePath;

  public KeyContainer(File filePath, String fileName) {
    try {
      keyStore = KeyStore.getInstance(CONTAINER_FORMAT);
    } catch (KeyStoreException e) {
      logger.log(Level.WARNING, String.format("Could not create instance of %s", CONTAINER_FORMAT),
          e);
    }

    if (filePath.isDirectory()) {
      this.filePath = new File(filePath.getAbsolutePath(), fileName);
    }
    this.filePath = canoniseFileName(this.filePath);

    try {
      loadKeyStore();
    } catch (FileNotFoundException e) {
      createKeyStore();
    }
  }

  public boolean addUserKeyingMaterial(final String friendlyName, final X509Certificate certificate,
      final PrivateKey privateKey) {
    checkFriendlyName(friendlyName);
    checkCertificate(certificate);
    checkPrivateKey(privateKey);

    boolean success;
    try {
      keyStore
          .setKeyEntry(friendlyName, privateKey, getEncodedPassword(), createChain(certificate));
      updateKeyStoreAfterChange();
      success = true;
    } catch (KeyStoreException e) {
      logger.log(Level.WARNING, "Could not safe private key to key store", e);
      success = false;
    }

    writeToDisk();
    return success;
  }

  public PrivateKey getUserPrivateKey(final String friendlyName) {
    checkFriendlyName(friendlyName);

    PrivateKey key = null;
    try {
      key = (PrivateKey) keyStore.getKey(friendlyName, getEncodedPassword());
    } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
      logger.log(Level.WARNING, "Unable to load private key");
    }

    return key;
  }

  public boolean existsUserKeyingMaterial(final String friendlyName) {
    checkFriendlyName(friendlyName);

    boolean success;
    PrivateKey privateKey = getUserPrivateKey(friendlyName);
    if (privateKey == null) {
      success = false;
    } else {
      success = true;
    }

    return success;
  }


  public boolean existsCertificate(final String friendlyName) {
    checkFriendlyName(friendlyName);

    boolean success;
    try {

      X509Certificate cert = (X509Certificate) keyStore.getCertificate(friendlyName);
      if (cert != null) {
        success = true;
      } else {
        success = false;
      }
    } catch (KeyStoreException e) {
      success = false;
    }

    return success;
  }

  public boolean addPeerCertificate(final String friendlyName,
      final X509Certificate newPeerCertificate) {
    checkFriendlyName(friendlyName);
    checkCertificate(newPeerCertificate);

    boolean success;
    try {
      keyStore.setCertificateEntry(friendlyName, newPeerCertificate);
      updateKeyStoreAfterChange();

      success = true;
    } catch (KeyStoreException e) {
      success = false;
      logger.log(
          Level.WARNING,
          String.format("Could not safe new certificate %s", newPeerCertificate.toString()),
          e);
    }

    writeToDisk();
    return success;
  }

  public X509Certificate getCertificate(final String friendlyName) {
    checkFriendlyName(friendlyName);
    try {
      return (X509Certificate) keyStore.getCertificate(friendlyName);
    } catch (KeyStoreException e) {
      throw new NoSuchElementException(e.getMessage());
    }
  }

  public boolean removeEntry(final String friendlyName) {
    checkFriendlyName(friendlyName);

    try {
      keyStore.deleteEntry(friendlyName);
    } catch (KeyStoreException e) {
      throw new NoSuchElementException(e.getMessage());
    }

    writeToDisk();
    return true;
  }

  public Map<String, X509Certificate> allPeerCertificates() {
    Map<String, X509Certificate> allPeerCertificates = new HashMap<>();
    try {
      Enumeration<String> allFriendlyNames = keyStore.aliases();
      while (allFriendlyNames.hasMoreElements()) {
        String friendlyName = allFriendlyNames.nextElement();
        X509Certificate cert = (X509Certificate) keyStore.getCertificate(friendlyName);
        allPeerCertificates.put(friendlyName, cert);
      }
    } catch (KeyStoreException e) {
      logger.log(Level.WARNING, "Problem reading key store", e);
    }
    return allPeerCertificates;
  }

  public File getFilePath() {
    return filePath;
  }

  private File canoniseFileName(final File filePath) {
    if (filePath == null) {
      throw new IllegalArgumentException("File name must be present");
    }

    if (!filePath.getName().endsWith(".p12") && !filePath.getName().endsWith(".pfx")) {
      return new File(filePath + ".p12");
    } else {
      return filePath;
    }
  }

  private void createKeyStore() {
    try (FileOutputStream fos = new FileOutputStream(filePath.getAbsoluteFile())) {
      keyStore.load(null, null);
      keyStore.store(fos, getEncodedPassword());
    } catch (IOException e) {
      logger.log(Level.WARNING, "File not found", e);
    } catch (KeyStoreException e) {
      logger.log(Level.WARNING, KEYSTORE_FAILURE, e);
    } catch (NoSuchAlgorithmException | CertificateException e) {
      logger.log(Level.WARNING, "Could not create instance of KeyStore", e);
    }
  }

  private void loadKeyStore() throws FileNotFoundException {
    try (FileInputStream fis = new FileInputStream(filePath)) {
      keyStore.load(fis, getEncodedPassword());
    } catch (FileNotFoundException e) {
      throw e;
    } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
      logger.log(Level.WARNING, KEYSTORE_FAILURE, e);
    }
  }

  private void writeToDisk() {
    try (FileOutputStream fos = new FileOutputStream(filePath.getAbsoluteFile())) {
      keyStore.store(fos, getEncodedPassword());
    } catch (IOException e) {
      logger.log(Level.WARNING, "File not found", e);
    } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
      logger.log(Level.WARNING, KEYSTORE_FAILURE, e);
    }
  }

  private void updateKeyStoreAfterChange() {
    writeToDisk();
    try {
      loadKeyStore();
    } catch (FileNotFoundException e) {
      logger.log(Level.SEVERE, "Could not update Key store on file system");
    }
  }

  private void checkFriendlyName(final String friendlyName) {
    if (friendlyName == null || friendlyName.equals("")) {
      throw new IllegalArgumentException("Friendly name must be present");
    }
  }

  private void checkCertificate(final X509Certificate certificate) {
    if (certificate == null) {
      throw new IllegalArgumentException("Certificate must not be null");
    }
  }

  private void checkPrivateKey(final PrivateKey privateKey) {
    if (privateKey == null) {
      throw new IllegalArgumentException("Private key must not be null");
    }
  }

  private char[] getEncodedPassword() {
    String encoded = "" + 0x66 + 0x6F + 0x6F + 0x62 + 0x61 + 0x72;
    return encoded.toCharArray();
  }

  private X509Certificate[] createChain(final X509Certificate certificate) {
    X509Certificate[] pseudoChain = new X509Certificate[1];
    pseudoChain[0] = certificate;
    return pseudoChain;
  }

}
