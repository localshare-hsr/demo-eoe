package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
import ch.hsr.epj.localshare.demo.gui.data.Transfer;
import ch.hsr.epj.localshare.demo.logic.ConfigManager;
import ch.hsr.epj.localshare.demo.logic.DiscoveryController;
import ch.hsr.epj.localshare.demo.logic.HttpClientController;
import ch.hsr.epj.localshare.demo.logic.HttpServerController;
import ch.hsr.epj.localshare.demo.logic.User;
import ch.hsr.epj.localshare.demo.logic.keymanager.KeyManager;
import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStoreException;
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
  private ListView<Transfer> listViewTransfer;

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

  @FXML
  private ObservableList<Transfer> transferObservableList;

  HttpClientController httpClientController;

  public MainWindowController() {

    peerObservableList = FXCollections.observableArrayList();
    transferObservableList = FXCollections.observableArrayList();

    DiscoveryController discoveryController = new DiscoveryController(peerObservableList);
    discoveryController.startServer();
    discoveryController.startSearcher();

    httpClientController = new HttpClientController();
    System.out.println(ConfigManager.getInstance().getDownloadPath());

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

    peerObservableList.add(new Peer("10.0.0.0", "dummy", "dummy", ""));

    listView.setItems(peerObservableList);

    transferObservableList.addAll(new Transfer("Elvis", 12345, "Test.pdf", null),
        new Transfer("Elvis", 35, "config.txt", null));

    listViewTransfer.setItems(transferObservableList);

    startHttpServer();
    listView.setCellFactory(peerListView -> new PeerListViewCell(httpServerController));
    listViewTransfer
        .setCellFactory(transferListView -> new TransferListViewCell(httpClientController));

    ipAddressText.setText(String.valueOf(IPAddressUtil.getLocalIPAddress()));
    fingerPrintText.setText(fingerPrint);
    friendlyNameText.setText(friendlyName);
  }

  private void startHttpServer() {
    httpServerController = new HttpServerController();
  }
}
