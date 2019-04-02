package ch.hsr.epj.localshare.demo.gui.data;

import ch.hsr.epj.localshare.demo.network.Discovery;
import java.util.ArrayList;

public class PeerManager {
	private ArrayList<Peer> peerList;

	public PeerManager() {
		peerList = new ArrayList<Peer>();
	}

	public void createPeers(){
		Discovery discovery = new Discovery();
		String[] ip_array = discovery.getIPAsArray();

		for(String ip : ip_array){
			Peer p = new Peer(ip, "", "", "0x0adsjk19adj1");
			addPeer(p);
		}
	}

  public void addPeer(Peer p) {
		peerList.add(p);
	}
	public ArrayList<Peer> getList(){
		return peerList;
	}
	
}
