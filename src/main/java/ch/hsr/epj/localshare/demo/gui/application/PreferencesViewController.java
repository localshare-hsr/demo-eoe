package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.logic.environment.ConfigManager;
import ch.hsr.epj.localshare.demo.logic.environment.StartupMethods;
import ch.hsr.epj.localshare.demo.persistent.JSONParser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class PreferencesViewController implements Initializable {

  private final DirectoryChooser directoryChooser = new DirectoryChooser();
  private ConfigManager configManager = ConfigManager.getInstance();
  @FXML
  private Text configPath;
  @FXML
  private Text downloadPath;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    configPath.setText(configManager.getConfigPath());
    downloadPath.setText(configManager.getDownloadPath());
  }

  @FXML
  private void handleChangeDownloadButtonAction(ActionEvent event) {

    Node node = (Node) event.getSource();
    final Stage stage = (Stage) node.getScene().getWindow();

    File dir = directoryChooser.showDialog(stage);
    if (dir != null) {
      if (StartupMethods.isWindows()) {
        configManager.setDownloadPath(dir.getAbsolutePath() + "\\");
      }
      if (!StartupMethods.isWindows()) {
        configManager.setDownloadPath(dir.getAbsolutePath() + "/");
      }

      JSONParser parser = new JSONParser();
      parser.saveAllToJSON();
      try {
        parser.writeJSONToDisk();
      } catch (IOException e) {
        e.printStackTrace();
      }

      downloadPath.setText(configManager.getDownloadPath());
    } else {
      downloadPath.setText(configManager.getDownloadPath());
    }
  }
}
