package ch.hsr.epj.localshare.demo.gui.application;

import ch.hsr.epj.localshare.demo.network.transfer.LsHttpServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		 //Parent root = FXMLLoader.load(getClass().getResource("/views/MainWindowView.fxml"));
		 Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MainWindowView.fxml"));
		 primaryStage.setTitle("GUI Prototype");
	     primaryStage.setScene(new Scene(root, 800, 600));
	     primaryStage.show();
	}

	public static void main(String[] args) throws InterruptedException {
		Thread httpServerThread = new Thread(new LsHttpServer());
		httpServerThread.setDaemon(true);
		httpServerThread.start();
		launch(args);
		httpServerThread.join();
	}
}
