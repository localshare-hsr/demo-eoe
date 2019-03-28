package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
import ch.hsr.epj.localshare.demo.logic.DiscoveryController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainWindowController implements Initializable, PeerUpdaterIF {
	
	@FXML
	private AnchorPane preferencesRootPane;

	@FXML
	private ListView<Peer> listView;

	@FXML
	private ObservableList<Peer> peerObservableList;

	@FXML
	private void handlePreferencesButtonAction(ActionEvent event) throws IOException {
		AnchorPane preferencesPane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/PreferencesView.fxml"));
		System.out.println(preferencesRootPane);
		preferencesRootPane.getChildren().setAll(preferencesPane);
	}
	
	public MainWindowController() {
		peerObservableList = FXCollections.observableArrayList();
		DiscoveryController discoveryController = new DiscoveryController(this);
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listView.setItems(peerObservableList);
		listView.setCellFactory(peerListView -> new PeerListViewCell());
	}

	@Override
	public void update(String[] event) {
		for (String ip : event) {
      Peer newPeer = new Peer(ip, "LS user", "", "aasd98asdas8d7");
      if (!peerObservableList.contains(newPeer)) {
        peerObservableList.add(newPeer);
      }
		}
	}
}
