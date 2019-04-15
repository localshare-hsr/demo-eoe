package ch.hsr.epj.localshare.demo.logic;

public class ConfigManager {
  private static ConfigManager instance;
  private String downloadPath = "";
  private String configPath = "";

    private ConfigManager() {
    }

  public static ConfigManager getInstance() {
    if (ConfigManager.instance == null) {
      ConfigManager.instance = new ConfigManager();
    }
    return ConfigManager.instance;
  }

  public String getDownloadPath() {
    return downloadPath;
  }

  public void setDownloadPath(String s) {
    downloadPath = s;
  }

  public String getConfigPath() {
    return configPath;
  }

  public void setConfigPath(String s) {
    configPath = s;
  }
}
