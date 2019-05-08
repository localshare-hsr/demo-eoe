package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import static org.junit.Assert.assertEquals;

import ch.hsr.epj.localshare.demo.logic.networkcontroller.TransferSizeCalculator.BytePrefix;
import org.junit.Test;

public class TransferSizeCalculatorTest {

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
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferSizeCalculator.formatBytesToNiceString(BYTE_0);
    String expected = "0 B/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1BYTE() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferSizeCalculator.formatBytesToNiceString(BYTE_1);
    String expected = "1 B/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10BYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferSizeCalculator.formatBytesToNiceString(BYTE_10);
    String expected = "10 B/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100BYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferSizeCalculator.formatBytesToNiceString(BYTE_100);
    String expected = "100 B/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1KILOBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferSizeCalculator.formatBytesToNiceString(KILO_1);
    String expected = "1 kB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10KILOBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferSizeCalculator.formatBytesToNiceString(KILO_10);
    String expected = "10 kB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100KILOBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferSizeCalculator.formatBytesToNiceString(KILO_100);
    String expected = "100 kB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1MEGABYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferSizeCalculator.formatBytesToNiceString(MEGA_1);
    String expected = "1 MB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10MEGABYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferSizeCalculator.formatBytesToNiceString(MEGA_10);
    String expected = "10 MB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100MEGABYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferSizeCalculator.formatBytesToNiceString(MEGA_100);
    String expected = "100 MB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1GIGABYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferSizeCalculator.formatBytesToNiceString(GIGA_1);
    String expected = "1 GB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10GIGABYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferSizeCalculator.formatBytesToNiceString(GIGA_10);
    String expected = "10 GB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100GIGABYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferSizeCalculator.formatBytesToNiceString(GIGA_100);
    String expected = "100 GB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1000GIGABYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, false);
    String result = transferSizeCalculator.formatBytesToNiceString(GIGA_1000);
    String expected = "1000 GB/s";
    assertEquals(expected, result);
  }


  @Test
  public void testDecimal0BYTEBINARY() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        false);
    String result = transferSizeCalculator.formatBytesToNiceString(BYTE_0);
    String expected = "0 B/s";
    assertEquals(expected, result);
  }


  @Test
  public void testDecimal1KIBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        false);
    String result = transferSizeCalculator.formatBytesToNiceString(KIBI_1);
    String expected = "1 KiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10KIBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        false);
    String result = transferSizeCalculator.formatBytesToNiceString(KIBI_10);
    String expected = "10 KiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100KIBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        false);
    String result = transferSizeCalculator.formatBytesToNiceString(KIBI_100);
    String expected = "100 KiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1MEBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        false);
    String result = transferSizeCalculator.formatBytesToNiceString(MEBI_1);
    String expected = "1 MiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10MEBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        false);
    String result = transferSizeCalculator.formatBytesToNiceString(MEBI_10);
    String expected = "10 MiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100MEBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        false);
    String result = transferSizeCalculator.formatBytesToNiceString(MEBI_100);
    String expected = "100 MiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1GIBBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        false);
    String result = transferSizeCalculator.formatBytesToNiceString(GIBI_1);
    String expected = "1 GiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10GIBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        false);
    String result = transferSizeCalculator.formatBytesToNiceString(GIBI_10);
    String expected = "10 GiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100GIBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        false);
    String result = transferSizeCalculator.formatBytesToNiceString(GIBI_100);
    String expected = "100 GiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1000GIBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        false);
    String result = transferSizeCalculator.formatBytesToNiceString(GIBI_1000);
    String expected = "1000 GiB/s";
    assertEquals(expected, result);
  }


  @Test
  public void testSizeDecimal0BYTE() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferSizeCalculator.formatBytesToNiceString(BYTE_0);
    String expected = "0 B";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1BYTE() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferSizeCalculator.formatBytesToNiceString(BYTE_1);
    String expected = "1 B";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal10BYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferSizeCalculator.formatBytesToNiceString(BYTE_10);
    String expected = "10 B";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal100BYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferSizeCalculator.formatBytesToNiceString(BYTE_100);
    String expected = "100 B";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1KILOBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferSizeCalculator.formatBytesToNiceString(KILO_1);
    String expected = "1 kB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal10KILOBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferSizeCalculator.formatBytesToNiceString(KILO_10);
    String expected = "10 kB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal100KILOBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferSizeCalculator.formatBytesToNiceString(KILO_100);
    String expected = "100 kB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1MEGABYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferSizeCalculator.formatBytesToNiceString(MEGA_1);
    String expected = "1 MB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal10MEGABYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferSizeCalculator.formatBytesToNiceString(MEGA_10);
    String expected = "10 MB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal100MEGABYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferSizeCalculator.formatBytesToNiceString(MEGA_100);
    String expected = "100 MB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1GIGABYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferSizeCalculator.formatBytesToNiceString(GIGA_1);
    String expected = "1 GB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal10GIGABYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferSizeCalculator.formatBytesToNiceString(GIGA_10);
    String expected = "10 GB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal100GIGABYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferSizeCalculator.formatBytesToNiceString(GIGA_100);
    String expected = "100 GB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1000GIGABYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(
        BytePrefix.DECIMAL, true);
    String result = transferSizeCalculator.formatBytesToNiceString(GIGA_1000);
    String expected = "1000 GB";
    assertEquals(expected, result);
  }


  @Test
  public void testSizeDecimal0BYTEBINARY() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        true);
    String result = transferSizeCalculator.formatBytesToNiceString(BYTE_0);
    String expected = "0 B";
    assertEquals(expected, result);
  }


  @Test
  public void testSizeDecimal1KIBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        true);
    String result = transferSizeCalculator.formatBytesToNiceString(KIBI_1);
    String expected = "1 KiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal10KIBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        true);
    String result = transferSizeCalculator.formatBytesToNiceString(KIBI_10);
    String expected = "10 KiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal100KIBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        true);
    String result = transferSizeCalculator.formatBytesToNiceString(KIBI_100);
    String expected = "100 KiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1MEBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        true);
    String result = transferSizeCalculator.formatBytesToNiceString(MEBI_1);
    String expected = "1 MiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal10MEBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        true);
    String result = transferSizeCalculator.formatBytesToNiceString(MEBI_10);
    String expected = "10 MiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal100MEBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        true);
    String result = transferSizeCalculator.formatBytesToNiceString(MEBI_100);
    String expected = "100 MiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1GIBBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        true);
    String result = transferSizeCalculator.formatBytesToNiceString(GIBI_1);
    String expected = "1 GiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal10GIBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        true);
    String result = transferSizeCalculator.formatBytesToNiceString(GIBI_10);
    String expected = "10 GiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal100GIBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        true);
    String result = transferSizeCalculator.formatBytesToNiceString(GIBI_100);
    String expected = "100 GiB";
    assertEquals(expected, result);
  }

  @Test
  public void testSizeDecimal1000GIBIBYTES() {
    TransferSizeCalculator transferSizeCalculator = new TransferSizeCalculator(BytePrefix.BINAR,
        true);
    String result = transferSizeCalculator.formatBytesToNiceString(GIBI_1000);
    String expected = "1000 GiB";
    assertEquals(expected, result);
  }

}