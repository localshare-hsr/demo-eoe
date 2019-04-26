package ch.hsr.epj.localshare.demo.logic.environment;

import ch.hsr.epj.localshare.demo.persistence.JSONParser;
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
      configManager.setDownloadPath(getHomePath() + "\\LocalShare\\download\\");
      configManager.setConfigPath(getHomePath() + "\\LocalShare\\config\\");
    } else {
      configManager.setDownloadPath(getHomePath() + "/LocalShare/download/");
      configManager.setConfigPath(getHomePath() + "/LocalShare/config/");
    }

    File downloadFolder = new File(String.valueOf(configManager.getDownloadPath()));
    File localShareFolder;
    if (isWindows()) {
      localShareFolder = new File(getHomePath() + "\\LocalShare");
    } else {
      localShareFolder = new File(getHomePath() + "/LocalShare");
    }

    localShareFolder.mkdir();
    downloadFolder.mkdir();

    configFile = new File(configManager.getConfigPath() + "config.json");

    if (configFile.exists() && !configFile.isDirectory()) {
      firstLaunch = false;
    }
    return firstLaunch;
  }

  private static String getOsName() {
    if (os == null) {
      os = System.getProperty("os.name");
    }
    return os;
  }

  public static boolean isWindows() {
    return getOsName().startsWith("Windows");
  }

  private static boolean isLinux() {
    return getOsName().startsWith("Linux");
  }

  public static void loadConfig() {
    JSONParser parser = new JSONParser();
    parser.loadData();
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
