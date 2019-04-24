package ch.hsr.epj.localshare.demo.network.transfer.utils;

import ch.hsr.epj.localshare.demo.gui.data.Transfer;
import ch.hsr.epj.localshare.demo.network.transfer.server.DownloadFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MetaParser {

  private static final Logger logger = Logger.getLogger(MetaParser.class.getName());

  private MetaParser() {
  }

  public static List<Transfer> parse(final File jsonFile) throws FileNotFoundException {

    List<Transfer> transferList = new LinkedList<>();

    JSONParser parser = new JSONParser();

    try {
      JSONObject jsonFileObject = (JSONObject) parser.parse(new FileReader(jsonFile));
      String friendlyName = (String) jsonFileObject.get("friendlyName");

      JSONArray files = (JSONArray) jsonFileObject.get("files");

      for (JSONObject jsonFileEntryObject : (Iterable<JSONObject>) files) {

        String name = (String) jsonFileEntryObject.get("name");
        long size = Long.parseLong((String) jsonFileEntryObject.get("size"));
        String url = (String) jsonFileEntryObject.get("url");
        transferList.add(new Transfer(friendlyName, size, name, new URL(url)));

      }


    } catch (IOException | ParseException e) {
      logger.log(Level.WARNING, "Unable to parse JSON", e);
    }

    return transferList;

  }

  public static File generateMeta(final List<DownloadFile> downloadableFiles) {
    JSONObject jsonFileObject = new JSONObject();

    jsonFileObject.put("friendlyName", downloadableFiles.get(0).getOwnerFriendlyName());

    JSONArray jsonFileArray = new JSONArray();
    for (DownloadFile df : downloadableFiles) {
      JSONObject jsonArrayEntry = new JSONObject();
      jsonArrayEntry.put("name", df.getFileName());
      jsonArrayEntry.put("size", String.valueOf(df.getFileSize()));
      jsonArrayEntry.put("url", df.getFileURL());
      jsonFileArray.add(jsonArrayEntry);
    }

    jsonFileObject.put("files", jsonFileArray);

    File jsonFile = new File("index.json");
    try (FileWriter fw = new FileWriter(jsonFile)) {
      fw.write(jsonFileObject.toJSONString());
      fw.flush();
    } catch (IOException e) {
      logger.log(Level.WARNING, "Unable to write JSON", e);
    }
    return jsonFile;
  }

}
