package application;
	
	
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.Notifications;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.extra.entity.components.ExpireCleanComponent;
import com.almasb.fxgl.extra.entity.components.HealthComponent;
import com.almasb.fxgl.gameplay.GameState;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.scene.GameScene;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.TimerAction;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static com.almasb.fxgl.app.DSLKt.texture;

public class LaserGame extends GameApplication {
	private List<Entity> enemyList;
	private TimerAction timerAction;	
	private boolean placeDefenseMode = false;
	private IntegerProperty livesProperty;
	private IntegerProperty moneyProperty;
	private final Label livesLabel = new Label("");
	private final Label moneyLabel = new Label("");
	private DefenseFactory defenses;
	private EnemyFactory enemies;
	private static GameState state;
	private static GameWorld world;
	private static GameScene scene;
	private static Button waveButton;
	private static Button upgradeButton;
	private static Label upgradeLabel;
	private static Boolean menuIsVisible = false;
	private static Entity defenseToUpgrade;
	private Font systemFont = Font.loadFont(LaserGame.class.getResourceAsStream("pixelated.ttf"), 20);
	
	private final Label waveLabel = new Label("");
	private static final Wave[][] waveArr = new Wave[][] {
		{new Wave(1,5,50,0)},
		{new Wave(1,5,50,0), new Wave(2,5,50,0)}
	};
	private static int waveNum = 0;
	
	private final ChangeListener livesListener = new ChangeListener() {
		@Override
		public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
			livesLabel.setText("" + newValue);
			
		}
    };
    
    private final ChangeListener moneyListener = new ChangeListener() {
		@Override
		public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
			moneyLabel.setText("" + newValue);
		}
    };
	
	UserAction userClicked = new UserAction("Hit") {
	    @Override
	    protected void onActionEnd() {
	    	if (menuIsVisible) {
	    		hideDefenseMenu();
	    	}
	    	if (placeDefenseMode) {
	    		defenses.spawn(getInput().getMousePositionWorld());
	    		scene.getRoot().setCursor(Cursor.DEFAULT);
	    		placeDefenseMode = false;
	    	}	    	
	    }
	};
	
	protected void initUI() {
		
		livesLabel.setLayoutX(1100);
		livesLabel.setLayoutY(30);
		livesLabel.setTextFill(Color.WHITE);
		livesLabel.setFont(systemFont);
		moneyLabel.setLayoutX(1225);
		moneyLabel.setLayoutY(30);
		moneyLabel.setFont(systemFont);
		moneyLabel.setTextFill(Color.WHITE);
		scene.addUINode(livesLabel);
		scene.addUINode(moneyLabel);
		
		state.setValue("lives", 0);
		state.setValue("money", 0);
		
		livesProperty = state.intProperty("lives");
		livesProperty.addListener(livesListener);
		
		moneyProperty = state.intProperty("money");
		moneyProperty.addListener(moneyListener);
		
		state.setValue("lives", 5);
		state.setValue("money", 650);
		
		
		Button spawnButton = new Button("");
		spawnButton.setGraphic(new ImageView("/assets/textures/turret1_60x60.png"));
		spawnButton.setStyle("-fx-background-color: none;");
		spawnButton.setLayoutX(1035);
		spawnButton.setLayoutY(115);
		spawnButton.setOnAction(actionEvent ->  {
			scene.setCursor("turret1_60x60.png", new Point2D(-35,-35));
			placeDefenseMode = true;
		});
		scene.addUINode(spawnButton);
		
		waveButton = new Button("");
		waveButton.setLayoutX(1122);
		waveButton.setLayoutY(113);
		waveButton.setScaleX(0.6);
		waveButton.setScaleY(0.6);
		waveButton.setGraphic(new ImageView("/assets/textures/nextWave.png"));
		waveButton.setStyle("-fx-background-color: none;");
		waveButton.setOnAction(actionEvent ->  {
//			winModal();
			newWave();
			waveButton.setDisable(true);
			
		});
		waveLabel.setLayoutX(1228);
		waveLabel.setLayoutY(124);
		waveLabel.setTextFill(Color.WHITE);
		waveLabel.setFont(systemFont);
		waveLabel.setTextAlignment(TextAlignment.CENTER);
		
		upgradeLabel = new Label("");
		upgradeLabel.setLayoutX(1060);
		upgradeLabel.setLayoutY(255);
		upgradeLabel.setFont(Font.loadFont(LaserGame.class.getResourceAsStream("pixelated.ttf"), 30));
		upgradeLabel.setTextFill(Color.WHITE);
		
		upgradeButton = new Button("asdf;khjasdlkcja");
		upgradeButton.setLayoutX(1090);
		upgradeButton.setLayoutY(380);
		upgradeButton.setStyle("-fx-background-color: #63c64f");
		upgradeButton.setFont(Font.loadFont(LaserGame.class.getResourceAsStream("pixelated.ttf"), 30));
		upgradeButton.setTextFill(Color.WHITE);
		upgradeButton.setFont(systemFont);
		upgradeButton.setVisible(false);
		scene.addUINode(waveButton);
		scene.addUINode(waveLabel);
		scene.addUINode(upgradeButton);
		scene.addUINode(upgradeLabel);
		updateWaveLabel();
	}
	
	public static void winModal() {
		scene.clear();
		scene.addGameView(new EntityView(new ImageView("winScreen.jpg")));
	}
	
	public static void loseModal() {
		scene.clear();
		scene.addGameView(new EntityView(new ImageView("loseScreen.jpg")));
	}
	
	@Override
	protected void initInput() {
	    Input input = getInput();
	    input.addAction(userClicked, MouseButton.PRIMARY);
	}
	//Executes at the start of the game
	protected void initGame() {
		 
		state = getGameState();
		world = getGameWorld();
		scene = getGameScene();
		
		defenses = new DefenseFactory(state, world, scene, enemyList);
		enemies = new EnemyFactory(world, state);
		
	    
//	    System.out.println(livesProperty.get());
		
//		String message = "Blue enemies only need one hit to die.";
//    	FXGL.getNotificationService().pushNotification(message);
    	
		//Set the background
		Image image1 = new Image("/newBack.png", true);
		ImageView iv1 = new ImageView();
        iv1.setImage(image1);
        EntityView newView = new EntityView(iv1);
		getGameScene().addGameView(newView);
		
		Rectangle rect = new Rectangle(153,86,60,188);
		rect.setFill(Color.BLUE);
		
		
//		enemies.spawn(1, 50, 50, 3, 0);
	}

	@Override
	protected void initSettings(GameSettings settings) {
		// TODO Auto-generated method stub
		settings.setWidth(1307);
	    settings.setHeight(719);
	    settings.setTitle("Laser Defense");
	    settings.setVersion("");
	}
	
	@Override
	protected void initPhysics() {
	    getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.LASER) {

	        protected void onCollisionBegin(Entity enemy, Entity laser) {
//	        	 getGameScene().getViewport().shake(1);
	            laser.removeFromWorld();
	            if (enemy.getComponent(EnemyControl.class).minusHealth() == 0) {
	            	addMoney(enemy.getComponent(EnemyControl.class).getReward());
	            	enemy.removeFromWorld();
	            	if(world.getEntitiesByType(EntityType.ENEMY).size() == 0)
						enableButton();
	            	Entities.builder()
	                .at(enemy.getX()-10, enemy.getY()-10)
	                .viewFromAnimatedTexture("explosion_blue.png", 48, Duration.seconds(0.2), false, true)
	                .buildAndAttach();
	            	
	            }
	            
	            
	        }
	    });
	}
	public static void addMoney(int money) {
		state.setValue("money", state.getInt("money")+money);
	}
	public static void subtractMoney(int money) {
		state.setValue("money", state.getInt("money")-money);
	}
	public static void enableButton() {
		System.out.println(waveNum);
		if (waveNum == waveArr.length) {
			winModal();
		}
		waveButton.setDisable(false);
	}
	public static void showDefenseMenu(Entity entity) {
		menuIsVisible = true;
		DefenseControl defenseComponent = entity.getComponent(DefenseControl.class);
		upgradeLabel.setVisible(true);
		upgradeButton.setVisible(true);
		upgradeLabel.setText("Defense Level: " + defenseComponent.getLevel());
		upgradeButton.setText("upgrade: $" + (defenseComponent.getLevel()*10));
		defenseToUpgrade = entity;
		upgradeButton.setOnAction(actionEvent ->  {
			if (state.getInt("money") < defenseComponent.getLevel()*10) {
				Notifications.create()
		         .title("BIG OOF")
		         .text("Ur broke.")
		         .owner(scene.getRoot())
		         .showError();
			} else {
				subtractMoney(defenseComponent.getLevel()*10);
				defenseComponent.upgrade();
				upgradeLabel.setText("Defense Level: " + defenseComponent.getLevel());
				upgradeButton.setText("upgrade: $" + (defenseComponent.getLevel()*10));
			}
		});
		defenseComponent.showRadius(true);
		defenseComponent.setSelectedTexture(true);
		
	}
	
	public static void hideDefenseMenu() {
		upgradeButton.setVisible(false);
		upgradeLabel.setVisible(false);
		defenseToUpgrade.getComponent(DefenseControl.class).showRadius(false);
		defenseToUpgrade.getComponent(DefenseControl.class).setSelectedTexture(false);
		menuIsVisible = false;
	}
	public void newWave() {
		if(waveArr.length > waveNum) {
			for (Wave wave:waveArr[waveNum]) {
				enemies.spawn(wave);
				
				
			}
		}
		waveNum++;
		updateWaveLabel();
	}
	public void updateWaveLabel() {
		waveLabel.setText("WAVE\n" + waveNum);
	}
	public static boolean isNotNearDefense(Point2D start) {
		for (Entity newEntity: world.getEntitiesByType(EntityType.DEFENSE)) {
			System.out.println(start.distance(newEntity.getCenter()));
			if (start.distance(newEntity.getCenter()) < 60) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
	
}