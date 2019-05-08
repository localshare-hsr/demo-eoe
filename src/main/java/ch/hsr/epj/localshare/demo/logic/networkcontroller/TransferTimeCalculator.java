package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import java.util.concurrent.TimeUnit;

class TransferTimeCalculator {

  String formatSecond(final long millis) {
    long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
    long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
    String niceFormat;
    if (minutes == 0) {
      niceFormat = String.format("%d sec", seconds - TimeUnit.MINUTES.toSeconds(minutes));
    } else {
      niceFormat = String
          .format("%d min %d sec", minutes, seconds - TimeUnit.MINUTES.toSeconds(minutes));
    }

    return niceFormat;
  }
}
