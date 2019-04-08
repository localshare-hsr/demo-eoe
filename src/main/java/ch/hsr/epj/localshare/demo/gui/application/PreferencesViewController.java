package ch.hsr.epj.localshare.demo.gui.application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import ch.hsr.epj.localshare.demo.logic.ConfigManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class PreferencesViewController implements Initializable {

    ConfigManager configManager = ConfigManager.getInstance();

    @FXML
    private Text configPath;

    @FXML
    private Text downloadPath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configPath.setText(configManager.getConfigPath());
        downloadPath.setText(configManager.getDownloadPath());
    }



    final DirectoryChooser directoryChooser = new DirectoryChooser();
    // configuringDirectoryChooser(directoryChooser);

    @FXML
    private void handleChangeConfigButtonAction(ActionEvent event) {

        Node node = (Node) event.getSource();
        final Stage stage = (Stage) node.getScene().getWindow();

        File dir = directoryChooser.showDialog(stage);
        if (dir != null) {
            configManager.setConfigPath(dir.getAbsolutePath());
            configPath.setText(configManager.getConfigPath());
        } else {
            configPath.setText(configManager.getConfigPath());
        }
    }

    @FXML
    private void handleChangeDownloadButtonAction(ActionEvent event) {

        Node node = (Node) event.getSource();
        final Stage stage = (Stage) node.getScene().getWindow();

        File dir = directoryChooser.showDialog(stage);
        if (dir != null) {
            configManager.setDownloadPath(dir.getAbsolutePath());
            downloadPath.setText(configManager.getDownloadPath());
        } else {
            downloadPath.setText(configManager.getDownloadPath());
        }
    }



    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        // Set title for DirectoryChooser
        directoryChooser.setTitle("Select Some Directories");
        //Set Initial Directory
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }


}

