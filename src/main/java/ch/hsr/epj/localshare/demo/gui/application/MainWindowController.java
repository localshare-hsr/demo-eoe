package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
import ch.hsr.epj.localshare.demo.logic.DiscoveryController;
import ch.hsr.epj.localshare.demo.logic.KeyManager;
import ch.hsr.epj.localshare.demo.logic.User;
import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

  @FXML
  private AnchorPane preferencesRootPane;

  @FXML
  private ListView<Peer> listView;

  @FXML
  private Text ipAddressText;

  @FXML
  private Text fingerPrintText;

  @FXML
  private VBox testRefresh;

  @FXML
  private Text friendlyNameText;

  private String fingerPrint;
  private String friendlyName;

  @FXML
  private ObservableList<Peer> peerObservableList;

  // double click list item -> trusted on/off + change color
  public MainWindowController() {

    peerObservableList = FXCollections.observableArrayList();
    DiscoveryController discoveryController = new DiscoveryController(peerObservableList);
    discoveryController.startServer();
    discoveryController.startSearcher();

    User user = User.getInstance();
    KeyManager keyManager = new KeyManager();
    keyManager.generateNewCertificate(user.getFriendlyName());
    fingerPrint = keyManager.getFingerprint();
    friendlyName = user.getFriendlyName();
  }

  @FXML
  private void handlePreferencesButtonAction(ActionEvent event) throws IOException {
    AnchorPane preferencesPane =
        FXMLLoader.load(getClass().getClassLoader().getResource("fxml/PreferencesView.fxml"));
    preferencesRootPane.getChildren().setAll(preferencesPane);
  }

  @FXML
  private void onListItemDoubleClick(MouseEvent click) {
    if (click.getClickCount() == 2) {
      Peer currentItemSelected = listView.getSelectionModel().getSelectedItem();
      currentItemSelected.setTrustState(true);
    }
  }

  @FXML
  private void onWindowDragEnter() {
    listView.setEffect(new InnerShadow(50, Color.GRAY));
  }

  @FXML
  private void onWindowDragExit() {
    listView.setEffect(null);
  }

  // right click display dialogue box to enter display name
  @FXML
  private void onListItemRighClick(MouseEvent click) {
  }

  @FXML
  private void refreshList() {

    listView.refresh();

    for (Peer item : listView.getItems()) {
      System.out.println(item.toString());
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    listView.setItems(peerObservableList);
    listView.setCellFactory(peerListView -> new PeerListViewCell());
//                listView.setCellFactory(ComboBoxListCell.forListView(peerObservableList));
    ipAddressText.setText(String.valueOf(IPAddressUtil.getLocalIPAddress()));
    fingerPrintText.setText(fingerPrint);
    friendlyNameText.setText(friendlyName);
  }
}
