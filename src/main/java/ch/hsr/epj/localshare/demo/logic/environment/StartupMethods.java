package ch.hsr.epj.localshare.demo.logic.environment;

import ch.hsr.epj.localshare.demo.persistence.JSONParser;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartupMethods {

  private static final Logger logger = Logger.getLogger(StartupMethods.class.getName());

  private String folderLocalShare;
  private String folderConfig;
  private String folderDownload;

  public StartupMethods(OSDetector osDetector) {
    this.folderLocalShare = osDetector.getOSSpecificLocalShareDirectory();
    this.folderConfig = osDetector.getOSSpecificConfigDirectory();
    this.folderDownload = osDetector.getOSSpecificDownloadDirectory();

    initConfigManager();
  }

  public boolean startupCheck() {
    File localShareFolder = new File(folderLocalShare);
    File configFolder = new File(folderConfig);
    File downloadFolder = new File(folderDownload);

    boolean existsLocalShareFolder = localShareFolder.mkdir();
    if (existsLocalShareFolder) {
      logger.log(Level.INFO, "Folder LocalShare exists");
    }

    boolean existsConfigFolder = configFolder.mkdir();
    if (existsConfigFolder) {
      logger.log(Level.WARNING, "Folder config exists");
    }

    boolean existsDownloadFolder = downloadFolder.mkdir();
    if (existsDownloadFolder) {
      logger.log(Level.WARNING, "Folder download exists");
    }

    File configFile = new File(folderConfig + "config.json");

    return !configFile.exists() || configFile.isDirectory();
  }

  public boolean loadConfig() {
    boolean success;
    JSONParser parser = new JSONParser(ConfigManager.getInstance().getConfigPath());
    success = parser.loadData();
    ConfigManager.getInstance().setDownloadPath(parser.getDownloadPath());
    ConfigManager.getInstance().setConfigPath(parser.getConfigPath());
    User.getInstance().setFriendlyName(parser.getFriendlyName());
    return success;
  }

  private void initConfigManager() {
    ConfigManager configManager = ConfigManager.getInstance();
    configManager.setConfigPath(folderConfig);
    configManager.setDownloadPath(folderDownload);
  }
}
