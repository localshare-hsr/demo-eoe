package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TransferTimeCalculatorTest {

  @Test
  public void test0Seconds() {
    TransferTimeCalculator transferTimeCalculator = new TransferTimeCalculator();
    String result = transferTimeCalculator.formatSecond(0);
    String expected = "0 sec";
    assertEquals(expected, result);
  }

  @Test
  public void test30Seconds() {
    TransferTimeCalculator transferTimeCalculator = new TransferTimeCalculator();
    String result = transferTimeCalculator.formatSecond(30000);
    String expected = "30 sec";
    assertEquals(expected, result);
  }

  @Test
  public void test59Seconds() {
    TransferTimeCalculator transferTimeCalculator = new TransferTimeCalculator();
    String result = transferTimeCalculator.formatSecond(59000);
    String expected = "59 sec";
    assertEquals(expected, result);
  }

  @Test
  public void test60Seconds() {
    TransferTimeCalculator transferTimeCalculator = new TransferTimeCalculator();
    String result = transferTimeCalculator.formatSecond(60000);
    String expected = "1 min 0 sec";
    assertEquals(expected, result);
  }

  @Test
  public void test62Seconds() {
    TransferTimeCalculator transferTimeCalculator = new TransferTimeCalculator();
    String result = transferTimeCalculator.formatSecond(62000);
    String expected = "1 min 2 sec";
    assertEquals(expected, result);
  }

  @Test
  public void test300Seconds() {
    TransferTimeCalculator transferTimeCalculator = new TransferTimeCalculator();
    String result = transferTimeCalculator.formatSecond(300000);
    String expected = "5 min 0 sec";
    assertEquals(expected, result);
  }

  @Test
  public void test1337Seconds() {
    TransferTimeCalculator transferTimeCalculator = new TransferTimeCalculator();
    String result = transferTimeCalculator.formatSecond(1337000);
    String expected = "22 min 17 sec";
    assertEquals(expected, result);
  }

  @Test
  public void test3000Seconds() {
    TransferTimeCalculator transferTimeCalculator = new TransferTimeCalculator();
    String result = transferTimeCalculator.formatSecond(3000000);
    String expected = "50 min 0 sec";
    assertEquals(expected, result);
  }

}