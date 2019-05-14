package ch.hsr.epj.localshare.demo.persistence;

import ch.hsr.epj.localshare.demo.logic.environment.ConfigManager;
import ch.hsr.epj.localshare.demo.logic.environment.StartupMethods;
import ch.hsr.epj.localshare.demo.logic.environment.User;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class JSONParser {

  private static final Logger logger = Logger.getLogger(JSONParser.class.getName());

  private String jsonFriendlyName = "friendly_name";
  private String jsonConfigPath = "config_path";
  private String jsonDownloadPath = "download_path";
  private User user;
  private ConfigManager manager;
  private JSONObject jsonObject;

  public JSONParser() {
    jsonObject = new JSONObject();
    user = User.getInstance();
    manager = ConfigManager.getInstance();
  }

  public void saveAllToJSON() {
    jsonObject.put(jsonFriendlyName, user.getFriendlyName());
    jsonObject.put(jsonConfigPath, manager.getConfigPath());
    jsonObject.put(jsonDownloadPath, manager.getDownloadPath());
  }

  public void writeJSONToDisk() throws IOException {
    Files.createDirectories(Paths.get(manager.getConfigPath()));
    String savedFileName = getOSConfigPath();

    try (FileWriter jsonConfig = new FileWriter(savedFileName)) {
      jsonConfig.write(jsonObject.toJSONString());
    } catch (IOException e) {
      logger.log(Level.WARNING, "Unable to write JSON file", e);
    }
  }

  public void loadData() {
    org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
    try {
      JSONObject parsedJSONData = (JSONObject) parser.parse(new FileReader(getOSConfigPath()));

      String friendlyName = (String) parsedJSONData.get(jsonFriendlyName);
      String downloadPath = (String) parsedJSONData.get(jsonDownloadPath);
      String configPath = (String) parsedJSONData.get(jsonConfigPath);

      user.setFriendlyName(friendlyName);
      manager.setDownloadPath(downloadPath);
      manager.setConfigPath(configPath);


    } catch (IOException | ParseException e) {
      logger.log(Level.WARNING, "Unable to load JSON file", e);
    }
  }

  private String getOSConfigPath() {
    String configPath;
    if (StartupMethods.isWindows()) {
      configPath = manager.getConfigPath() + "\\config.json";
    } else {
      configPath = manager.getConfigPath() + "/config.json";
    }
    return configPath;
  }
}
