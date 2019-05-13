package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.presentation.Peer;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.HttpServerController;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.bouncycastle.util.encoders.Hex;

public class PeerListViewCell extends ListCell<Peer> {

  private static final Logger logger = Logger.getLogger(PeerListViewCell.class.getName());

  private static final String COLOR = "derive(palegreen, 50%)";
  @FXML
  private Label ip;
  @FXML
  private Label fn;
  @FXML
  private Label finger;
  @FXML
  private Label dn;
  @FXML
  private HBox panePeer;
  @FXML
  private Circle peerIcon;
  @FXML
  private Text textIcon;


  private FXMLLoader mLLoader;


  private HttpServerController httpServerController;

  public PeerListViewCell(HttpServerController httpServerController) {
    this.httpServerController = httpServerController;
  }


  @Override
  protected void updateItem(Peer peer, boolean empty) {
    super.updateItem(peer, empty);

    if (empty || peer == null) {
      setText(null);
      setGraphic(null);
    } else {
      if (mLLoader == null) {
        loadMLLoader();
      }
      ContextMenu contextMenu = createContextMenu(peer);
      this.setContextMenu(contextMenu);

      try {
        setPeerAttributes(peer);
      } catch (NoSuchAlgorithmException e) {
        logger.log(Level.WARNING, "No such Algorithm in Bouncy Castle");
      }

      addDragAndDropCapabilities(peer);
    }
  }

  private void loadMLLoader() {
    mLLoader = new FXMLLoader(getClass().getResource("/fxml/ListCell.fxml"));
    mLLoader.setController(this);
    try {
      mLLoader.load();
    } catch (IOException e) {
      logger.log(Level.WARNING, "Unable to load ListCell file", e);
    }
  }

  private ContextMenu createContextMenu(final Peer peer) {
    ContextMenu contextMenu = new ContextMenu();
    MenuItem editDisplayName = new MenuItem("Edit Displayname");
    MenuItem editTrustState = new MenuItem("Change Trust State");

    addDisplayNameActionListener(editDisplayName, peer);
    addTrustStateActionListener(editTrustState);

    contextMenu.getItems().add(editDisplayName);
    contextMenu.getItems().add(editTrustState);

    return contextMenu;
  }

  private void addDisplayNameActionListener(final MenuItem menuItem, final Peer peer) {
    menuItem.setOnAction(event -> {
      TextInputDialog textInputDialog = new TextInputDialog("hanswurst");
      textInputDialog.setHeaderText("Enter Displayname");
      textInputDialog.showAndWait();
      peer.setDisplayName(textInputDialog.getEditor().getText());
      this.getListView().refresh();
    });
  }

  private void addTrustStateActionListener(final MenuItem menuItem) {
    menuItem.setOnAction(event -> {
      Peer item = this.getItem();
      item.setTrustState(!item.getTrustState());
      this.getListView().refresh();
    });
  }

  private void setPeerAttributes(final Peer peer) throws NoSuchAlgorithmException {
    ip.setText(String.valueOf(peer.getIP()));
    fn.setText(String.valueOf(peer.getFriendlyName()));
    finger.setText(String.valueOf(peer.getFingerPrint()));
    dn.setText(String.valueOf(peer.getDisplayName()));
    peerIcon.setFill(Paint.valueOf(getPeerHexColor(peer.getFriendlyName() + peer.getIP())));
    textIcon.setText(peer.getFriendlyName().substring(0, 2).toUpperCase());
    if (peer.getTrustState()) {
      setStyle("-fx-background: " + COLOR + ";");
    }
  }

  private String getPeerHexColor(String originalString) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("MD5");
    byte[] hash = digest.digest(
        originalString.getBytes(StandardCharsets.UTF_8));
    String md5hex = new String(Hex.encode(hash));
    return "#" + md5hex.substring(0, 6);
  }

  private void addDragAndDropCapabilities(final Peer peer) {
    panePeer.setOnDragOver(
        event -> {
          if (event.getGestureSource() != panePeer && event.getDragboard().hasFiles()) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            panePeer.setStyle("-fx-background-color: PALEGREEN");
          }
          event.consume();
        });

    panePeer.setOnDragExited(
        event -> panePeer.setStyle("-fx-background-color: none"));

    panePeer.setOnDragDropped(
        event -> {
          Dragboard db = event.getDragboard();
          boolean success = false;
          if (db.hasFiles()) {
            logger.log(Level.INFO,
                String.format("Send File: {0} To: {1}", db.getFiles().toString(), fn.getText()));
            try {

              httpServerController
                  .sharePrivate(InetAddress.getByName(peer.getIP()), db.getFiles());
            } catch (UnknownHostException e) {
              logger.log(Level.INFO, "Peer host does not exist", e);
            }
            success = true;
          }
          /* let the source know whether the string was successfully
           * transferred and used */
          event.setDropCompleted(success);

          event.consume();
        });
    setGraphic(panePeer);
  }
}
