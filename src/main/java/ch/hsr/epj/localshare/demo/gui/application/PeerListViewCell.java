package ch.hsr.epj.localshare.demo.gui.application;

import java.io.IOException;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

public class PeerListViewCell extends ListCell<Peer> {

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    GridPane gridPane;

    private FXMLLoader mLLoader;

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
            label1.setText(String.valueOf(peer.getIP()));
            label2.setText(String.valueOf(peer.getFirendlyName()));


        }

        gridPane.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != gridPane
                        && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        gridPane.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    System.out.println("Send File: " + db.getFiles().toString() + " To: " + label1.getText());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });


        setText(null);
        setGraphic(gridPane);
    }
}
