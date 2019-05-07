package ch.hsr.epj.localshare.demo.logic.keymanager;

import ch.hsr.epj.localshare.demo.persistence.KeyContainer;
import java.io.File;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.List;

public class KeyManager implements KeyManagerServerInterface {

  private KeyContainer keyContainer;
  private KeyPeer user;
  private List<KeyPeer> peerList;

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
  public boolean addTrustedCertificate(final X509Certificate newCertificate) {
    boolean success;
    KeyPeer keyPeer = new KeyPeer(newCertificate);
    peerList.add(keyPeer);
    success = keyContainer.addPeerCertificate(keyPeer.getFriendlyName(), newCertificate);
    return success;
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
  public X509Certificate getTrustedCertificate(final String alias) {
    return keyContainer.getCertificate(alias);
  }

  public KeyPeer getUser() {
    return user;
  }

  @Override
  public KeyStore getKeyStore() {
    //TODO
    return null;
  }

  private void generateKeyingMaterial(final String userFriendlyName) {
    KeyGenerator keyGenerator = new KeyGenerator(userFriendlyName);
    X509Certificate newUserCertificate = keyGenerator.generateNewX509Certificate();
    user = new KeyPeer(newUserCertificate);
    PrivateKey privateKey = keyGenerator.getPrivateKey();
    keyContainer.addUserKeyingMaterial(userFriendlyName, newUserCertificate, privateKey);
  }

}
