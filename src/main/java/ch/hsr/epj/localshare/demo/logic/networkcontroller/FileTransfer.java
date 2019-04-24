package ch.hsr.epj.localshare.demo.logic.networkcontroller;

import ch.hsr.epj.localshare.demo.gui.presentation.Peer;
import java.net.URL;
import javafx.scene.control.ProgressBar;

public class FileTransfer {

  private Peer peer;
  private URL path;
  private ProgressBar progress;

  public FileTransfer(Peer peer, URL path, ProgressBar progress) {
    this.peer = peer;
    this.path = path;
    this.progress = progress;
  }


  public Peer getPeer() {
    return peer;
  }

  public URL getPath() {
    return path;
  }

  public ProgressBar getProgress() {
    return progress;
  }


}
