package ch.hsr.epj.localshare.demo.logic.keymanager;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class KeyManager {

  public KeyManager() {
    KeyGenerator keyGenerator = new KeyGenerator("Sir FooBar");
    X509Certificate certificate = keyGenerator.generateNewX509Certificate();
    PrivateKey privateKey = keyGenerator.getPrivateKey();


    KeyStore keyStore = KeyContainer.createNewKeyStoreOnDisk();
    KeyContainer.safeKeyStore(keyStore);
    KeyStore keyStore2 = KeyContainer.loadKeyStoreFromDisk();
    KeyContainer.safeX509CertificateToKeyStore(keyStore2, certificate);
    KeyContainer.safePrivateKeyAndX509CertificateToKeyStore(keyStore2, certificate, privateKey);
    KeyContainer.safeKeyStore(keyStore2);

  }
}
