package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
import ch.hsr.epj.localshare.demo.logic.DiscoveryController;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class MainWindowController implements Initializable, PeerUpdaterIF {

	@FXML
	private ListView<Peer> listView;

	private ObservableList<Peer> peerObservableList;

	public MainWindowController() {

    peerObservableList = FXCollections.observableArrayList();
		DiscoveryController discoveryController = new DiscoveryController(this);
    discoveryController.startSearch();
  }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listView.setItems(peerObservableList);
		listView.setCellFactory(peerListView -> new PeerListViewCell());
	}

	@Override
	public void update(String[] event) {
    List<Peer> newPeerList = new ArrayList<>();
		for (String ip : event) {
      newPeerList.add(new Peer(ip, "LS user"));
    }

    for (Peer p : peerObservableList) {
      if (!newPeerList.contains(p)) {
        peerObservableList.remove(p);
      }
    }

    for (Peer p : newPeerList) {
      if (!peerObservableList.contains(p)) {
        peerObservableList.add(p);
      }
		}
	}
}
