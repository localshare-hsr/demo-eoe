package ch.hsr.epj.localshare.demo.logic.keymanager;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import org.junit.Before;

public class KeyManagerTest {

  private static final String TEST_USER = "Elvis";
  private static final String HSR_CERTIFICATE =
      "-----BEGIN CERTIFICATE-----\n"
          + "MIIImTCCB4GgAwIBAgIUBtSDyzaKnE5WhHpNQJPKU6cdPS4wDQYJKoZIhvcNAQEL\n"
          + "BQAwSTELMAkGA1UEBhMCQk0xGTAXBgNVBAoTEFF1b1ZhZGlzIExpbWl0ZWQxHzAd\n"
          + "BgNVBAMTFlF1b1ZhZGlzIEVWIFNTTCBJQ0EgRzEwHhcNMTgwNzI1MTEzMDA2WhcN\n"
          + "MjAwNzI1MTE0MDAwWjCCAQwxEzARBgsrBgEEAYI3PAIBAxMCQ0gxGzAZBgsrBgEE\n"
          + "AYI3PAIBAgwKU3QuIEdhbGxlbjEbMBkGCysGAQQBgjc8AgEBDApSYXBwZXJzd2ls\n"
          + "MRowGAYDVQQPDBFHb3Zlcm5tZW50IEVudGl0eTEeMBwGA1UEBRMVTlRSQ0gtQ0hF\n"
          + "LTExNi4wNzguMzkxMQswCQYDVQQGEwJDSDETMBEGA1UECAwKU3QuIEdhbGxlbjET\n"
          + "MBEGA1UEBwwKUmFwcGVyc3dpbDEeMBwGA1UECgwVSG9jaHNjaHVsZSBSYXBwZXJz\n"
          + "d2lsMRMwEQYDVQQLDApJVC1TeXN0ZW1zMRMwEQYDVQQDDAp3d3cuaHNyLmNoMIIC\n"
          + "IjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEApy53P/DkYmQNT+8OsV5Ur6hv\n"
          + "FKB78zSTVsQrlIFvwHaM1G8yUjJMzbHwX8MII8GRVsKoqMcGxRBm5lOPK08SssMi\n"
          + "OZi5PpCtRhbsJYFo2EwPNySFDIgeNltrwkJ7dAZzsgyN2k6/ot+d1S1M5uj3+J9D\n"
          + "niMZa20Qom0oMTaiYtyQXOltM9KQDJvH3/7xk6+hf4EbcHbk0bvh5FMpCbDIIBwj\n"
          + "kkuxOShsGG/j/nkEvLYVxQanC17brA+mLboc9bnPel3DKsUWXE/Q+Ww713288PVt\n"
          + "uHNElPAWty6mUarAyN+lBcR3oh/g1nDDJ/AZMFjr7VC8YWLzRUpwfBFv3ilW9hnb\n"
          + "f5HxZTE5Nburkt3NeqfAqwNpl/ZIi1QQr1oK2u0VeL6ns2c1Hbcgs+s4aRSQZlgo\n"
          + "aUKS3cnRK8S/RCcd4Yq/DeIkI8X8g9+bFmD5cBxsbZUSpXF2n36wNJxfRQRYZckG\n"
          + "s5uv/YeX34eoIetTJb+0ouIqbQvIE1r1dU6a4ktH/QvdY+XwVxOR7s1tGep9umNQ\n"
          + "SD2los/leHcdM5X980ATlRNGJWDxb6Oh2DH9BkJ+m1DrHn80q7G8NSzeoHhtYjJo\n"
          + "8YbfmFrq5WCeOeJFriCGi77IvHHyPhLWGtqJ0ZHKqMY7sSxrWF2M+aBooobF/AQk\n"
          + "l/Gb71j5dftoZHqorDsCAwEAAaOCA7IwggOuMHcGCCsGAQUFBwEBBGswaTA4Bggr\n"
          + "BgEFBQcwAoYsaHR0cDovL3RydXN0LnF1b3ZhZGlzZ2xvYmFsLmNvbS9xdmV2c3Ns\n"
          + "MS5jcnQwLQYIKwYBBQUHMAGGIWh0dHA6Ly9ldi5vY3NwLnF1b3ZhZGlzZ2xvYmFs\n"
          + "LmNvbTAdBgNVHQ4EFgQU/ptD9elVXyxqiDyAfUNDenYYl5cwDAYDVR0TAQH/BAIw\n"
          + "ADAfBgNVHSMEGDAWgBRVWIbOunx2TpkTqQ/TbJ/C9dM84zBaBgNVHSAEUzBRMEYG\n"
          + "DCsGAQQBvlgAAmQBAjA2MDQGCCsGAQUFBwIBFihodHRwOi8vd3d3LnF1b3ZhZGlz\n"
          + "Z2xvYmFsLmNvbS9yZXBvc2l0b3J5MAcGBWeBDAEBMDsGA1UdHwQ0MDIwMKAuoCyG\n"
          + "Kmh0dHA6Ly9jcmwucXVvdmFkaXNnbG9iYWwuY29tL3F2ZXZzc2wxLmNybDAOBgNV\n"
          + "HQ8BAf8EBAMCBaAwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMBMCEGA1Ud\n"
          + "EQQaMBiCCnd3dy5oc3IuY2iCCmxvZy5oc3IuY2gwggH4BgorBgEEAdZ5AgQCBIIB\n"
          + "6ASCAeQB4gB3ALvZ37wfinG1k5Qjl6qSe0c4V5UKq1LoGpCWZDaOHtGFAAABZNE9\n"
          + "NO4AAAQDAEgwRgIhAMch1+5vCzkAjQn8PgQUp66edCroeycIp4D+dUXW3PJQAiEA\n"
          + "09ZcoUnLgUV4fHf2ozsVIzADSJ2TTr1dsxF3lwc0JocAdgCkuQmQtBhYFIe7E6LM\n"
          + "Z3AKPDWYBPkb37jjd80OyA3cEAAAAWTRPTUeAAAEAwBHMEUCIFzD4IVIp5JNJ4Dq\n"
          + "iUzWgGKiW41ax1MOP0KaG2kvJNTlAiEAht1Ei2ayQ7ullfX3yTTb70MeCO3LSlZf\n"
          + "2nj/rEOiYXwAdgBWFAaaL9fC7NP14b1Esj7HRna5vJkRXMDvlJhV1onQ3QAAAWTR\n"
          + "PTczAAAEAwBHMEUCIHiVDnUoSeSoemtmRJmWYfPI8BXmvwv1bXzGOYqSSRk7AiEA\n"
          + "ofjewCB+hjqlYY8KTjyzoCbI0DCcY4FGCl5jySTDDJ4AdwBvU3asMfAxGdiZAKRR\n"
          + "Ff93FRwR2QLBACkGjbIImjfZEwAAAWTRPTevAAAEAwBIMEYCIQD51bZ125aq21dr\n"
          + "VHnOfTLj9CO2PK4mvmy+G9Gxif5RJAIhAJxLRGEkpp/9Zleuhz2rRdUJk6MzNMSb\n"
          + "PufEkC4hNvDhMA0GCSqGSIb3DQEBCwUAA4IBAQCM73wh3nY3/6De7GeS+bu2w6xe\n"
          + "PlC0lWBBAbbygDXA5mz6ZcyM+gZIDdN7Ex1axKL4W/2DnyuZy+I0pTxa/rw3KM69\n"
          + "c8hluswsBF5Mi9+40aEo/+ipUV0opZwB/dkfWRMiNVzwntwtea3mt0W/I+uilTgU\n"
          + "a3Yorlco0QjsQZqW4JkYscqGSo216g1hKy3oqDq6EKLzhV/xdV5jEzef1IGeeTyN\n"
          + "7e4MAkByraidqSTswPPJ1uPiWjyFtMsv+o8lCYM5dRAPvnwMcrTFJOkVhnqjpqw/\n"
          + "6bvZ4Jgf7T4zK6xBKN6SHTDXXteFGKWbDiYpNcc/OKQn6kK40R5gPQV+/vsk\n"
          + "-----END CERTIFICATE-----";
  private static final String ELVIS_CERTIFICATE = "-----BEGIN CERTIFICATE-----\n"
      + "MIIBIDCBx6ADAgECAgSg4L6+MAoGCCqGSM49BAMCMBAxDjAMBgNVBAMMBUVsdmlz\n"
      + "MB4XDTE5MDQxOTIwMjY1MloXDTE5MDQyMDA0NTkzNFowEDEOMAwGA1UEAwwFRWx2\n"
      + "aXMwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAAQmMAgD7dhM7qLFJRkfaH44uE4I\n"
      + "mPRqGUI+DZk6KosZ4K0zr59fKgXRftqI45KKOOHo+Bp0K/h+rIkGJLavCr8wow8w\n"
      + "DTALBgNVHQ8EBAMCAbYwCgYIKoZIzj0EAwIDSAAwRQIgQSdoDTb/MpROqL6puxlm\n"
      + "nt7HjYY4807OBQRPJSMUoLoCIQD1enHxeStCp6K6GULXCsMlod4RC5mnbmm5ESa3\n"
      + "4HB3IQ==\n"
      + "-----END CERTIFICATE-----";

  private X509Certificate testCertificate1;
  private X509Certificate testCertificate2;

  @Before
  public void loadTestCertificate() throws CertificateException {
    CertificateFactory factory = CertificateFactory.getInstance("X.509");

    testCertificate1 = (X509Certificate) factory
        .generateCertificate(new ByteArrayInputStream(HSR_CERTIFICATE.getBytes()));
    testCertificate2 = (X509Certificate) factory
        .generateCertificate(new ByteArrayInputStream(ELVIS_CERTIFICATE.getBytes()));
  }

/*  @Test
  public void testKeyStoreNonNull() {
    KeyManager keyManager = new KeyManager();
    assertNotNull(keyManager.getKeyStore());
  }*/

/*  @Test
  public void testKeyStoreKeyingMaterialForTestUserDoesNotExists() {
    KeyManager keyManager = new KeyManager();
    assertFalse(keyManager.existsKeyingMaterial(TEST_USER));
  }

  @Test
  public void testKeyStoreKeyingMaterialForTestUserExists() {
    KeyManager keyManager = new KeyManager();
    keyManager.generateKeyingMaterial(TEST_USER);
    assertTrue(keyManager.existsKeyingMaterial(TEST_USER));
  }

  @Test
  public void testSafeNewCertificate() throws KeyStoreException {
    KeyManager keyManager = new KeyManager();
    keyManager.addTrustedCertificate(testCertificate1);
    X509Certificate result = keyManager.getTrustedCertificate("www.hsr.ch");
    assertEquals(testCertificate1, result);
  }*/
}
