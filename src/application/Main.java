package application;
	

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.time.TimerAction;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends GameApplication {
	private List<Entity> enemyList;
	private TimerAction timerAction;
	private List<Point2D> path;
	
	//Executes at the start of the game
	protected void initGame() {
		GameWorld world = getGameWorld();
		
		//Set the background
		Image image1 = new Image("/636460990.jpg", true);
		ImageView iv1 = new ImageView();
        iv1.setImage(image1);
        EntityView newView = new EntityView(iv1);
		getGameScene().addGameView(newView);
		
		//Spawn enemies
		enemyList = new ArrayList<Entity>();
		EnemyFactory.spawn(0, world, enemyList);
		
		//Create the path, this will change once Sheek makes the Path class
		path = new ArrayList<Point2D>();
		path.add(new Point2D(200, 318));
		path.add(new Point2D(200, 134));
		path.add(new Point2D(445, 134));
		path.add(new Point2D(445, 559));
		path.add(new Point2D(687, 559));
		path.add(new Point2D(687, 318));
		path.add(new Point2D(985, 318));
	    Point2D newPoint = new Point2D(300, 500);
	    
	    //Enemy timer, controls all enemies
	    timerAction = getMasterTimer().runAtInterval(() -> {
	    	
	    	for (Entity i:enemyList) {
	    		
	    		//Retrieves each enemy
	    		Enemy newEnemy = (Enemy) i;
	    		
	    		Point2D currentPos = newEnemy.getPosition();
	    		Point2D destination = path.get(newEnemy.getNum());
	    		
	    		if (currentPos.equals(destination)) {
	    			
	    			//Increment to the next position
	    			newEnemy.increment();
	    			if (newEnemy.getNum() == path.size()) {
	    				i.removeFromWorld();
	    				enemyList.remove(i);
	    				break;
	    			}
	    			
	    		} else if (currentPos.getX()-destination.getX() < 0) {
	    			newEnemy.setRotation(0);
	    		} else if (currentPos.getY()-destination.getY() < 0) {
	    			newEnemy.setRotation(90);
	    		} else if (currentPos.getY()-destination.getY() > 0) {
	    			newEnemy.setRotation(270);
	    		}
	    		//Move towards destination
		    	i.translateTowards(path.get(newEnemy.getNum()), 1);
	    	}
	    	
	    }, Duration.millis(0.5));
	}

	@Override
	protected void initSettings(GameSettings settings) {
		// TODO Auto-generated method stub
		settings.setWidth(1307);
	    settings.setHeight(719);
	    settings.setTitle("LASER DEFENSE");
	    settings.setVersion("0.1");
	}
}
