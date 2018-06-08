package application;
	

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
    public void start(Stage primaryStage) throws Exception {
//		LaserGame gameLasah = new LaserGame("hi");
//	    gameLasah.startGame();
		//hi jon
		Rectangle rectangle = new Rectangle(100,100,Color.RED);
        Pane root = FXMLLoader.load(getClass().getResource("/laserDefense.fxml"));
        Pane newPane = new Pane(root, rectangle);
//        root.getChildren().addAll(rectangle);
        primaryStage.setScene(new Scene(newPane));
        primaryStage.show();
       
    }
}
