package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.logic.ConfigManager;
import ch.hsr.epj.localshare.demo.logic.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StartupViewController implements Initializable {

    @FXML
    private TextField friendlyNameText;

    @FXML
    private Label defaultConfigLabel;

    @FXML
    private Label defaultDownloadLabel;

    final DirectoryChooser directoryChooser = new DirectoryChooser();

    ConfigManager configManager = ConfigManager.getInstance();


    public StartupViewController() {

    }

    public void changeFriendlyName(String friendlyName) {
        User user = User.getInstance();
        user.setFriendlyName(friendlyName);

        System.out.println(user.getFriendlyName());
    }

    @FXML
    private void handleFinishButtonClick(ActionEvent event) throws IOException {

        // FIRST SAVE CONFIG PERSISTENT


        // LoadMainScene
        Parent root =
                FXMLLoader.load(Objects
                        .requireNonNull(getClass().getClassLoader().getResource("fxml/MainWindowView.fxml")));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("GUI Prototype");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    @FXML
    private void handleChangeConfigButtonClicked(ActionEvent event) {

        Node node = (Node) event.getSource();
        final Stage stage = (Stage) node.getScene().getWindow();

        File dir = directoryChooser.showDialog(stage);
        if (dir != null) {
            configManager.setConfigPath(dir.getAbsolutePath());
            defaultConfigLabel.setText(configManager.getConfigPath());
        } else {
            defaultConfigLabel.setText(configManager.getConfigPath());
        }
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

        friendlyNameText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                changeFriendlyName(newValue);
            }
        });
    }
}
