package ch.hsr.epj.localshare.demo.gui.data;

public class Transfer {

  private double size;
  private String name;
  private boolean accepted;


  public Transfer(double size, String name) {
    this.size = size;
    this.name = name;
    accepted = false;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

  public double getSize() {
    return size;
  }

  public void setSize(double size) {
    this.size = size;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
