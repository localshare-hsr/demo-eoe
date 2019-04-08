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
        configManager.setDownloadPath(getHomePath() + "\\LocalShare\\download");
        configManager.setConfigPath(getHomePath() + "\\LocalShare\\config");

    }

    public static String getHomePath() {
        String homePath = "";
        if (isWindows()) homePath = System.getenv("USERPROFILE");
        if (isLinux()) homePath = System.getProperty("user.home");
        return homePath;
    }
}
