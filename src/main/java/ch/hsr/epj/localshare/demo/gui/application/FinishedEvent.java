package ch.hsr.epj.localshare.demo.gui.application;

import javafx.event.EventType;

public class FinishedEvent extends CustomEvent {

  public static final EventType<CustomEvent> FINISH = new EventType(CUSTOM_EVENT_TYPE,
      "FinishedEvent");

  private final int param;

  public FinishedEvent(int param) {
    super(FINISH);
    this.param = param;
  }

  @Override
  public void invokeHandler(MyCustomEventHandler handler) {
    handler.onFinishedEvent(param);
  }

}
