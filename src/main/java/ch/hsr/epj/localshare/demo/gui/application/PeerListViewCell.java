package ch.hsr.epj.localshare.demo.gui.application;

import java.io.IOException;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
		
		setText(null);
		setGraphic(gridPane);
	}
}
