package ch.hsr.epj.localshare.demo.logic.environment;

public class User {

  private static User instance;

  private String friendlyName;

  private User() {
  }

  public static User getInstance() {
    if (User.instance == null) {
      User.instance = new User();
    }
    return User.instance;
  }

  public String getFriendlyName() {
    return friendlyName;
  }

  public void setFriendlyName(String name) {
    friendlyName = name;
  }
}

