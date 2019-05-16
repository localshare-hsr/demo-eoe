package ch.hsr.epj.localshare.demo.logic.environment;

public class OSDetectorMock extends OSDetector {

  private String configDirectory = "/foo/bar";

  @Override
  public String getOSSpecificLocalShareDirectory() {
    return "/foo/bar";
  }

  @Override
  public String getOSSpecificDownloadDirectory() {
    return "/foo/bar";
  }

  @Override
  public String getOSSpecificConfigDirectory() {
    return configDirectory;
  }

  @Override
  public String getOSSpecificHomeDirectory() {
    return "/foo/bar";
  }

  @Override
  public boolean isWindows() {
    return true;
  }

  @Override
  public boolean isLinux() {
    return true;
  }

  @Override
  public String getOsName() {
    return "Virtual OS";
  }

  public void setConfigDirectory(String configDirectory) {
    this.configDirectory = configDirectory;
  }
}
