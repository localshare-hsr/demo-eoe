package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.presentation.Download;
import ch.hsr.epj.localshare.demo.gui.presentation.Download.DownloadState;
import ch.hsr.epj.localshare.demo.gui.presentation.Peer;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.FileTransfer;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.HttpClientController;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

public class DownloadListViewCell extends ListCell<Download> {

  private static final Logger logger = Logger.getLogger(DownloadListViewCell.class.getName());

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

  @FXML
  Label transferSpeed;

  @FXML
  Label secondsToGo;

  private FXMLLoader mLLoader;

  private HttpClientController httpClientController;
  private FileTransfer fileTransfer;

  DownloadListViewCell(HttpClientController httpClientController) {
    this.httpClientController = httpClientController;
  }

  @Override
  protected void updateItem(Download download, boolean empty) {
    super.updateItem(download, empty);

    if (empty) {
      setText(null);
      setGraphic(null);

    } else {
      if (mLLoader == null) {
        mLLoader = new FXMLLoader(getClass().getResource("/fxml/TransferCell.fxml"));
        mLLoader.setController(this);

        try {
          mLLoader.load();
        } catch (IOException e) {
          logger.log(Level.INFO, "Could not load TransferCell.fxml", e);
        }
      }

      if (download.getDownloadState() == DownloadState.RUNNING) {
        setRunningVisability();
      }

      buttonAccept.setOnAction(
          event -> {
            setRunningVisability();
            download.setDownloadState(DownloadState.RUNNING);

            try {
              fileTransfer = new FileTransfer(
                  new Peer("10.10.10.10", download.getFriendlyName(), null, null),
                  download.getUrl(),
                  transferProgressBar, transferSpeed, secondsToGo);
              httpClientController.downloadFileFromPeer(fileTransfer);

            } catch (FileNotFoundException e) {
              logger.log(Level.INFO, "Could not find file", e);
            }


          }
      );

      buttonCancelTransfer.setOnAction(
          event -> fileTransfer.shutdownDownload()
      );

      buttonDecline.setOnAction(
          event -> {
            ObservableList<Download> downloadObservableList = httpClientController
                .getDownloadObservableList();
            downloadObservableList.remove(download);
            this.getListView().refresh();
          }
      );

      size.setText(String.valueOf(download.getSize()));
      filename.setText(String.valueOf(download.getFileName()));
      setGraphic(gridPaneTransfer);

    }
  }

  private void setRunningVisability() {
    gridPaneTransfer.setStyle("-fx-background-color: PALEGREEN");
    buttonAccept.setVisible(false);
    buttonAccept.setDisable(true);
    buttonDecline.setVisible(false);
    buttonDecline.setDisable(true);
    transferProgressBar.setVisible(true);
    buttonCancelTransfer.setDisable(false);
    buttonCancelTransfer.setVisible(true);
    transferSpeed.setVisible((true));
    secondsToGo.setVisible(true);
  }

}
