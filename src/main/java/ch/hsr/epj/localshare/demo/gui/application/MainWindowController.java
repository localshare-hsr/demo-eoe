package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.presentation.Download;
import ch.hsr.epj.localshare.demo.gui.presentation.Peer;
import ch.hsr.epj.localshare.demo.logic.environment.ConfigManager;
import ch.hsr.epj.localshare.demo.logic.environment.User;
import ch.hsr.epj.localshare.demo.logic.keymanager.KeyManager;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.DiscoveryController;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.HttpClientController;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.HttpServerController;
import ch.hsr.epj.localshare.demo.network.utils.IPAddressUtil;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.bouncycastle.util.IPAddress;
import org.bouncycastle.util.encoders.Hex;

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
  private Text friendlyNameText;

  @FXML
  private ImageView openDownloads;

  @FXML
  private ImageView addPeerIcon;

  @FXML
  private Label addPeerText;

  @FXML
  private ImageView openDownloadsIcon;

  @FXML
  private Label openDownloadsText;

  @FXML
  private VBox searchingPeers;

  @FXML
  private VBox noDownloads;

  @FXML
  private Circle ownIcon;

  @FXML
  private Text ownText;


  private String fingerPrint;
  private String friendlyName;
  private HttpServerController httpServerController;
  private HttpClientController httpClientController;

  private DropShadow createDropShadow() {
    DropShadow dropShadow = new DropShadow();
    dropShadow.setOffsetX(2.0f);
    dropShadow.setOffsetX(2.0f);
    dropShadow.setColor(Color.GRAY);
    return dropShadow;
  }


  @FXML
  private ObservableList<Peer> peerObservableList;

  @FXML
  private ObservableList<Download> downloadObservableList;

  private KeyManager keyManager;

  public MainWindowController() {

    peerObservableList = FXCollections.observableArrayList();
    downloadObservableList = FXCollections.observableArrayList();

    httpClientController = new HttpClientController(downloadObservableList, peerObservableList);

    DiscoveryController discoveryController = new DiscoveryController(httpClientController);
    discoveryController.startServer();
    discoveryController.startSearcher();


    User user = User.getInstance();
    friendlyName = user.getFriendlyName();
    keyManager = new KeyManager(ConfigManager.getInstance().getConfigPath(),
        "keystore.p12", friendlyName);
    fingerPrint = keyManager.getUsersFingerprint();

  }

  private void startHttpServer() {
    httpServerController = new HttpServerController(keyManager.getKeyStore());
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
  private void openDownloadFolder() throws IOException {
    Runtime.getRuntime()
        .exec("explorer.exe /select," + ConfigManager.getInstance().getDownloadPath());
  }


  @FXML
  private void addPeerManually() throws IOException {
    TextInputDialog peerInputDialog = createInputDialog();
    Optional<String> result = peerInputDialog.showAndWait();
    if (result.isPresent()) {
      String insertedIP = peerInputDialog.getEditor().getText();
      if (!IPAddress.isValid(insertedIP)) {
        invalidIpDialog();
        addPeerManually();
      } else {
        try {
          httpClientController.checkPeerAvailability(new Peer(insertedIP, "", "", ""));
        } catch (ConnectException e) {
          ipNotAvailableDialog();
        }
      }
    } else {
      peerInputDialog.close();
    }

  }

  private void invalidIpDialog() {
    Alert invalidIP = new Alert(AlertType.ERROR);
    invalidIP.setTitle("Invalid IP Address");
    invalidIP.setHeaderText("Please insert a valid IP!");
    invalidIP.showAndWait();
  }

  private void ipNotAvailableDialog() {
    Alert invalidIP = new Alert(AlertType.ERROR);
    invalidIP.setTitle("LocalShare not available");
    invalidIP.setHeaderText("There is no LocalShare-Instance Running");
    invalidIP.showAndWait();
  }


  private TextInputDialog createInputDialog() {
    TextInputDialog peerInputDialog = new TextInputDialog("152.96.");
    peerInputDialog.setHeaderText("Enter Peer IP Address");
    peerInputDialog.setTitle("Add Peer");
    return peerInputDialog;
  }

  @FXML
  private void highlightDownloadButton() {
    openDownloadsIcon.setEffect(createDropShadow());
    openDownloadsText.setEffect(createDropShadow());
  }

  @FXML
  void normalizeDownloadButton() {
    openDownloadsText.setEffect(null);
    openDownloadsIcon.setEffect(null);
  }


  @FXML
  private void highlightAddButton() {
    addPeerIcon.setEffect(createDropShadow());
    addPeerText.setEffect(createDropShadow());
  }

  @FXML
  void normalizeAddButton() {
    addPeerIcon.setEffect(null);
    addPeerText.setEffect(null);
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {

    listView.setItems(peerObservableList);
    peerObservableList
        .addListener((ListChangeListener<Peer>) c -> searchingPeers.setVisible(false));

    listViewTransfer.setItems(downloadObservableList);
    downloadObservableList
        .addListener((ListChangeListener<Download>) c -> noDownloads.setVisible(false));

    startHttpServer();
    startHttpClient();
    httpServerController.connectClientController(httpClientController);
    listView.setCellFactory(peerListView -> new PeerListViewCell(httpServerController));
    listViewTransfer
        .setCellFactory(transferListView -> new DownloadListViewCell(httpClientController));

    ipAddressText.setText(String.valueOf(IPAddressUtil.getLocalIPAddress()));
    fingerPrintText.setText(fingerPrint);
    friendlyNameText.setText(friendlyName);
    try {
      ownIcon.setFill(
          Paint.valueOf(getPeerHexColor(
              friendlyName + IPAddressUtil.getLocalIPAddress().toString().substring(1))));
    } catch (NoSuchAlgorithmException e) {
      logger.log(Level.WARNING, "No Algorithm Found");
    }
    ownText.setText(friendlyName.substring(0, 2).toUpperCase());
  }

  public void shutdownApplication() {
    httpServerController.stopHTTPServer();
    logger.log(Level.INFO, "LocalShare properly closed");
  }

  private void startHttpClient() {
    httpClientController = new HttpClientController(downloadObservableList, peerObservableList);
  }

  private String getPeerHexColor(String originalString) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("MD5");
    byte[] hash = digest.digest(
        originalString.getBytes(StandardCharsets.UTF_8));
    String md5hex = new String(Hex.encode(hash));
    return "#" + md5hex.substring(0, 6);
  }

  @FXML
  private void handlePreferencesButtonAction() throws IOException {
    AnchorPane preferencesPane =
        FXMLLoader.load(Objects
            .requireNonNull(getClass().getClassLoader().getResource("fxml/PreferencesView.fxml")));
    preferencesRootPane.getChildren().setAll(preferencesPane);
  }
}
