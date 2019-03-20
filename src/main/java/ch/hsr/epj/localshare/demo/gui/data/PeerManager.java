package ch.hsr.epj.localshare.demo.gui.data;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PeerManager {
	private int id;
	private ArrayList<Peer> peerList;
	

	public PeerManager(int id) {
		this.id = id;
		peerList = new ArrayList<Peer>();
	}
	
	public void addPeer(Peer p) {
		peerList.add(p);
	}
	
	public ArrayList<Peer> getList(){
		return peerList;
	}
	
}
