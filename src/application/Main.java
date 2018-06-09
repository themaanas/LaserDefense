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
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.time.TimerAction;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
	UserAction hitBall = new UserAction("Hit") {
	    @Override
	    protected void onActionBegin() {
	        // action just started (key has just been pressed), play swinging animation
	    	
	    }

	    @Override
	    protected void onAction() {
	        // action continues (key is held), increase swing power
	    	
	    }

	    @Override
	    protected void onActionEnd() {
	        // action finished (key is released), play hitting animation based on swing power
	    	Laser.lasah(enemyList, getGameWorld(), getInput().getMousePositionWorld());
	    }
	};
	@Override
	protected void initInput() {
	    Input input = getInput();

	    input.addAction(hitBall, MouseButton.PRIMARY);
	}
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
		EnemyFactory.spawn(0, world, enemyList, 10, 50, 4, 0);
	}

	@Override
	protected void initSettings(GameSettings settings) {
		// TODO Auto-generated method stub
		settings.setWidth(1307);
	    settings.setHeight(719);
	    settings.setTitle("LASER DEFENSE");
	    settings.setVersion("0.1");
	}
	
	@Override
	protected void initPhysics() {
	    getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.LASER) {

	        // order of types is the same as passed into the constructor
	        protected void onCollisionBegin(Entity enemy, Entity laser) {
//	        	 getGameScene().getViewport().shake(8);
	            laser.removeFromWorld();
	            enemy.removeFromWorld();
	            enemyList.remove(enemy);
	        }
	    });
	}
}
