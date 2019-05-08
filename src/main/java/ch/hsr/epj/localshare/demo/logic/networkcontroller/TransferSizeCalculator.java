package ch.hsr.epj.localshare.demo.logic.networkcontroller;


public class TransferSizeCalculator {

  private static final int GIGABYTE = (int) Math.pow(10, 9);
  private static final int MEGABYTE = (int) Math.pow(10, 6);
  private static final int KILOBYTE = (int) Math.pow(10, 3);
  private static final String GB = "GB";
  private static final String MB = "MB";
  private static final String KB = "kB";
  private static final int GIBIBYTE = (int) Math.pow(2, 30);
  private static final int MEBIBYTE = (int) Math.pow(2, 20);
  private static final int KILIBYTE = (int) Math.pow(2, 10);
  private static final String GIB = "GiB";
  private static final String MIB = "MiB";
  private static final String KIB = "KiB";
  private static final String BYTE = "B";
  private static final String SECONDS = "/s";
  private static final String DELIMITER = " ";
  private BytePrefix bytePrefix;
  private boolean isSize;

  public TransferSizeCalculator(BytePrefix bytePrefix, boolean isSize) {
    this.bytePrefix = bytePrefix;
    this.isSize = isSize;
  }

  public String formatBytesToNiceString(final long bytes) {
    String niceFormat;

    if (isSize) {
      switch (bytePrefix) {
        case DECIMAL:
          niceFormat = decimalFormatSize(bytes);
          break;
        case BINAR:
          niceFormat = binaryFormatSize(bytes);
          break;
        default:
          niceFormat = "";
      }
    } else {
      switch (bytePrefix) {
        case DECIMAL:
          niceFormat = decimalFormatSeconds(bytes);
          break;
        case BINAR:
          niceFormat = binaryFormatSeconds(bytes);
          break;
        default:
          niceFormat = "";
      }
    }

    return niceFormat;
  }

  private String binaryFormatSeconds(final long bytes) {
    String niceFormat;

    if (bytes >= GIBIBYTE) {
      niceFormat = (bytes / GIBIBYTE) + DELIMITER + GIB + SECONDS;
    } else if (bytes >= MEBIBYTE) {
      niceFormat = (bytes / MEBIBYTE) + DELIMITER + MIB + SECONDS;
    } else if (bytes >= KILIBYTE) {
      niceFormat = (bytes / KILIBYTE) + DELIMITER + KIB + SECONDS;
    } else {
      niceFormat = bytes + DELIMITER + BYTE + SECONDS;
    }

    return niceFormat;
  }

  private String decimalFormatSeconds(final long bps) {
    String niceFormat;

    if (bps >= GIGABYTE) {
      niceFormat = (bps / GIGABYTE) + DELIMITER + GB + SECONDS;
    } else if (bps >= MEGABYTE) {
      niceFormat = (bps / MEGABYTE) + DELIMITER + MB + SECONDS;
    } else if (bps >= KILOBYTE) {
      niceFormat = (bps / KILOBYTE) + DELIMITER + KB + SECONDS;
    } else {
      niceFormat = bps + DELIMITER + BYTE + SECONDS;
    }

    return niceFormat;
  }

  private String binaryFormatSize(final long bytes) {
    String niceFormat;

    if (bytes >= GIBIBYTE) {
      niceFormat = (bytes / GIBIBYTE) + DELIMITER + GIB;
    } else if (bytes >= MEBIBYTE) {
      niceFormat = (bytes / MEBIBYTE) + DELIMITER + MIB;
    } else if (bytes >= KILIBYTE) {
      niceFormat = (bytes / KILIBYTE) + DELIMITER + KIB;
    } else {
      niceFormat = bytes + DELIMITER + BYTE;
    }

    return niceFormat;
  }

  private String decimalFormatSize(final long bps) {
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

  public enum BytePrefix {
    DECIMAL,
    BINAR
  }

}
