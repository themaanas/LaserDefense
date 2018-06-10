package application;
	
	
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.extra.entity.components.ExpireCleanComponent;
import com.almasb.fxgl.extra.entity.components.HealthComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
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
import static com.almasb.fxgl.app.DSLKt.texture;

public class Main extends GameApplication {
	private List<Entity> enemyList;
	private TimerAction timerAction;	
	UserAction hitBall = new UserAction("Hit") {
	    @Override
	    protected void onActionBegin() {
	    	System.out.println(getInput().getMousePositionWorld());
	        // action just started (key has just been pressed), play swinging animation
	    	
	    }

	    @Override
	    protected void onAction() {
	        // action continues (key is held), increase swing power
	    	
	    }

	    @Override
	    protected void onActionEnd() {
	    	Defense.defense(enemyList, getGameWorld(), getInput().getMousePositionWorld(), getAudioPlayer());
	        // action finished (key is released), play hitting animation based on swing power
	    	
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
		String message = "Blue enemies only need one hit to die.";
    	FXGL.getNotificationService().pushNotification(message);
		//Set the background
		Image image1 = new Image("/636460990.jpg", true);
		ImageView iv1 = new ImageView();
        iv1.setImage(image1);
        EntityView newView = new EntityView(iv1);
		getGameScene().addGameView(newView);
		Rectangle rect = new Rectangle(153,86,60,188);
		rect.setFill(Color.BLUE);
//		getGameScene().addGameView(new EntityView(rect));
		//Spawn enemies
		enemyList = new ArrayList<Entity>();
//		Bounds.isInBounds(0, 0, getGameScene());
		EnemyFactory.spawn(0, world, enemyList, 50, 50, 3, 0);
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
//	        	 getGameScene().getViewport().shake(2);
	            laser.removeFromWorld();
	            if (enemy.getComponent(EnemyControl.class).minusHealth() == 0) {
	            	enemy.removeFromWorld();
	            	enemyList.remove(enemy);
	            	Entities.builder()
	                .at(enemy.getX()-10, enemy.getY()-10)
	                .viewFromAnimatedTexture("explosion_blue.png", 48, Duration.seconds(0.2), false, true)
	                .buildAndAttach();
	            }
	            
	            
	        }
	    });
	}
	
}
