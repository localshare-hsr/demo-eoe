package ch.hsr.epj.localshare.demo.logic;


import java.io.File;

public class StartupMethods {
    private static boolean firstLaunch = false;

    public static boolean startupCheck() {

        //Check if all Files available and everything is Set

        //check if config file exists
        ConfigManager configManager = ConfigManager.getInstance();

        File configFile = new File(configManager.getConfigPath());

        if (configFile.exists() && !configFile.isDirectory()) {
            firstLaunch = true;
        }

        return false;
    }

    public static void loadFriendlyName() {


    }

}
