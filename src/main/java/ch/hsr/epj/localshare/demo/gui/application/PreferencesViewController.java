package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.logic.environment.ConfigManager;
import ch.hsr.epj.localshare.demo.logic.environment.OSDetector;
import ch.hsr.epj.localshare.demo.logic.environment.User;
import ch.hsr.epj.localshare.demo.persistence.JSONParser;
import java.io.File;
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
    OSDetector osDetector = new OSDetector();

    Node node = (Node) event.getSource();
    final Stage stage = (Stage) node.getScene().getWindow();

    File dir = directoryChooser.showDialog(stage);
    if (dir != null) {
      if (osDetector.isWindows()) {
        configManager.setDownloadPath(dir.getAbsolutePath() + "\\");
      }
      if (!osDetector.isWindows()) {
        configManager.setDownloadPath(dir.getAbsolutePath() + "/");
      }

      String config = ConfigManager.getInstance().getConfigPath();
      String download = ConfigManager.getInstance().getDownloadPath();
      String friendlyName = User.getInstance().getFriendlyName();
      JSONParser parser = new JSONParser(config, friendlyName, download);
      parser.writeData();

      downloadPath.setText(configManager.getDownloadPath());
    } else {
      downloadPath.setText(configManager.getDownloadPath());
    }
  }
}
