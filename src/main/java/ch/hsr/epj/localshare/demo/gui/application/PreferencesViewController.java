package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.logic.environment.ConfigManager;
import ch.hsr.epj.localshare.demo.logic.environment.StartupMethods;
import ch.hsr.epj.localshare.demo.logic.environment.User;
import ch.hsr.epj.localshare.demo.persistence.JSONParser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class PreferencesViewController implements Initializable {

  private static final Logger logger = Logger.getLogger(PreferencesViewController.class.getName());

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

      try {
        String config = ConfigManager.getInstance().getConfigPath();
        String download = ConfigManager.getInstance().getDownloadPath();
        String friendlyName = User.getInstance().getFriendlyName();
        JSONParser parser = new JSONParser(config, friendlyName, download);
        parser.writeData();
      } catch (IOException e) {
        logger.log(Level.WARNING, "Could not write JSON config to disk");
      }

      downloadPath.setText(configManager.getDownloadPath());
    } else {
      downloadPath.setText(configManager.getDownloadPath());
    }
  }
}
