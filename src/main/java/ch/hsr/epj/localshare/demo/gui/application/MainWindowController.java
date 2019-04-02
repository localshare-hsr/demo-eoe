package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
import ch.hsr.epj.localshare.demo.logic.DiscoveryController;
import ch.hsr.epj.localshare.demo.logic.KeyManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainWindowController implements Initializable {

  @FXML
  private AnchorPane preferencesRootPane;

	@FXML
	private ListView<Peer> listView;

  @FXML
  private ObservableList<Peer> peerObservableList;

  @FXML
  private void handlePreferencesButtonAction(ActionEvent event) throws IOException {
    AnchorPane preferencesPane = FXMLLoader
        .load(getClass().getClassLoader().getResource("fxml/PreferencesView.fxml"));
    System.out.println(preferencesRootPane);
    preferencesRootPane.getChildren().setAll(preferencesPane);
  }

  // double click list item -> trusted on/off + change color
  @FXML
  private void onListItemDoubleClick(MouseEvent click) {
    if (click.getClickCount() == 2) {
      Peer currentItemSelected = listView.getSelectionModel().getSelectedItem();
      currentItemSelected.setTrustState(true);
      listView.refresh();
    }
  }

  //right click display dialogue box to enter display name
  @FXML
  private void onListItemRighClick(MouseEvent click) {

  }

	public MainWindowController() {

    peerObservableList = FXCollections.observableArrayList();
    DiscoveryController discoveryController = new DiscoveryController(peerObservableList);
    discoveryController.startServer();
    discoveryController.startSearcher();

    KeyManager keyManager = new KeyManager();
    keyManager.generateNewCertificate("pascal");
    System.out.println("My Fingerprint is: " + keyManager.getFingerprint());

  }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listView.setItems(peerObservableList);
		listView.setCellFactory(peerListView -> new PeerListViewCell());
	}
}
