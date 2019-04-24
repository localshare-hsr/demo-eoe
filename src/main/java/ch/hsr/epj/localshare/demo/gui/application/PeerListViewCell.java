package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
import ch.hsr.epj.localshare.demo.logic.HttpServerController;
import java.io.IOException;
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
import javafx.scene.layout.GridPane;

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
  private GridPane gridPane;
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
        mLLoader = new FXMLLoader(getClass().getResource("/fxml/ListCell.fxml"));
        mLLoader.setController(this);

        try {
          mLLoader.load();
        } catch (IOException e) {
          logger.log(Level.WARNING, "Unable to load ListCell file", e);
        }
      }

      ContextMenu contextMenu = new ContextMenu();
      MenuItem editDisplayName = new MenuItem("Edit Displayname");
      MenuItem editTrustState = new MenuItem("Change Trust State");

      editDisplayName.setOnAction(event -> {
        Peer item = this.getItem();
        TextInputDialog textInputDialog = new TextInputDialog("hanswurst");
        textInputDialog.setHeaderText("Enter Displayname");
        textInputDialog.showAndWait();
        peer.setDisplayName(textInputDialog.getEditor().getText());

        // refresh ListView

      });

      editTrustState.setOnAction(event -> {
        Peer item = this.getItem();
        item.setTrustState(!item.getTrustState());
      });

      contextMenu.getItems().add(editDisplayName);
      contextMenu.getItems().add(editTrustState);

      this.setContextMenu(contextMenu);


      ip.setText(String.valueOf(peer.getIP()));
      fn.setText(String.valueOf(peer.getFriendlyName()));
      finger.setText(String.valueOf(peer.getFingerPrint()));
      dn.setText(String.valueOf(peer.getDisplayName()));
      if (peer.getTrustState()) {
        setStyle("-fx-background: " + COLOR + ";");
      }

      gridPane.setOnDragOver(
          event -> {
            if (event.getGestureSource() != gridPane && event.getDragboard().hasFiles()) {
              /* allow for both copying and moving, whatever user chooses */
              event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
              gridPane.setStyle("-fx-background-color: PALEGREEN");
            }
            event.consume();
          });

      gridPane.setOnDragExited(
          event -> gridPane.setStyle("-fx-background-color: none"));

      gridPane.setOnDragDropped(
          event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
              logger.log(Level.INFO,
                  String.format("Send File: {0} To: {0}", db.getFiles().toString(), fn.getText()));
              httpServerController.sharePrivate("someone", db.getFiles());
              success = true;
            }
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);

            event.consume();
          });

      setGraphic(gridPane);
    }
  }
}
