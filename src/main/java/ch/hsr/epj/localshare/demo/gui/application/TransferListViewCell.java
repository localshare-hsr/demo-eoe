package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.data.Transfer;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

public class TransferListViewCell extends ListCell<Transfer> {

  @FXML
  GridPane gridPaneTransfer;

  @FXML
  Label size;

  @FXML
  Label filename;

  private FXMLLoader mLLoader;

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

      size.setText(String.valueOf(transfer.getSize()));
      filename.setText(String.valueOf(transfer.getName()));
      setGraphic(gridPaneTransfer);

    }
  }

}
