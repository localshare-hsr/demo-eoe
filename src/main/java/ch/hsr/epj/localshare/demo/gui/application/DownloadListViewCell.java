package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.presentation.Download;
import ch.hsr.epj.localshare.demo.gui.presentation.Download.DownloadState;
import ch.hsr.epj.localshare.demo.gui.presentation.Peer;
import ch.hsr.epj.localshare.demo.gui.presentation.UIProgress;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.FileTransfer;
import ch.hsr.epj.localshare.demo.logic.networkcontroller.HttpClientController;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class DownloadListViewCell extends ListCell<Download> {

  private static final Logger logger = Logger.getLogger(DownloadListViewCell.class.getName());

  @FXML
  private AnchorPane paneTransfer;

  @FXML
  private Label sizeTotal;

  @FXML
  private Label filename;

  @FXML
  private ImageView buttonAccept;

  @FXML
  private ImageView buttonCancel;

  @FXML
  private ProgressBar transferProgressBar;

  @FXML
  private Label transferSpeed;

  @FXML
  private Label secondsToGo;

  @FXML
  private HBox progressHbox;

  @FXML
  private Label sizeCurrent;

  @FXML
  private ImageView finishedIcon;

  @FXML
  private ImageView downloadingIcon;

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

      setEmptyState();

      if (download.getDownloadState() == DownloadState.WAITING) {
        setWaitingVisibility();
      }

      if (download.getDownloadState() == DownloadState.RUNNING) {
        setRunningVisibility();
        ProgressBar progressBar = download.getProgressBar();
        transferProgressBar.progressProperty().bind(progressBar.progressProperty());

        Label transferSpeedLabel = download.getTransferSpeed();
        transferSpeed.textProperty().bind(transferSpeedLabel.textProperty());

        Label transferTime = download.getTransferTime();
        secondsToGo.textProperty().bind(transferTime.textProperty());
      }

      if (download.getDownloadState() == DownloadState.FINISHED) {
        setFinishedVisibility();
      }

      buttonAccept.setOnMouseClicked(
          event -> {
            setEmptyState();
            setRunningVisibility();
            download.setDownloadState(DownloadState.RUNNING);

            ProgressBar progressBar = new ProgressBar();
            download.setProgressBar(progressBar);
            transferProgressBar.progressProperty().bind(progressBar.progressProperty());

            progressBar.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE,
                new MyCustomEventHandler() {
                  @Override
                  public void onFinishedEvent(int param0) {
                    setEmptyState();
                    setFinishedVisibility();
                    download.setDownloadState(DownloadState.FINISHED);
                    refreshList();
                  }
                });

            Label transferSpeedLabel = new Label();
            download.setTransferSpeed(transferSpeedLabel);
            transferSpeed.textProperty().bind(transferSpeedLabel.textProperty());

            Label transferTimeLabel = new Label();
            download.setTransferTime(transferTimeLabel);
            secondsToGo.textProperty().bind(transferTimeLabel.textProperty());

            try {
              UIProgress uiProgress = new UIProgress(progressBar, transferSpeedLabel,
                  transferTimeLabel, sizeCurrent);
              fileTransfer = new FileTransfer(
                  new Peer("10.10.10.10", download.getFriendlyName(), null, null),
                  download.getUrl(), uiProgress);
              httpClientController.downloadFileFromPeer(fileTransfer);

            } catch (FileNotFoundException e) {
              logger.log(Level.INFO, "Could not find file", e);
            }


          }
      );

      buttonCancel.setOnMouseClicked(
          event -> {
            if (download.getDownloadState() == DownloadState.RUNNING) {
              fileTransfer.shutdownDownload();

            }
              ObservableList<Download> downloadObservableList = httpClientController
                  .getDownloadObservableList();
              downloadObservableList.remove(download);
              this.getListView().refresh();

          });

      sizeTotal.setText(download.getFileSize());
      filename.setText(String.valueOf(download.getFileName()));
      setGraphic(paneTransfer);

    }
  }

  private void refreshList() {
    this.getListView().refresh();
  }

  private void setEmptyState() {
    buttonAccept.setVisible(false);
    buttonCancel.setVisible(false);
    finishedIcon.setVisible(false);
    downloadingIcon.setVisible(false);
    progressHbox.setVisible(false);
    transferProgressBar.setVisible(false);
    filename.setVisible(false);
  }

  private void setFinishedVisibility() {
    finishedIcon.setVisible(true);
    filename.setVisible(true);
  }

  private void setWaitingVisibility() {
    progressHbox.setVisible(false);
    transferProgressBar.setVisible(false);
    buttonAccept.setVisible(true);
    filename.setVisible(true);
    buttonCancel.setVisible(true);
    downloadingIcon.setVisible(true);
  }

  private DropShadow createDropShadow() {
    DropShadow dropShadow = new DropShadow();
    dropShadow.setOffsetX(2.0f);
    dropShadow.setOffsetX(2.0f);
    dropShadow.setColor(Color.GRAY);
    return dropShadow;
  }

  @FXML
  private void highlightCancelButton() {
    buttonCancel.setEffect(createDropShadow());
  }

  @FXML
  void normalizeCancelButton() {
    buttonCancel.setEffect(null);
  }

  @FXML
  void highlightAcceptButton() {
    buttonAccept.setEffect(createDropShadow());
  }

  @FXML
  void normalizeAcceptButton() {
    buttonAccept.setEffect(null);
  }


  private void setRunningVisibility() {
    progressHbox.setVisible(true);
    filename.setVisible(true);
    transferProgressBar.setVisible(true);
    buttonCancel.setVisible(true);
    downloadingIcon.setVisible(true);
  }

}
