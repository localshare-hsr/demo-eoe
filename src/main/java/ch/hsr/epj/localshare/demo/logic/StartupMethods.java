package ch.hsr.epj.localshare.demo.logic;


import ch.hsr.epj.localshare.demo.persistent.JSONParser;

import java.io.File;

public class StartupMethods {
    private static boolean firstLaunch = true;

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
}
