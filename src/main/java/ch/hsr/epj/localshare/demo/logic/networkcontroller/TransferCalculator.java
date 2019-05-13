package ch.hsr.epj.localshare.demo.logic.networkcontroller;


public class TransferCalculator {

  private static final long GIGABYTE = (long) Math.pow(10, 9);
  private static final long MEGABYTE = (long) Math.pow(10, 6);
  private static final long KILOBYTE = (long) Math.pow(10, 3);
  private static final String GB = "GB";
  private static final String MB = "MB";
  private static final String KB = "kB";
  private static final long GIBIBYTE = (long) Math.pow(2, 30);
  private static final long MEBIBYTE = (long) Math.pow(2, 20);
  private static final long KILIBYTE = (long) Math.pow(2, 10);
  private static final String GIB = "GiB";
  private static final String MIB = "MiB";
  private static final String KIB = "KiB";
  private static final String BYTE = "B";
  private static final String SECONDS = "/s";
  private static final String DELIMITER = " ";
  private BytePrefix bytePrefix;
  private boolean isSize;

  public TransferCalculator(BytePrefix bytePrefix, boolean isSize) {
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
        case BINARY:
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
        case BINARY:
          niceFormat = binaryFormatSeconds(bytes);
          break;
        default:
          niceFormat = "";
      }
    }

    return niceFormat;
  }

  private String binaryFormatSeconds(final long bps) {
    String niceFormat;

    if (bps >= GIBIBYTE) {
      niceFormat = buildString(calculation(bps, GIBIBYTE), GIB) + SECONDS;
    } else if (bps >= MEBIBYTE) {
      niceFormat = buildString(calculation(bps, MEBIBYTE), MIB) + SECONDS;
    } else if (bps >= KILIBYTE) {
      niceFormat = buildString(calculation(bps, KILIBYTE), KIB) + SECONDS;
    } else {
      niceFormat = buildString(bps, BYTE) + SECONDS;
    }

    return niceFormat;
  }

  private String decimalFormatSeconds(final long bps) {
    String niceFormat;

    if (bps >= GIGABYTE) {
      niceFormat = buildString(calculation(bps, GIGABYTE), GB) + SECONDS;
    } else if (bps >= MEGABYTE) {
      niceFormat = buildString(calculation(bps, MEGABYTE), MB) + SECONDS;
    } else if (bps >= KILOBYTE) {
      niceFormat = buildString(calculation(bps, KILOBYTE), KB) + SECONDS;
    } else {
      niceFormat = buildString(bps, BYTE) + SECONDS;
    }

    return niceFormat;
  }

  private String binaryFormatSize(final long bytes) {
    String niceFormat;

    if (bytes >= GIBIBYTE) {
      niceFormat = buildString(calculation(bytes, GIBIBYTE), GIB);
    } else if (bytes >= MEBIBYTE) {
      niceFormat = buildString(calculation(bytes, MEBIBYTE), MIB);
    } else if (bytes >= KILIBYTE) {
      niceFormat = buildString(calculation(bytes, KILIBYTE), KIB);
    } else {
      niceFormat = buildString(bytes, BYTE);
    }

    return niceFormat;
  }

  private String decimalFormatSize(final long bytes) {
    String niceFormat;

    if (bytes >= GIGABYTE) {
      niceFormat = buildString(calculation(bytes, GIGABYTE), GB);
    } else if (bytes >= MEGABYTE) {
      niceFormat = buildString(calculation(bytes, MEGABYTE), MB);
    } else if (bytes >= KILOBYTE) {
      niceFormat = buildString(calculation(bytes, KILOBYTE), KB);
    } else {
      niceFormat = buildString(bytes, BYTE);
    }

    return niceFormat;
  }

  private float calculation(final long bytes, final long unit) {
    if (unit < 0) {
      throw new IllegalArgumentException("Unit must be bigger 0");
    }

    return (float) bytes / unit;
  }

  private String buildString(final float size, final String unit) {
    String formatedSize = String.format("%.1f", size);
    return formatedSize + DELIMITER + unit;
  }

  public enum BytePrefix {
    DECIMAL,
    BINARY
  }

}
