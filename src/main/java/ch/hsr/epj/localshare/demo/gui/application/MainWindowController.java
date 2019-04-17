package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
import ch.hsr.epj.localshare.demo.logic.DiscoveryController;
import ch.hsr.epj.localshare.demo.logic.HttpServerController;
import ch.hsr.epj.localshare.demo.logic.User;
import ch.hsr.epj.localshare.demo.logic.keymanager.KeyManager;
import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStoreException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
  private HttpServerController httpServerController;

  @FXML
  private ObservableList<Peer> peerObservableList;

  public MainWindowController() {

    peerObservableList = FXCollections.observableArrayList();
    DiscoveryController discoveryController = new DiscoveryController(peerObservableList);
    discoveryController.startServer();
    discoveryController.startSearcher();

    User user = User.getInstance();
    friendlyName = user.getFriendlyName();
    KeyManager keyManager = new KeyManager();
    if (!keyManager.existsKeyingMaterial(friendlyName)) {
      keyManager.generateKeyingMaterial(friendlyName);
    }
    try {
      fingerPrint = keyManager.getUsersFingerprint();
    } catch (KeyStoreException e) {
      e.printStackTrace();
    }

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

  @FXML
  private void refreshList() {

    listView.refresh();
    peerObservableList = FXCollections.observableArrayList();
    DiscoveryController discoveryController = new DiscoveryController(peerObservableList);
    discoveryController.startServer();
    discoveryController.startSearcher();

    for (Peer item : listView.getItems()) {
      System.out.println(item.toString());
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    listView.setItems(peerObservableList);
    startHttpServer();
    listView.setCellFactory(peerListView -> new PeerListViewCell(httpServerController));
    ipAddressText.setText(String.valueOf(IPAddressUtil.getLocalIPAddress()));
    fingerPrintText.setText(fingerPrint);
    friendlyNameText.setText(friendlyName);
  }

  private void startHttpServer() {
    httpServerController = new HttpServerController();

    // FIXME: hack to get a file to share without a peer
    List<File> files = new ArrayList<>();
    File file = new File("C:\\Users\\marco\\rocket.jpg");
    files.add(file);
    httpServerController.sharePrivate("test", files);
  }
}
