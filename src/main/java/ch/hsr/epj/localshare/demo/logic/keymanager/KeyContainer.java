package ch.hsr.epj.localshare.demo.logic.keymanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

class KeyContainer {
  private static final String PATH = "keys/";
  private static final String KEYSTORE_FILE = "/keystore.p12";
  private static final File FULL_PATH = new File(PATH + KEYSTORE_FILE);
  private static final String CONTAINER_FORMAT = "pkcs12";
  private static final Logger logger = Logger.getLogger(KeyContainer.class.getName());

  private KeyContainer() {}

  static KeyStore createNewKeyStoreOnDisk() {
    createFileStructure();

    KeyStore ks;
    try {
      ks = KeyStore.getInstance(CONTAINER_FORMAT);
      ks.load(null, getEncodedPassword());
    } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException e) {
      logger.log(Level.WARNING, "Could not create instance of KeyStore", e);
      logger.log(Level.WARNING, "Could not load instance of KeyStore", e);
      ks = null;
    }

    return ks;
  }

  static void safeKeyStore(final KeyStore keyStore) {
    try (FileOutputStream fos = new FileOutputStream(FULL_PATH)) {
      keyStore.store(fos, getEncodedPassword());

    } catch (IOException e) {
      logger.log(Level.WARNING, "File not found", e);
    } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
      logger.log(Level.WARNING, "KeyStore failure", e);
    }
  }

  static KeyStore loadKeyStoreFromDisk() {
    KeyStore ks;
    try (FileInputStream fis = new FileInputStream(FULL_PATH)) {
      ks = KeyStore.getInstance(CONTAINER_FORMAT);
      ks.load(fis, getEncodedPassword());
    } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
      logger.log(Level.WARNING, "KeyStore failure", e);
      logger.log(Level.WARNING, "Could not load keystore", e);
      ks = null;
    }

    return ks;
  }

  static void safeX509CertificateToKeyStore(
          final KeyStore keyStore, final X509Certificate certificate) {
    if (keyStore == null) {
      throw new IllegalArgumentException("KeyStore is not initialized");
    }

    String alias = getAlias(certificate);
    try {
      keyStore.setCertificateEntry(alias, certificate);
    } catch (KeyStoreException e) {
      logger.log(
          Level.WARNING,
          String.format("Could not safe new certificate %s", certificate.toString()));
    }
  }

  static void safePrivateKeyAndX509CertificateToKeyStore(final KeyStore keyStore, final X509Certificate certificate, final PrivateKey privateKey) {

    String alias = getAlias(certificate);

    X509Certificate[] chain = new X509Certificate[1];
    chain[0] = certificate;

    try {
      keyStore.setKeyEntry(alias, privateKey, getEncodedPassword(), chain);
    } catch (KeyStoreException e) {
      logger.log(
              Level.WARNING,
              String.format("Could not safe new certificate %s", certificate.toString()));
    }
  }

  private static void createFileStructure() {
    File filePath = new File(PATH);
    if (!filePath.exists()) {
      boolean isCreated = filePath.mkdir();
      if (isCreated) {
        logger.info("New folder created");
      }
    }
  }

  private static char[] getEncodedPassword() {
    String encoded = "" + 0x66 + 0x6F + 0x6F + 0x62 + 0x61 + 0x72;
    return encoded.toCharArray();
  }

  private static String getAlias(final X509Certificate certificate) {
    Principal principal = certificate.getSubjectDN();
    return principal.getName();
  }
}
