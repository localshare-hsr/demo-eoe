package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class PeerListViewCell extends ListCell<Peer> {

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

    private static final String COLOR = "derive(palegreen, 50%)";


    @Override
    protected void updateItem(Peer peer, boolean empty) {
        super.updateItem(peer, empty);

        if (empty || peer == null) {
            setText(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/fxml/ListCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

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
                        }
                        event.consume();
                    });

            gridPane.setOnDragDropped(
                    event -> {
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        if (db.hasFiles()) {
                            System.out.println(
                                    "Send File: " + db.getFiles().toString() + " To: " + fn.getText());
                            success = true;
                        }
                        /* let the source know whether the string was successfully
                         * transferred and used */
                        event.setDropCompleted(success);

                        event.consume();
                    });

            setText(null);
            setGraphic(gridPane);
        }
    }


}
