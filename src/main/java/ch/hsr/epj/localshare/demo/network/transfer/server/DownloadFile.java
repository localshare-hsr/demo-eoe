package ch.hsr.epj.localshare.demo.network.transfer.server;


public class DownloadFile {

  private String ownerFriendlyName;
  private String fileName;
  private long fileSize;
  private String fileURL;

  public DownloadFile(String ownerFriendlyName, String fileName, long fileSize, String fileURL) {
    this.ownerFriendlyName = ownerFriendlyName;
    this.fileName = fileName;
    this.fileSize = fileSize;
    this.fileURL = fileURL;
  }

  public String getOwnerFriendlyName() {
    return ownerFriendlyName;
  }

  public String getFileName() {
    return fileName;
  }

  public long getFileSize() {
    return fileSize;
  }

  public String getFileURL() {
    return fileURL;
  }
}
