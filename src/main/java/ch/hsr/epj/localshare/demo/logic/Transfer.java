package ch.hsr.epj.localshare.demo.logic;

import ch.hsr.epj.localshare.demo.gui.data.Peer;
import java.net.URL;
import javafx.scene.control.ProgressBar;

public class Transfer {

  private Peer peer;
  private URL path;
  private ProgressBar progress;

  public Transfer(Peer peer, URL path, ProgressBar progress) {
    this.peer = peer;
    this.path = path;
    this.progress = progress;
  }


  public URL getURL() {
    return path;
  }


}
