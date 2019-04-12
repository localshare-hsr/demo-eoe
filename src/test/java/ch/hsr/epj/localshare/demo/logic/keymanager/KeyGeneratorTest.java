package ch.hsr.epj.localshare.demo.logic.keymanager;

import org.junit.Before;
import org.junit.Test;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KeyGeneratorTest {
  private X509Certificate certificate;

  @Before
  public void generateNewCertificate() {
    KeyGenerator keyGenerator = new KeyGenerator("FooBar");
    this.certificate = keyGenerator.generateNewX509Certificate();
  }

  @Test
  public void testCertificateType() {
    assertEquals("X.509", certificate.getType());
  }

  @Test
  public void testVerifyX509Version3() {
    assertEquals(3, certificate.getVersion());
  }

  @Test
  public void testSignatureAlgorithm() {
    assertEquals("SHA256WITHECDSA", certificate.getSigAlgName());
  }

}
