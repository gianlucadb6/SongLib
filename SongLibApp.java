//gfd15-Gianluca Delbarba
//ljg116-Lucas Geiselman

package app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import view.SongLibController;


public class SongLibApp extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/songlib.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		SongLibController listController = loader.getController();
		listController.start(primaryStage);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
	

}
