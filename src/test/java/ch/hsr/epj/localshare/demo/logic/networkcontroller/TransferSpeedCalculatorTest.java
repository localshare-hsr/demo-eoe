package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TransferSpeedCalculatorTest {

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
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(
        BytePrefix.DECIMAL);
    String result = transferSpeedCalculator.formatBytesPerSecond(BYTE_0);
    String expected = "0 B/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1BYTE() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(
        BytePrefix.DECIMAL);
    String result = transferSpeedCalculator.formatBytesPerSecond(BYTE_1);
    String expected = "1 B/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10BYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(
        BytePrefix.DECIMAL);
    String result = transferSpeedCalculator.formatBytesPerSecond(BYTE_10);
    String expected = "10 B/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100BYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(
        BytePrefix.DECIMAL);
    String result = transferSpeedCalculator.formatBytesPerSecond(BYTE_100);
    String expected = "100 B/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1KILOBYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(
        BytePrefix.DECIMAL);
    String result = transferSpeedCalculator.formatBytesPerSecond(KILO_1);
    String expected = "1 kB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10KILOBYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(
        BytePrefix.DECIMAL);
    String result = transferSpeedCalculator.formatBytesPerSecond(KILO_10);
    String expected = "10 kB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100KILOBYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(
        BytePrefix.DECIMAL);
    String result = transferSpeedCalculator.formatBytesPerSecond(KILO_100);
    String expected = "100 kB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1MEGABYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(
        BytePrefix.DECIMAL);
    String result = transferSpeedCalculator.formatBytesPerSecond(MEGA_1);
    String expected = "1 MB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10MEGABYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(
        BytePrefix.DECIMAL);
    String result = transferSpeedCalculator.formatBytesPerSecond(MEGA_10);
    String expected = "10 MB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100MEGABYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(
        BytePrefix.DECIMAL);
    String result = transferSpeedCalculator.formatBytesPerSecond(MEGA_100);
    String expected = "100 MB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1GIGABYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(
        BytePrefix.DECIMAL);
    String result = transferSpeedCalculator.formatBytesPerSecond(GIGA_1);
    String expected = "1 GB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10GIGABYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(
        BytePrefix.DECIMAL);
    String result = transferSpeedCalculator.formatBytesPerSecond(GIGA_10);
    String expected = "10 GB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100GIGABYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(
        BytePrefix.DECIMAL);
    String result = transferSpeedCalculator.formatBytesPerSecond(GIGA_100);
    String expected = "100 GB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1000GIGABYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(
        BytePrefix.DECIMAL);
    String result = transferSpeedCalculator.formatBytesPerSecond(GIGA_1000);
    String expected = "1000 GB/s";
    assertEquals(expected, result);
  }


  @Test
  public void testDecimal0BYTEBINARY() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(BytePrefix.BINAR);
    String result = transferSpeedCalculator.formatBytesPerSecond(BYTE_0);
    String expected = "0 B/s";
    assertEquals(expected, result);
  }


  @Test
  public void testDecimal1KIBIBYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(BytePrefix.BINAR);
    String result = transferSpeedCalculator.formatBytesPerSecond(KIBI_1);
    String expected = "1 KiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10KIBIBYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(BytePrefix.BINAR);
    String result = transferSpeedCalculator.formatBytesPerSecond(KIBI_10);
    String expected = "10 KiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100KIBIBYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(BytePrefix.BINAR);
    String result = transferSpeedCalculator.formatBytesPerSecond(KIBI_100);
    String expected = "100 KiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1MEBIBYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(BytePrefix.BINAR);
    String result = transferSpeedCalculator.formatBytesPerSecond(MEBI_1);
    String expected = "1 MiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10MEBIBYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(BytePrefix.BINAR);
    String result = transferSpeedCalculator.formatBytesPerSecond(MEBI_10);
    String expected = "10 MiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100MEBIBYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(BytePrefix.BINAR);
    String result = transferSpeedCalculator.formatBytesPerSecond(MEBI_100);
    String expected = "100 MiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1GIBBIBYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(BytePrefix.BINAR);
    String result = transferSpeedCalculator.formatBytesPerSecond(GIBI_1);
    String expected = "1 GiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal10GIBIBYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(BytePrefix.BINAR);
    String result = transferSpeedCalculator.formatBytesPerSecond(GIBI_10);
    String expected = "10 GiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal100GIBIBYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(BytePrefix.BINAR);
    String result = transferSpeedCalculator.formatBytesPerSecond(GIBI_100);
    String expected = "100 GiB/s";
    assertEquals(expected, result);
  }

  @Test
  public void testDecimal1000GIBIBYTES() {
    TransferSpeedCalculator transferSpeedCalculator = new TransferSpeedCalculator(BytePrefix.BINAR);
    String result = transferSpeedCalculator.formatBytesPerSecond(GIBI_1000);
    String expected = "1000 GiB/s";
    assertEquals(expected, result);
  }

}