package ch.hsr.epj.localshare.demo.logic.environment;

public class OSDetector {

  private String os;

  public String getOSSpecificLocalShareDirectory() {
    String localshareDir;
    if (isWindows()) {
      localshareDir = getOSSpecificHomeDirectory() + "\\LocalShare";
    } else {
      localshareDir = getOSSpecificHomeDirectory() + "/LocalShare";
    }
    return localshareDir;
  }

  public String getOSSpecificDownloadDirectory() {
    String downloadDir;
    if (isWindows()) {
      downloadDir = getOSSpecificLocalShareDirectory() + "\\download\\";
    } else {
      downloadDir = getOSSpecificLocalShareDirectory() + "/download/";
    }
    return downloadDir;
  }

  public String getOSSpecificConfigDirectory() {
    String configDir;
    if (isWindows()) {
      configDir = getOSSpecificLocalShareDirectory() + "\\config\\";
    } else {
      configDir = getOSSpecificLocalShareDirectory() + "/config/";
    }
    return configDir;
  }

  public String getOSSpecificHomeDirectory() {
    String homePath = "";
    if (isWindows()) {
      homePath = System.getenv("USERPROFILE");
    }
    if (isLinux()) {
      homePath = System.getProperty("user.home");
    }
    return homePath;
  }

  public boolean isWindows() {
    return getOsName().startsWith("Windows");
  }

  public boolean isLinux() {
    return getOsName().startsWith("Linux");
  }

  public String getOsName() {
    if (os == null || os.equals("")) {
      os = System.getProperty("os.name");
    }
    return os;
  }

}
