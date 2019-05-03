package ch.hsr.epj.localshare.demo;

import ch.hsr.epj.localshare.demo.logic.environment.StartupMethods;
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

public class Main extends Application {

  private static final Logger logger = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    if (StartupMethods.startupCheck()) {
      Parent root =
          FXMLLoader.load(
              Objects.requireNonNull(
                  getClass().getClassLoader().getResource("fxml/StartupView.fxml")));
      primaryStage.setTitle("Startup Window");
      primaryStage.setScene(new Scene(root, 800, 600));
      primaryStage.show();
    } else {
      StartupMethods.loadConfig();
      Parent root =
          FXMLLoader.load(
              Objects.requireNonNull(
                  getClass().getClassLoader().getResource("fxml/MainWindowView.fxml")));
      primaryStage.setTitle("LocalShare");
      Image Icon = new Image("file:main/resources/icon.png");
      primaryStage.getIcons().add(Icon);
      primaryStage.setScene(new Scene(root, 800, 700));
      primaryStage.show();
    }

    primaryStage.setOnCloseRequest(
        event -> {
          JSONParser parser = new JSONParser();
          parser.saveAllToJSON();
          try {
            parser.writeJSONToDisk();
          } catch (Exception e) {
            logger.log(Level.WARNING, "Unable to parse config", e);
          }
        });
  }
}
