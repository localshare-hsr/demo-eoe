package ch.hsr.epj.localshare.demo.logic;

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

    public void setFriendlyName(String name) {
        friendlyName = name;
    }

    public String getFriendlyName() {
        return friendlyName;
    }


}