package ch.hsr.epj.localshare.demo.logic.keymanager;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class KeyManagerTest {

    private X509Certificate testCertificate1;
    private X509Certificate testCertificate2;

    @Before
    public void loadTestCertificate() throws CertificateException, FileNotFoundException {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");

        File file1 = new File(ClassLoader.getSystemResource("certificates/elvis.crt").getFile());
        testCertificate1 = (X509Certificate) factory.generateCertificate(new FileInputStream(file1));

        File file2 = new File(ClassLoader.getSystemResource("certificates/wwwhsrch.crt").getFile());
        testCertificate2 = (X509Certificate) factory.generateCertificate(new FileInputStream(file2));
    }

    @Test
    public void testCertificateLoader() {
        assertNotNull(testCertificate1);
    }

    @Test
    public void testKeyStore() {
        KeyManager keyManager = new KeyManager();
        assertNotNull(keyManager.getKeyStore());
    }

    @Test
    public void testSafeNewCertificate() throws KeyStoreException {
        KeyManager keyManager = new KeyManager();
        keyManager.addTrustedCertificate(testCertificate2);
        assertEquals(
                testCertificate2,
                keyManager.getTrustedCertificate("www.hsr.ch"));
    }
}
