package ch.hsr.epj.localshare.demo.gui.application;

import javafx.event.EventHandler;

public abstract class MyCustomEventHandler implements EventHandler<CustomEvent> {

  public abstract void onFinishedEvent(int param0);

  @Override
  public void handle(CustomEvent event) {
    event.invokeHandler(this);
  }
}
