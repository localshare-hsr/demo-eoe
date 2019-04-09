package ch.hsr.epj.localshare.demo.gui.application;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class PreferencesViewController {

  @FXML private Text path;

  final DirectoryChooser directoryChooser = new DirectoryChooser();
  // configuringDirectoryChooser(directoryChooser);

  @FXML
  private void handleChangeDirectoryButtonAction(ActionEvent event) {
    Node node = (Node) event.getSource();
    final Stage stage = (Stage) node.getScene().getWindow();

    File dir = directoryChooser.showDialog(stage);
    if (dir != null) {
      path.setText(dir.getAbsolutePath());
    } else {
      path.setText("C:\\TEMP\\LocalShareDownloads");
    }
  }

  private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
    // Set title for DirectoryChooser
    directoryChooser.setTitle("Select Some Directories");
    // Set Initial Directory
    directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
  }
}
