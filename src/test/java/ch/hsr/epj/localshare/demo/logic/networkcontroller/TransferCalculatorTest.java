package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import static org.junit.Assert.assertEquals;

import ch.hsr.epj.localshare.demo.logic.networkcontroller.TransferCalculator.BytePrefix;
import org.junit.Test;

public class TransferCalculatorTest {

  private static final int BYTE_0 = 0;
  private static final int BYTE_1 = 1;
  private static final int BYTE_10 = 10;
  private static final int BYTE_100 = 100;

  private static final int KILO_1 = 1_000;
  private static final int KILO_10 = 10_000;
  private static final int KILO_100 = 100_000;
  private static final int MEGA_1 = 1_000_000;
  private static final int MEGA_10 = 10_000_000;
  private static final int MEGA_100 = 100_000_000;
  private static final int GIGA_1 = 1_000_000_000;
  private static final long GIGA_10 = 10_000_000_000L;
  private static final long GIGA_100 = 100_000_000_000L;
  private static final long GIGA_1000 = 1_000_000_000_000L;

  private static final int KIBI_1 = (int) Math.pow(2, 10);
  private static final int KIBI_10 = 10 * KIBI_1;
  private static final int KIBI_100 = 10 * KIBI_10;
  private static final int MEBI_1 = (int) Math.pow(2, 20);
  private static final int MEBI_10 = 10 * MEBI_1;
  private static final int MEBI_100 = 10 * MEBI_10;
  private static final int GIBI_1 = (int) Math.pow(2, 30);
  private static final long GIBI_10 = (long) 10 * GIBI_1;
  private static final long GIBI_100 = (long) 10 * GIBI_10;
  private static final long GIBI_1000 = (long) 10 * GIBI_100;

  @Test
  public void testDecimal0BYTE() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferCalculator.formatBytesToNiceString(BYTE_0);
    String expected = "0.0 B/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1BYTE() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferCalculator.formatBytesToNiceString(BYTE_1);
    String expected = "1.0 B/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10BYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferCalculator.formatBytesToNiceString(BYTE_10);
    String expected = "10.0 B/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100BYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferCalculator.formatBytesToNiceString(BYTE_100);
    String expected = "100.0 B/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1KILOBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferCalculator.formatBytesToNiceString(KILO_1);
    String expected = "1.0 kB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10KILOBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferCalculator.formatBytesToNiceString(KILO_10);
    String expected = "10.0 kB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100KILOBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferCalculator.formatBytesToNiceString(KILO_100);
    String expected = "100.0 kB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1MEGABYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferCalculator.formatBytesToNiceString(MEGA_1);
    String expected = "1.0 MB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10MEGABYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferCalculator.formatBytesToNiceString(MEGA_10);
    String expected = "10.0 MB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100MEGABYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferCalculator.formatBytesToNiceString(MEGA_100);
    String expected = "100.0 MB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1GIGABYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferCalculator.formatBytesToNiceString(GIGA_1);
    String expected = "1.0 GB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10GIGABYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferCalculator.formatBytesToNiceString(GIGA_10);
    String expected = "10.0 GB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100GIGABYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferCalculator.formatBytesToNiceString(GIGA_100);
    String expected = "100.0 GB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1000GIGABYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferCalculator.formatBytesToNiceString(GIGA_1000);
    String expected = "1000.0 GB/s";
    assertEquals(expected, result);
  }


  @Test
  public void testDecimal0BYTEBINARY() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        false);
    String result = transferCalculator.formatBytesToNiceString(BYTE_0);
    String expected = "0.0 B/s";
    assertEquals(expected, result);
  }


  @Test
  public void testDecimal1KIBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        false);
    String result = transferCalculator.formatBytesToNiceString(KIBI_1);
    String expected = "1.0 KiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10KIBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        false);
    String result = transferCalculator.formatBytesToNiceString(KIBI_10);
    String expected = "10.0 KiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100KIBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        false);
    String result = transferCalculator.formatBytesToNiceString(KIBI_100);
    String expected = "100.0 KiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1MEBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        false);
    String result = transferCalculator.formatBytesToNiceString(MEBI_1);
    String expected = "1.0 MiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10MEBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        false);
    String result = transferCalculator.formatBytesToNiceString(MEBI_10);
    String expected = "10.0 MiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100MEBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        false);
    String result = transferCalculator.formatBytesToNiceString(MEBI_100);
    String expected = "100.0 MiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1GIBBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        false);
    String result = transferCalculator.formatBytesToNiceString(GIBI_1);
    String expected = "1.0 GiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10GIBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        false);
    String result = transferCalculator.formatBytesToNiceString(GIBI_10);
    String expected = "10.0 GiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100GIBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        false);
    String result = transferCalculator.formatBytesToNiceString(GIBI_100);
    String expected = "100.0 GiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1000GIBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        false);
    String result = transferCalculator.formatBytesToNiceString(GIBI_1000);
    String expected = "1000.0 GiB/s";
    assertEquals(expected, result);
  }


  @Test
  public void testSizeDecimal0BYTE() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferCalculator.formatBytesToNiceString(BYTE_0);
    String expected = "0.0 B";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1BYTE() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferCalculator.formatBytesToNiceString(BYTE_1);
    String expected = "1.0 B";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal10BYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferCalculator.formatBytesToNiceString(BYTE_10);
    String expected = "10.0 B";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal100BYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferCalculator.formatBytesToNiceString(BYTE_100);
    String expected = "100.0 B";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1KILOBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferCalculator.formatBytesToNiceString(KILO_1);
    String expected = "1.0 kB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal10KILOBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferCalculator.formatBytesToNiceString(KILO_10);
    String expected = "10.0 kB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal100KILOBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferCalculator.formatBytesToNiceString(KILO_100);
    String expected = "100.0 kB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1MEGABYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferCalculator.formatBytesToNiceString(MEGA_1);
    String expected = "1.0 MB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal10MEGABYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferCalculator.formatBytesToNiceString(MEGA_10);
    String expected = "10.0 MB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal100MEGABYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferCalculator.formatBytesToNiceString(MEGA_100);
    String expected = "100.0 MB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1GIGABYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferCalculator.formatBytesToNiceString(GIGA_1);
    String expected = "1.0 GB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal10GIGABYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferCalculator.formatBytesToNiceString(GIGA_10);
    String expected = "10.0 GB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal100GIGABYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferCalculator.formatBytesToNiceString(GIGA_100);
    String expected = "100.0 GB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1000GIGABYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferCalculator.formatBytesToNiceString(GIGA_1000);
    String expected = "1000.0 GB";
    assertEquals(expected, result);
  }


  @Test
  public void testSizeDecimal0BYTEBINARY() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        true);
    String result = transferCalculator.formatBytesToNiceString(BYTE_0);
    String expected = "0.0 B";
    assertEquals(expected, result);
  }


  @Test
  public void testSizeDecimal1KIBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        true);
    String result = transferCalculator.formatBytesToNiceString(KIBI_1);
    String expected = "1.0 KiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal10KIBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        true);
    String result = transferCalculator.formatBytesToNiceString(KIBI_10);
    String expected = "10.0 KiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal100KIBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        true);
    String result = transferCalculator.formatBytesToNiceString(KIBI_100);
    String expected = "100.0 KiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1MEBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        true);
    String result = transferCalculator.formatBytesToNiceString(MEBI_1);
    String expected = "1.0 MiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal10MEBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        true);
    String result = transferCalculator.formatBytesToNiceString(MEBI_10);
    String expected = "10.0 MiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal100MEBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        true);
    String result = transferCalculator.formatBytesToNiceString(MEBI_100);
    String expected = "100.0 MiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1GIBBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        true);
    String result = transferCalculator.formatBytesToNiceString(GIBI_1);
    String expected = "1.0 GiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal10GIBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        true);
    String result = transferCalculator.formatBytesToNiceString(GIBI_10);
    String expected = "10.0 GiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal100GIBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        true);
    String result = transferCalculator.formatBytesToNiceString(GIBI_100);
    String expected = "100.0 GiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1000GIBIBYTES() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.BINARY,
        true);
    String result = transferCalculator.formatBytesToNiceString(GIBI_1000);
    String expected = "1000.0 GiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal42GIGABYTE() {
    TransferCalculator transferCalculator = new TransferCalculator(BytePrefix.DECIMAL,
        true);
    String result = transferCalculator.formatBytesToNiceString(42_100_000_000L);
    String expected = "42.1 GB";
    assertEquals(expected, result);
  }

}