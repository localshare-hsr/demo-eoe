package ch.hsr.epj.localshare.demo.logic.environment;

import ch.hsr.epj.localshare.demo.persistence.JSONParser;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartupMethods {

  private static final Logger logger = Logger.getLogger(StartupMethods.class.getName());

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

    boolean existsLocalShareFolder = localShareFolder.mkdir();
    if (!existsLocalShareFolder) {
      logger.log(Level.WARNING, "Could not create folder LocalShare");
    }
    boolean existsDownloadFolder = downloadFolder.mkdir();
    if (!existsDownloadFolder) {
      logger.log(Level.WARNING, "Could not create folder download");
    }

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
    try {
      JSONParser parser = new JSONParser(ConfigManager.getInstance().getConfigPath());
      parser.loadData();
      ConfigManager.getInstance().setDownloadPath(parser.getDownloadPath());
      ConfigManager.getInstance().setConfigPath(parser.getConfigPath());
      User.getInstance().setFriendlyName(parser.getFriendlyName());
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Could not safe config file");
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
