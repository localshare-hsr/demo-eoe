package ch.hsr.epj.localshare.demo.logic.networkcontroller;

enum BytePrefix {
  DECIMAL,
  BINAR
}

class TransferSpeedCalculator {

  private static final int GIGABYTE = (int) Math.pow(10, 9);
  private static final int MEGABYTE = (int) Math.pow(10, 6);
  private static final int KILOBYTE = (int) Math.pow(10, 3);
  private static final String GB = "GB/s";
  private static final String MB = "MB/s";
  private static final String KB = "kB/s";

  private static final int GIBIBYTE = (int) Math.pow(2, 30);
  private static final int MEBIBYTE = (int) Math.pow(2, 20);
  private static final int KILIBYTE = (int) Math.pow(2, 10);
  private static final String GIB = "GiB/s";
  private static final String MIB = "MiB/s";
  private static final String KIB = "KiB/s";

  private static final String BYTE = "B/s";

  private static final String DELIMITER = " ";

  private BytePrefix bytePrefix;

  TransferSpeedCalculator(BytePrefix bytePrefix) {
    this.bytePrefix = bytePrefix;
  }


  String formatBytesPerSecond(final long bps) {
    String niceFormat;
    switch (bytePrefix) {
      case DECIMAL:
        niceFormat = decimalFormat(bps);
        break;
      case BINAR:
        niceFormat = binaryFormat(bps);
        break;
      default:
        niceFormat = "";
    }

    return niceFormat;
  }

  private String binaryFormat(final long bps) {
    String niceFormat;

    if (bps >= GIBIBYTE) {
      niceFormat = (bps / GIBIBYTE) + DELIMITER + GIB;
    } else if (bps >= MEBIBYTE) {
      niceFormat = (bps / MEBIBYTE) + DELIMITER + MIB;
    } else if (bps >= KILIBYTE) {
      niceFormat = (bps / KILIBYTE) + DELIMITER + KIB;
    } else {
      niceFormat = bps + DELIMITER + BYTE;
    }

    return niceFormat;
  }

  private String decimalFormat(final long bps) {
    String niceFormat;

    if (bps >= GIGABYTE) {
      niceFormat = (bps / GIGABYTE) + DELIMITER + GB;
    } else if (bps >= MEGABYTE) {
      niceFormat = (bps / MEGABYTE) + DELIMITER + MB;
    } else if (bps >= KILOBYTE) {
      niceFormat = (bps / KILOBYTE) + DELIMITER + KB;
    } else {
      niceFormat = bps + DELIMITER + BYTE;
    }

    return niceFormat;
  }

}
