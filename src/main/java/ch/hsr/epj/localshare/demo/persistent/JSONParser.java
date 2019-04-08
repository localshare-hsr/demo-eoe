package ch.hsr.epj.localshare.demo.persistent;

import ch.hsr.epj.localshare.demo.logic.ConfigManager;
import ch.hsr.epj.localshare.demo.logic.User;
import org.json.simple.*;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONParser {
    private JSONObject obj;
    User user;
    ConfigManager manager;

    public JSONParser() {
        obj = new JSONObject();
        user = User.getInstance();
        manager = ConfigManager.getInstance();
    }

    public void saveAllToJSON() {
        obj.put("friendly_name", user.getFriendlyName());
        obj.put("config_path", manager.getConfigPath());
        obj.put("download_path", manager.getDownloadPath());
    }

    public void saveFriendlyNameIntoJSON(String friendlyName) {
        obj.put("friendly_name", friendlyName);
    }

    public void saveConfigPathIntoJSON(String configPath) {
        obj.put("config_path", configPath);
    }

    public void saveDownloadPathINTOJSON(String downloadPath) {
        obj.put("download_path", downloadPath);
    }

    public void writeJSONToDisk() throws IOException {
        Files.createDirectories(Paths.get(manager.getConfigPath()));
        String savePath = manager.getConfigPath() + "\\config.json";

        System.out.println(savePath);

        FileWriter jsonConfig = new FileWriter(savePath);

        try {
            jsonConfig.write(obj.toJSONString());
            System.out.println(obj);
        } catch (Exception e) {
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

            String friendlyName = (String) jsonObject.get("friendly_name");
            String downloadPath = (String) jsonObject.get("download_path");

            user.setFriendlyName(friendlyName);
            manager.setDownloadPath(downloadPath);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {

        }


    }


}
