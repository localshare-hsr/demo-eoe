package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.presentation.Download;
import ch.hsr.epj.localshare.demo.gui.presentation.Peer;
import ch.hsr.epj.localshare.demo.logic.environment.User;
import ch.hsr.epj.localshare.demo.logic.keymanager.KeyManager;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.DiscoveryController;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.HttpClientController;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.HttpServerController;
import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStoreException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

  private static final Logger logger = Logger.getLogger(MainWindowController.class.getName());

  @FXML
  private AnchorPane preferencesRootPane;

  @FXML
  private ListView<Peer> listView;

  @FXML
  private ListView<Download> listViewTransfer;

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
  private static HttpServerController httpServerController;
  private HttpClientController httpClientController;

  @FXML
  private ObservableList<Peer> peerObservableList;

  @FXML
  private ObservableList<Download> downloadObservableList;


  public MainWindowController() {

    peerObservableList = FXCollections.observableArrayList();
    downloadObservableList = FXCollections.observableArrayList();

    DiscoveryController discoveryController = new DiscoveryController(peerObservableList);
    discoveryController.startServer();
    discoveryController.startSearcher();

    httpClientController = new HttpClientController(downloadObservableList);

    User user = User.getInstance();
    friendlyName = user.getFriendlyName();
    KeyManager keyManager = new KeyManager();
    if (!keyManager.existsKeyingMaterial(friendlyName)) {
      keyManager.generateKeyingMaterial(friendlyName);
    }
    try {
      fingerPrint = keyManager.getUsersFingerprint();
    } catch (KeyStoreException e) {
      logger.log(Level.WARNING, "Could not load user fingerprint", e);
    }

  }

  private static void startHttpServer() {
    httpServerController = new HttpServerController();
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

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    listView.setItems(peerObservableList);

    listViewTransfer.setItems(downloadObservableList);

    startHttpServer();
    startHttpClient();
    httpServerController.connectClientController(httpClientController);
    listView.setCellFactory(peerListView -> new PeerListViewCell(httpServerController));
    listViewTransfer
        .setCellFactory(transferListView -> new DownloadListViewCell(httpClientController));

    ipAddressText.setText(String.valueOf(IPAddressUtil.getLocalIPAddress()));
    fingerPrintText.setText(fingerPrint);
    friendlyNameText.setText(friendlyName);
  }

  public static void shutdownApplication() {
    httpServerController.stopHTTPServer();
    logger.log(Level.INFO, "LocalShare properly closed");
  }

  private void startHttpClient() {
    httpClientController = new HttpClientController(downloadObservableList);
  }

  @FXML
  private void handlePreferencesButtonAction() throws IOException {
    AnchorPane preferencesPane =
        FXMLLoader.load(Objects
            .requireNonNull(getClass().getClassLoader().getResource("fxml/PreferencesView.fxml")));
    preferencesRootPane.getChildren().setAll(preferencesPane);
  }
}
