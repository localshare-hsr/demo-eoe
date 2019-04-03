package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.logic.TransferController;

import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("/views/MainWindowView.fxml"));
        Parent root =
                FXMLLoader.load(Objects
                        .requireNonNull(getClass().getClassLoader().getResource("fxml/MainWindowView.fxml")));
        primaryStage.setTitle("GUI Prototype");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

	public static void main(String[] args) {
        TransferController transferController = new TransferController();
        transferController.serveFileInChannel("rocket.jpg", "sky");
        transferController.serveFileInPrivate("rocket.jpg");
		launch(args);
	}
}
