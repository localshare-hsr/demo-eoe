package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
import ch.hsr.epj.localshare.demo.logic.DiscoveryController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class MainWindowController implements Initializable {

	@FXML
	private ListView<Peer> listView;

	private ObservableList<Peer> peerObservableList;

	public MainWindowController() {

    peerObservableList = FXCollections.observableArrayList();
		DiscoveryController discoveryController = new DiscoveryController(peerObservableList);
		discoveryController.startServer();
		discoveryController.startSearcher();

  }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listView.setItems(peerObservableList);
		listView.setCellFactory(peerListView -> new PeerListViewCell());
	}
}
