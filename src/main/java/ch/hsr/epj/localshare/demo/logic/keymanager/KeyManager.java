package ch.hsr.epj.localshare.demo.logic.keymanager;

import ch.hsr.epj.localshare.demo.persistence.KeyContainer;
import java.io.File;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class KeyManager {

  private static final Logger logger = Logger.getLogger(KeyManager.class.getName());

  private KeyContainer keyContainer;
  private KeyPeer user;
  private List<KeyPeer> peerList;

  private KeyManager() {
  }

  public KeyManager(String path, String file, String friendlyName) {
    File filePath = new File(path);
    keyContainer = new KeyContainer(filePath, file);

    if (!keyContainer.existsUserKeyingMaterial(friendlyName)) {
      generateKeyingMaterial(friendlyName);
    }

    X509Certificate cert = keyContainer.getCertificate(friendlyName);
    user = new KeyPeer(cert);
    peerList = new LinkedList<>();
  }

  /**
   * Formatted finger print of users identity.
   *
   * <p>Finger print is calculated over users public certificate.
   *
   * @return Formatted finger print
   */
  public String getUsersFingerprint() {
    return user.getFingerprintSpaces();
  }

  /**
   * Add a new X.509 Certificate to trusted key store
   *
   * @param newCertificate in for trusted store
   */
  public void addTrustedCertificate(final X509Certificate newCertificate) {
    KeyPeer keyPeer = new KeyPeer(newCertificate);
    peerList.add(keyPeer);
    keyContainer.addPeerCertificate(keyPeer.getFriendlyName(), newCertificate);
  }

  /**
   * Get list of all known trusted peers.
   *
   * @return All known trusted peers
   */
  public List<KeyPeer> getPeerList() {
    return peerList;
  }

  /**
   * Get X.509 Certificate for alias from trusted store
   *
   * @param alias Name of the certificate in the store
   * @return X.509 Certificate of alias
   */
  public X509Certificate getTrustedCertificate(final String alias) throws KeyStoreException {
    return keyContainer.getCertificate(alias);
  }

  public KeyPeer getUser() {
    return user;
  }

  private void generateKeyingMaterial(final String userFriendlyName) {
    KeyGenerator keyGenerator = new KeyGenerator(userFriendlyName);
    X509Certificate newUserCertificate = keyGenerator.generateNewX509Certificate();
    user = new KeyPeer(newUserCertificate);
    PrivateKey privateKey = keyGenerator.getPrivateKey();
    keyContainer.addUserKeyingMaterial(userFriendlyName, newUserCertificate, privateKey);
  }

}
