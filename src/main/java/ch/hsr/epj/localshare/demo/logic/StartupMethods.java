package ch.hsr.epj.localshare.demo.logic;

import ch.hsr.epj.localshare.demo.persistent.JSONParser;
import java.io.File;

public class StartupMethods {

  private static boolean firstLaunch = true;
  private static String os = null;

  private StartupMethods() {
  }

  public static boolean startupCheck() {
    ConfigManager configManager = ConfigManager.getInstance();
    File configFile;

    if (isWindows()) {
      configFile = new File(configManager.getConfigPath() + "\\config.json");
    } else {
      configFile = new File(configManager.getConfigPath() + "/config.json");
    }

    if (configFile.exists() && !configFile.isDirectory()) {
      firstLaunch = false;
      JSONParser parser = new JSONParser();
      parser.loadData();
    }
    return firstLaunch;
  }

  public static String getOsName() {
    if (os == null) {
      os = System.getProperty("os.name");
    }
    return os;
  }

  public static boolean isWindows() {
    return getOsName().startsWith("Windows");
  }

  public static boolean isLinux() {
    return getOsName().startsWith("Linux");
  }

  public static void setDefaultPath() {
    ConfigManager configManager = ConfigManager.getInstance();

    if (isWindows()) {
      configManager.setDownloadPath(getHomePath() + "\\LocalShare\\download");
      configManager.setConfigPath(getHomePath() + "\\LocalShare\\config");
    } else {
      configManager.setDownloadPath(getHomePath() + "/LocalShare/download");
      configManager.setConfigPath(getHomePath() + "/LocalShare/config");
    }
  }

  private static String getHomePath() {
    String homePath = "";
    if (isWindows()) {
      homePath = System.getenv("USERPROFILE");
    }
    if (isLinux()) {
      homePath = System.getProperty("user.home");
    }
    return homePath;
  }
}
