package ch.hsr.epj.localshare.demo.logic.keymanager;

import static org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

class KeyGenerator {

  private static final String SIGNATURE_ALGORITHM = "SHA256WITHECDSA";
  private static final String KEY_GENERATION_ALGORITHM = "ECDH";

  private static final Logger logger = Logger.getLogger(KeyGenerator.class.getName());

  private String friendlyName;
  private KeyPair keyPair;
  private X509Certificate certificate;

  private KeyGenerator() {
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    keyPair = null;
    certificate = null;
  }

  KeyGenerator(String friendlyName) {
    this();
    this.friendlyName = friendlyName;
  }

  private static X509Certificate signCertificate(
      final X509v3CertificateBuilder builder, final PrivateKey privateKey)
      throws CertificateException, OperatorCreationException {
    ContentSigner signer =
        new JcaContentSignerBuilder(SIGNATURE_ALGORITHM)
            .setProvider(PROVIDER_NAME)
            .build(privateKey);
    return new JcaX509CertificateConverter()
        .setProvider(PROVIDER_NAME)
        .getCertificate(builder.build(signer));
  }

  X509Certificate generateNewX509Certificate() {
    keyPair = getKeyPair();

    if (certificate == null) {
      try {
        certificate = generateV3Certificate(keyPair, friendlyName);
      } catch (CertificateException | OperatorCreationException e) {
        logger.log(Level.SEVERE, "Could not generate new certificate", e);
      }
    }

    return certificate;
  }

  PrivateKey getPrivateKey() {
    return getKeyPair().getPrivate();
  }

  private KeyPair getKeyPair() {
    if (keyPair == null) {
      try {
        keyPair = generateKeyPair();
      } catch (NoSuchAlgorithmException
          | InvalidAlgorithmParameterException
          | NoSuchProviderException e) {
        logger.log(Level.SEVERE, "Could not generate key pair", e);
      }
    }
    return keyPair;
  }

  private KeyPair generateKeyPair()
      throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchProviderException {
    KeyPairGenerator keyPairGenerator =
        KeyPairGenerator.getInstance(KEY_GENERATION_ALGORITHM, "BC");
    ECGenParameterSpec spec = new ECGenParameterSpec("secp256r1");
    keyPairGenerator.initialize(spec);
    return keyPairGenerator.generateKeyPair();
  }

  private X509Certificate generateV3Certificate(final KeyPair pair, final String cn)
      throws CertificateException, OperatorCreationException {
    X500Name issuerName = new X500Name("CN=" + cn);

    BigInteger serial = BigInteger.valueOf(new SecureRandom().nextInt());

    Date notBefore = new Date(System.currentTimeMillis() - 3600);
    Date notAfter = new Date(System.currentTimeMillis() + 30758400);

    X509v3CertificateBuilder builder =
        new JcaX509v3CertificateBuilder(
            issuerName, serial, notBefore, notAfter, issuerName, pair.getPublic());

    KeyUsage usage =
        new KeyUsage(
            KeyUsage.keyCertSign
                | KeyUsage.digitalSignature
                | KeyUsage.keyEncipherment
                | KeyUsage.dataEncipherment
                | KeyUsage.cRLSign);
    try {
      builder.addExtension(Extension.keyUsage, false, usage);
    } catch (CertIOException e) {
      logger.log(Level.SEVERE, "Could not add extension to certificate", e);
    }

    return signCertificate(builder, pair.getPrivate());
  }
}
