package ch.hsr.epj.localshare.demo.persistent;

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

  String jsonFriendlyName = "friendly_name";
  String jsonConfigPath = "config_path";
  String jsonDownloadPath = "download_path";
  User user;
  ConfigManager manager;
  private JSONObject obj;

  public JSONParser() {
    obj = new JSONObject();
    user = User.getInstance();
    manager = ConfigManager.getInstance();
  }

  public void saveAllToJSON() {
    obj.put(jsonFriendlyName, user.getFriendlyName());
    obj.put(jsonConfigPath, manager.getConfigPath());
    obj.put(jsonDownloadPath, manager.getDownloadPath());
  }

  public void writeJSONToDisk() throws IOException {
    Files.createDirectories(Paths.get(manager.getConfigPath()));
    String savedFileName;

    if (StartupMethods.isWindows()) {
      savedFileName = manager.getConfigPath() + "\\config.json";
    } else {
      savedFileName = manager.getConfigPath() + "/config.json";
    }

    try (FileWriter jsonConfig = new FileWriter(savedFileName)) {
      jsonConfig.write(obj.toJSONString());
    } catch (IOException e) {
      logger.log(Level.WARNING, "Unable to write JSON file", e);
    }
  }

  public void loadData() {
    org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
    try {
      JSONObject jsonObject;
      if (StartupMethods.isWindows()) {
        jsonObject = (JSONObject) parser
            .parse(new FileReader(manager.getConfigPath() + "\\config.json"));
      } else {
        jsonObject = (JSONObject) parser
            .parse(new FileReader(manager.getConfigPath() + "/config.json"));
      }

      String friendlyName = (String) jsonObject.get(jsonFriendlyName);
      String downloadPath = (String) jsonObject.get(jsonDownloadPath);
      String configPath = (String) jsonObject.get(jsonConfigPath);

      user.setFriendlyName(friendlyName);
      manager.setDownloadPath(downloadPath);
      manager.setConfigPath(configPath);


    } catch (IOException | ParseException e) {
      logger.log(Level.WARNING, "Unable to load JSON file", e);
    }
  }
}
