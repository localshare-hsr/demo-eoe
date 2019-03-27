package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
import ch.hsr.epj.localshare.demo.logic.DiscoveryController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class MainWindowController implements Initializable, PeerUpdaterIF {
	
	@FXML
	private ListView<Peer> listView;
	
	private ObservableList<Peer> peerObservableList;
	
	public MainWindowController() {
		
		peerObservableList = FXCollections.observableArrayList();
		DiscoveryController discoveryController = new DiscoveryController(this);

	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listView.setItems(peerObservableList);
		listView.setCellFactory(peerListView -> new PeerListViewCell());
		listView.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (event.getGestureSource() != listView
						&& event.getDragboard().hasFiles()) {
					/* allow for both copying and moving, whatever user chooses */
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			}
		});

		listView.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasFiles()) {
					System.out.println(db.getFiles().toString());
					success = true;
				}
				/* let the source know whether the string was successfully
				 * transferred and used */
				event.setDropCompleted(success);

				event.consume();
			}
		});

	}

	@Override
	public void update(String[] event) {
		for (String ip : event) {
      Peer newPeer = new Peer(ip, "LS user");
      if (!peerObservableList.contains(newPeer)) {
        peerObservableList.add(newPeer);
      }
		}
	}
}
