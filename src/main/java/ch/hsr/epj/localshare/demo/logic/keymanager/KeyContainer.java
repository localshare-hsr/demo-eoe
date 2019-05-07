package ch.hsr.epj.localshare.demo.logic.keymanager;

import ch.hsr.epj.localshare.demo.logic.environment.ConfigManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

class KeyContainer {

  private static final String KEYSTORE_FILE = "keystore.p12";
  private static final File FULL_PATH = new File(
      ConfigManager.getInstance().getConfigPath() + KEYSTORE_FILE);
  private static final String CONTAINER_FORMAT = "pkcs12";
  private static final Logger logger = Logger.getLogger(KeyContainer.class.getName());

  private KeyContainer() {
  }

  static KeyStore createNewKeyStoreOnDisk() {
    KeyStore ks;
    try {
      ks = KeyStore.getInstance(CONTAINER_FORMAT);
      ks.load(null, getEncodedPassword());
    } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException e) {
      logger.log(Level.WARNING, "Could not create instance of KeyStore", e);
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

  static KeyStore loadKeyStoreFromDisk() throws FileNotFoundException {
    KeyStore ks;
    try (FileInputStream fis = new FileInputStream(FULL_PATH)) {
      ks = KeyStore.getInstance(CONTAINER_FORMAT);
      ks.load(fis, getEncodedPassword());
    } catch (FileNotFoundException e) {
      throw e;
    } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
      logger.log(Level.WARNING, "KeyStore failure", e);
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
          String.format("Could not safe new certificate %s", certificate.toString()),
          e);
    }

    safeKeyStore(keyStore);
  }

  static boolean existsKeyingMaterialFor(final KeyStore keyStore, final String userFriendlyName) {
    boolean keyExists;
    try {
      Key certificate = keyStore.getKey(userFriendlyName, getEncodedPassword());
      if (certificate != null) {
        keyExists = true;
        logger.log(Level.INFO, "Key for {0} exists", userFriendlyName);
      } else {
        keyExists = false;
        logger.log(Level.INFO, "Key for {0} does not exists", userFriendlyName);
      }
    } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
      keyExists = false;
      logger.log(Level.INFO, String.format("Key for %s does not exists", userFriendlyName));
    }
    return keyExists;
  }

  static void safePrivateKeyAndX509CertificateToKeyStore(
      final KeyStore keyStore, final X509Certificate certificate, final PrivateKey privateKey) {

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
    safeKeyStore(keyStore);
  }

  static X509Certificate getX509CertificateFromKeyStore(
      final KeyStore keyStore, final String userFriendlyName) throws KeyStoreException {
    return (X509Certificate) keyStore.getCertificate(userFriendlyName);
  }

  private static char[] getEncodedPassword() {
    String encoded = "" + 0x66 + 0x6F + 0x6F + 0x62 + 0x61 + 0x72;
    return encoded.toCharArray();
  }

  private static String getAlias(final X509Certificate certificate) {
    Principal principal = certificate.getSubjectDN();
    return getCN(principal);
  }

  private static String getCN(final Principal principal) {
    int start = principal.getName().indexOf("CN=") + 3;
    int end = principal.getName().indexOf(',');
    if (end == -1) {
      end = principal.getName().length();
    }
    return principal.getName().substring(start, end).trim();
  }
}
