package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.logic.environment.ConfigManager;
import ch.hsr.epj.localshare.demo.logic.environment.User;
import ch.hsr.epj.localshare.demo.persistence.JSONParser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class StartupViewController implements Initializable {

  private final DirectoryChooser directoryChooser = new DirectoryChooser();
  private ConfigManager configManager = ConfigManager.getInstance();
  @FXML
  private TextField friendlyNameText;
  @FXML
  private Label defaultConfigLabel;
  @FXML
  private Label defaultDownloadLabel;

  @FXML
  private Button finishButton;

  public void changeFriendlyName(String friendlyName) {
    if (friendlyName.equals("")) {
      finishButton.setDisable(true);
    } else {
      User user = User.getInstance();
      user.setFriendlyName(friendlyName);
      finishButton.setDisable(false);
    }
  }

  @FXML
  private void handleFinishButtonClick(ActionEvent event) throws IOException {
    JSONParser parser = new JSONParser();
    parser.saveAllToJSON();
    parser.writeJSONToDisk();

    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("fxml/MainWindowView.fxml")));

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setTitle("GUI Prototype");
    stage.setScene(new Scene(root, 800, 600));
    stage.show();
  }

  @FXML
  private void handleChangeDownloadButtonClicked(ActionEvent event) {
    Node node = (Node) event.getSource();
    final Stage stage = (Stage) node.getScene().getWindow();

    File dir = directoryChooser.showDialog(stage);
    if (dir != null) {
      configManager.setDownloadPath(dir.getAbsolutePath());
      defaultDownloadLabel.setText(configManager.getDownloadPath());
    } else {
      defaultDownloadLabel.setText(configManager.getDownloadPath());
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    defaultConfigLabel.setText(String.valueOf(configManager.getConfigPath()));
    defaultDownloadLabel.setText(String.valueOf(configManager.getDownloadPath()));

    friendlyNameText
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> changeFriendlyName(newValue)
        );
  }
}
