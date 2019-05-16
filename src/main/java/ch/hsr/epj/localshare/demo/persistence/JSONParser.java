package ch.hsr.epj.localshare.demo.persistence;

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
  private static final String JSON_CONFIG_FILE = "config.json";
  private static final String JSON_FRIENDLY_NAME = "friendly_name";
  private static final String JSON_CONFIG_PATH = "config_path";
  private static final String JSON_DOWNLOAD_PATH = "download_path";

  private JSONObject jsonObject;
  private String directoryPath;
  private String friendlyName;
  private String downloadPath;
  private String configPath;


  public JSONParser(String directoryPath) {
    jsonObject = new JSONObject();
    this.directoryPath = directoryPath;
    this.friendlyName = "";
    this.downloadPath = "";
    this.configPath = directoryPath;

    if (directoryPath == null) {
      throw new IllegalArgumentException("Invalid argument");
    }

    try {
      Files.createDirectories(Paths.get(directoryPath));
    } catch (IOException e) {
      logger.log(Level.INFO, "File does already exist");
    }
  }

  public JSONParser(String directoryPath, String friendlyName, String downloadPath) {
    this(directoryPath);
    this.friendlyName = friendlyName;
    this.downloadPath = downloadPath;

    if (friendlyName == null || friendlyName.equals("") || downloadPath == null || downloadPath
        .equals("")) {
      throw new IllegalArgumentException("Invalid argument");
    }
  }

  public boolean writeData() {
    boolean success;

    if (friendlyName == null || friendlyName.equals("")
        || downloadPath == null || downloadPath.equals("")
        || configPath == null || configPath.equals("")) {
      success = false;
      return success;
    }

    jsonObject.put(JSON_FRIENDLY_NAME, friendlyName);
    jsonObject.put(JSON_CONFIG_PATH, configPath);
    jsonObject.put(JSON_DOWNLOAD_PATH, downloadPath);

    try (FileWriter fileWriter = new FileWriter(directoryPath + JSON_CONFIG_FILE)) {
      fileWriter.write(jsonObject.toJSONString());
      success = true;
    } catch (IOException e) {
      success = false;
      logger.log(Level.WARNING, "Unable to write JSON file", e);
    }

    return success;
  }

  public boolean loadData() {
    boolean success;
    org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
    try {
      JSONObject parsedJSONData = (JSONObject) parser
          .parse(new FileReader(directoryPath + JSON_CONFIG_FILE));

      friendlyName = (String) parsedJSONData.get(JSON_FRIENDLY_NAME);
      downloadPath = (String) parsedJSONData.get(JSON_DOWNLOAD_PATH);
      configPath = (String) parsedJSONData.get(JSON_CONFIG_PATH);

      success = friendlyName != null && !friendlyName.equals("")
          && downloadPath != null && !downloadPath.equals("")
          && configPath != null && !configPath.equals("");

    } catch (IOException | ParseException e) {
      success = false;
      logger.log(Level.WARNING, "Unable to load JSON file", e);
    }

    return success;
  }

  public String getFriendlyName() {
    return friendlyName;
  }

  public String getDownloadPath() {
    return downloadPath;
  }

  public String getConfigPath() {
    return configPath;
  }
}
