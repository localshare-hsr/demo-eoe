package ch.hsr.epj.localshare.demo.logic.keymanager;

import javax.xml.bind.DatatypeConverter;
import java.io.FileNotFoundException;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyManager {
    private static final Logger logger = Logger.getLogger(KeyManager.class.getName());

    private KeyStore keyStore;
    private String userFriendlyName;

    public KeyManager() {
        try {
            this.keyStore = KeyContainer.loadKeyStoreFromDisk();
        } catch (FileNotFoundException e) {
            this.keyStore = KeyContainer.createNewKeyStoreOnDisk();
        }
        KeyContainer.safeKeyStore(this.keyStore);
        userFriendlyName = "";
    }

    /**
     * KeyStore with users public certificate and private key.
     *
     * <p>KeyStore is used for creating a tls context to setup https server.
     *
     * @return current KeyStore with all certificates and private keys
     */
    public KeyStore getKeyStore() {
        return this.keyStore;
    }

    public boolean existsKeyingMaterial(final String userFriendlyName) {
        this.userFriendlyName = userFriendlyName;
        return KeyContainer.existsKeyingMaterialFor(this.keyStore, userFriendlyName);
    }

    public void generateKeyingMaterial(final String userFriendlyName) {
        this.userFriendlyName = userFriendlyName;
        KeyGenerator keyGenerator = new KeyGenerator(userFriendlyName);
        X509Certificate certificate = keyGenerator.generateNewX509Certificate();
        PrivateKey privateKey = keyGenerator.getPrivateKey();
        KeyContainer.safePrivateKeyAndX509CertificateToKeyStore(this.keyStore, certificate, privateKey);
    }

    /**
     * Formatted finger print of users identity.
     *
     * <p>Finger print is calculated over users public certificate.
     *
     * @return Formatted finger print
     */
    public String getUsersFingerprint() throws KeyStoreException {
        String fingerprint = "";

        try {
            X509Certificate cert = (X509Certificate) this.keyStore.getCertificate(userFriendlyName);
            byte[] certificateDER = cert.getEncoded();
            fingerprint =
                    DatatypeConverter.printHexBinary(
                            MessageDigest.getInstance("SHA-256").digest(certificateDER))
                            .toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "SHA-256 not supported");
        } catch (CertificateEncodingException e) {
            logger.log(Level.SEVERE, "DER not supported");
        }

        return addSpace(fingerprint);
    }

    /**
     * Add a new X.509 Certificate to trusted key store
     *
     * @param newCertificate in for trusted store
     */
    public void addTrustedCertificate(final X509Certificate newCertificate) {
        KeyContainer.safeX509CertificateToKeyStore(this.keyStore, newCertificate);
    }

    /**
     * Get X.509 Certificate for alias from trusted store
     *
     * @param alias Name of the certificate in the store
     * @return X.509 Certificate of alias
     */
    public X509Certificate getTrustedCertificate(final String alias) throws KeyStoreException {
        return KeyContainer.getX509CertificateFromKeyStore(this.keyStore, alias);
    }

    private String addSpace(final String fingerprint) {
        StringBuilder sb = new StringBuilder();

        int begin = 0;
        for (int i = 0; i < fingerprint.length(); i += 4) {

            sb.append(fingerprint.substring(begin, i) + " ");

            begin = i;
        }

        return sb.toString().trim();
    }
}
