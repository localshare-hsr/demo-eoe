package ch.hsr.epj.localshare.demo.persistent;

import ch.hsr.epj.localshare.demo.logic.ConfigManager;
import ch.hsr.epj.localshare.demo.logic.StartupMethods;
import ch.hsr.epj.localshare.demo.logic.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONParser {
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

  public void saveFriendlyNameIntoJSON(String friendlyName) {
    obj.put(jsonFriendlyName, friendlyName);
  }

  public void saveConfigPathIntoJSON(String configPath) {
    obj.put(jsonConfigPath, configPath);
  }

  public void saveDownloadPathINTOJSON(String downloadPath) {
    obj.put(jsonDownloadPath, downloadPath);
  }

  public void writeJSONToDisk() throws IOException {
    Files.createDirectories(Paths.get(manager.getConfigPath()));
    String savedFileName;

    if (StartupMethods.isWindows()) {
      savedFileName = manager.getConfigPath() + "\\config.json";
    } else {
      savedFileName = manager.getConfigPath() + "/config.json";
    }

    FileWriter jsonConfig = new FileWriter(savedFileName);

    try {
      jsonConfig.write(obj.toJSONString());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      jsonConfig.flush();
      jsonConfig.close();
    }
  }

  public void loadData() {
    org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
    try {
      Object obj = parser.parse(new FileReader(manager.getConfigPath() + "\\config.json"));
      JSONObject jsonObject = (JSONObject) obj;

      String friendlyName = (String) jsonObject.get(jsonFriendlyName);
      String downloadPath = (String) jsonObject.get(jsonDownloadPath);

      user.setFriendlyName(friendlyName);
      manager.setDownloadPath(downloadPath);

    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
  }
}
