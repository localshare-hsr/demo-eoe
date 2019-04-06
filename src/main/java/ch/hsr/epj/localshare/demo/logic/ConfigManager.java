package ch.hsr.epj.localshare.demo.logic;

public class ConfigManager {
    private static ConfigManager instance;

    private ConfigManager() {
    }

    private String downloadPath = "C:\\Program Files\\LocalShare\\download";
    private String configPath = "C:\\Program Files\\LocalShare\\config";

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
