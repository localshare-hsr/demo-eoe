package ch.hsr.epj.localshare.demo.gui.application;

import java.net.URL;
import java.util.ResourceBundle;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
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
		
		// add Peers
		peerObservableList.addAll(
			new Peer("10.0.0.1", "domi"),
			new Peer("10.0.0.2", "martin"),
			new Peer("10.0.0.3", "pascal")
		);
		
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listView.setItems(peerObservableList);
		listView.setCellFactory(peerListView -> new PeerListViewCell());
	}

}
