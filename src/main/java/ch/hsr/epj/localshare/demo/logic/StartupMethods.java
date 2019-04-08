package ch.hsr.epj.localshare.demo.logic;


import ch.hsr.epj.localshare.demo.persistent.JSONParser;

import java.io.File;

public class StartupMethods {
    private static boolean firstLaunch = true;
    private static String OS = null;

    public static boolean startupCheck() {
        //check if config file exists
        ConfigManager configManager = ConfigManager.getInstance();
        File configFile = new File(configManager.getConfigPath() + "\\config.json");

        if (configFile.exists() && !configFile.isDirectory()) {
            firstLaunch = false;
            JSONParser parser = new JSONParser();
            parser.loadData();
        }
        return firstLaunch;
    }

    public static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }

    public static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    public static boolean isLinux() {
        return getOsName().startsWith("Linux");
    }

    public static void setDefaultPath() {
        //set path according to OS
        ConfigManager configManager = ConfigManager.getInstance();

        if (isLinux()) {
            configManager.setConfigPath("");
            configManager.setDownloadPath("");
        }
        if (isWindows()) {
            configManager.setDownloadPath("C:\\Program Files\\LocalShare\\download");
            configManager.setConfigPath("C:\\Program Files\\LocalShare\\config");
        }
    }
}
