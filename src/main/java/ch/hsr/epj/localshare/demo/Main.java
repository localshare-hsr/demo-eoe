package ch.hsr.epj.localshare.demo;

import ch.hsr.epj.localshare.demo.gui.application.MainWindowController;
import ch.hsr.epj.localshare.demo.logic.environment.ConfigManager;
import ch.hsr.epj.localshare.demo.logic.environment.OSDetector;
import ch.hsr.epj.localshare.demo.logic.environment.StartupMethods;
import ch.hsr.epj.localshare.demo.logic.environment.User;
import ch.hsr.epj.localshare.demo.persistence.JSONParser;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;
import jfxtras.styles.jmetro8.JMetro.Style;


public class Main extends Application {

  private static final Logger logger = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    OSDetector osDetector = new OSDetector();
    StartupMethods startupMethods = new StartupMethods(osDetector);
    if (startupMethods.startupCheck()) {
      Parent root =
          FXMLLoader.load(
              Objects.requireNonNull(
                  getClass().getClassLoader().getResource("fxml/StartupView.fxml")));
      primaryStage.setTitle("Startup Window");
      Image icon = new Image("file:icon.png");
      primaryStage.getIcons().add(icon);
      new JMetro(JMetro.Style.LIGHT).applyTheme(root);
      primaryStage.setScene(new Scene(root, 1000, 600));
      primaryStage.show();
    } else {
      startupMethods.loadConfig();
      Parent root =
          FXMLLoader.load(
              Objects.requireNonNull(
                  getClass().getClassLoader().getResource("fxml/MainWindowView.fxml")));
      primaryStage.setTitle("LocalShare");
      new JMetro(Style.LIGHT).applyTheme(root);
      Image icon = new Image("file:icon.png");
      primaryStage.getIcons().add(icon);
      Scene scene = new Scene(root, 1200, 700);
      primaryStage.setScene(scene);
      primaryStage.show();
    }

    primaryStage.setOnCloseRequest(
        event -> {
          MainWindowController.shutdownApplication();
          try {
            String configPath = ConfigManager.getInstance().getConfigPath();
            String downloadPath = ConfigManager.getInstance().getDownloadPath();
            String friendlyName = User.getInstance().getFriendlyName();
            JSONParser parser = new JSONParser(configPath, friendlyName, downloadPath);
            parser.writeData();
          } catch (Exception e) {
            logger.log(Level.WARNING, "Unable to parse config", e);
          }
        });
  }
}
