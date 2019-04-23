package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
import ch.hsr.epj.localshare.demo.gui.data.Transfer;
import ch.hsr.epj.localshare.demo.logic.FileTransfer;
import ch.hsr.epj.localshare.demo.logic.HttpClientController;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

public class TransferListViewCell extends ListCell<Transfer> {

  @FXML
  GridPane gridPaneTransfer;

  @FXML
  Label size;

  @FXML
  Label filename;

  @FXML
  Button buttonAccept;

  @FXML
  Button buttonDecline;

  @FXML
  Button buttonCancelTransfer;

  @FXML
  ProgressBar transferProgressBar;

  private FXMLLoader mLLoader;

  private HttpClientController httpClientController;

  public TransferListViewCell(HttpClientController httpClientController) {
    this.httpClientController = httpClientController;
  }

  @Override
  protected void updateItem(Transfer transfer, boolean empty) {
    super.updateItem(transfer, empty);

    if (empty || transfer == null) {
      setText(null);
      setGraphic(null);

    } else {
      if (mLLoader == null) {
        mLLoader = new FXMLLoader(getClass().getResource("/fxml/TransferCell.fxml"));
        mLLoader.setController(this);

        try {
          mLLoader.load();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      buttonAccept.setOnAction(
          event -> {
            gridPaneTransfer.setStyle("-fx-background-color: PALEGREEN");
            buttonAccept.setVisible(false);
            buttonAccept.setDisable(true);
            buttonDecline.setVisible(false);
            buttonDecline.setDisable(true);
            transferProgressBar.setVisible(true);
            buttonCancelTransfer.setDisable(false);
            buttonCancelTransfer.setVisible(true);

            try {
              FileTransfer fileTransfer = new FileTransfer(
                  new Peer("10.10.10.10", "asdf", null, null),
                  new URL("http://releases.ubuntu.com/18.04.2/ubuntu-18.04.2-desktop-amd64.iso"),
                  transferProgressBar);
              httpClientController.downloadFileFromPeer(fileTransfer);

            } catch (MalformedURLException e) {
              e.printStackTrace();
            } catch (FileNotFoundException e) {
              e.printStackTrace();
            }

            //handle ProgressBar e.g if 10% of File loaded
          }
      );

      buttonCancelTransfer.setOnAction(
          event -> {
            //cancel transfer thread + delete view

          }
      );

      buttonDecline.setOnAction(
          event -> {
            // Methode im MainWindowController aufrufen mittels Interface das aktuell selektiertes Objekt aus ListView + ObservableList löscht

          }
      );

      size.setText(String.valueOf(transfer.getSize()));
      filename.setText(String.valueOf(transfer.getName()));
      setGraphic(gridPaneTransfer);

    }
  }

}
