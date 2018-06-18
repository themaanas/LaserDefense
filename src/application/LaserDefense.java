package application;
	
	
import java.util.List;

import org.controlsfx.control.Notifications;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.gameplay.GameState;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.scene.GameScene;
import com.almasb.fxgl.settings.GameSettings;

import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class LaserDefense extends GameApplication {
	private List<Entity> enemyList;
	private boolean placeDefenseMode = false;
	private IntegerProperty livesProperty;
	private IntegerProperty moneyProperty;
	private final Label livesLabel = new Label("");
	private final Label moneyLabel = new Label("");
	private final Label waveLabel = new Label("");
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
	private static int waveNum = 0;
	private Font systemFont = Font.loadFont(LaserDefense.class.getResourceAsStream("pixelated.ttf"), 20);
	
	//waves
	private static final Wave[][] waveArr = new Wave[][] {
		{new Wave(1,20,50,0)},
		{new Wave(1,30,50,0)},
		{new Wave(1,20,50,0), new Wave(2,5,50,100)},
		{new Wave(1,30,50,0), new Wave(2,15,50,100)},
		{new Wave(1,5,50,0), new Wave(2,25,50,100)},
		{new Wave(1,15,50,0), new Wave(2,15,50,100), new Wave(3,4,50,200)},
		{new Wave(1,20,50,0), new Wave(2,25,50,100), new Wave(3,5,50,200)},
		{new Wave(1,10,50,0), new Wave(2,20,50,100), new Wave(3,14,50,200)},
		{new Wave(3,30,50,0)},
		{new Wave(1,102,50,0)},
		{new Wave(1,10,50,0), new Wave(2,10,50,100), new Wave(3,12,50,200), new Wave(4,2,50,300)},
		{new Wave(2,5,50,0), new Wave(2,10,50,100), new Wave(3,5,50,200)},
		{new Wave(1,100,50,0), new Wave(3,23,50,100), new Wave(4,4,50,200)},
		{new Wave(1,50,50,0), new Wave(2,15,50,100), new Wave(3,10,50,200), new Wave(4,9,50,300)},
		{new Wave(1,100,50,0), new Wave(2,35,50,100), new Wave(3,25,50,200), new Wave(4,20,50,300)}
	};
	
	//listens for changes to update the lives label
	private final ChangeListener livesListener = new ChangeListener() {
		@Override
		public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
			livesLabel.setText("" + newValue);
			
		}
    };
    
    //listens for changes to update the money label
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
	
	//lot of code but little results; creates labels/buttons and displays them
	protected void initUI() {
		livesLabel.setLayoutX(1100);
		livesLabel.setLayoutY(30);
		livesLabel.setTextFill(Color.WHITE);
		livesLabel.setFont(systemFont);
		
		moneyLabel.setLayoutX(1225);
		moneyLabel.setLayoutY(30);
		moneyLabel.setTextFill(Color.WHITE);
		moneyLabel.setFont(systemFont);
		
		//Binds the lives and money labels to the variables and sets them equal to 50 and 650, respectively
		state.setValue("lives", 0);
		state.setValue("money", 0);
		
		livesProperty = state.intProperty("lives");
		livesProperty.addListener(livesListener);
		
		moneyProperty = state.intProperty("money");
		moneyProperty.addListener(moneyListener);
		
		state.setValue("lives", 50);
		state.setValue("money", 650);
		
		Button spawnButton = new Button("");
		spawnButton.setGraphic(new ImageView("/assets/textures/defense.png"));
		spawnButton.setStyle("-fx-background-color: none;");
		spawnButton.setLayoutX(1035);
		spawnButton.setLayoutY(115);
		spawnButton.setOnAction(actionEvent ->  {
			scene.setCursor("defenseCursor.png", new Point2D(-35,-35));
			placeDefenseMode = true;
		});
		
		
		waveButton = new Button("");
		waveButton.setLayoutX(1122);
		waveButton.setLayoutY(113);
		waveButton.setScaleX(0.6);
		waveButton.setScaleY(0.6);
		waveButton.setGraphic(new ImageView("/assets/textures/nextWave.png"));
		waveButton.setStyle("-fx-background-color: none;");
		waveButton.setOnAction(actionEvent ->  {
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
		upgradeLabel.setFont(Font.loadFont(LaserDefense.class.getResourceAsStream("pixelated.ttf"), 30));
		upgradeLabel.setTextFill(Color.WHITE);
		
		upgradeButton = new Button("");
		upgradeButton.setLayoutX(1090);
		upgradeButton.setLayoutY(380);
		upgradeButton.setStyle("-fx-background-color: #63c64f");
		upgradeButton.setFont(Font.loadFont(LaserDefense.class.getResourceAsStream("pixelated.ttf"), 30));
		upgradeButton.setTextFill(Color.WHITE);
		upgradeButton.setFont(systemFont);
		upgradeButton.setVisible(false);
		
		scene.addUINode(waveButton);
		scene.addUINode(waveLabel);
		scene.addUINode(upgradeButton);
		scene.addUINode(upgradeLabel);
		scene.addUINode(livesLabel);
		scene.addUINode(moneyLabel);
		scene.addUINode(spawnButton);
		updateWaveLabel();
	}
	
	//adds input method (user clicks)
	protected void initInput() {
	    Input input = getInput();
	    input.addAction(userClicked, MouseButton.PRIMARY);
	}
	
	//game stuff gets initialized
	protected void initGame() {
		state = getGameState();
		world = getGameWorld();
		scene = getGameScene();
		
		defenses = new DefenseFactory(state, world, scene, getAudioPlayer());
		enemies = new EnemyFactory(world, state);
		
		Image image1 = new Image("assets/backgrounds/newBack.png", true);
		ImageView iv1 = new ImageView();
        iv1.setImage(image1);
        EntityView newView = new EntityView(iv1);
		getGameScene().addGameView(newView);
	}

	//basic settings
	protected void initSettings(GameSettings settings) {
		settings.setWidth(1307);
	    settings.setHeight(719);
	    settings.setTitle("Laser Defense");
	    settings.setVersion("");
	}
	
	//collisions
	protected void initPhysics() {
	    getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.LASER) {
	        protected void onCollisionBegin(Entity enemy, Entity laser) {
	            laser.removeFromWorld();
	            if (enemy.getComponent(EnemyControl.class).minusHealth() == 0) {
	            	addMoney(enemy.getComponent(EnemyControl.class).getReward());
	            	enemy.removeFromWorld();
	            	if(world.getEntitiesByType(EntityType.ENEMY).size() == 0)
						enableButton();
	            	Entities.builder()
	                .at(enemy.getX()-10, enemy.getY()-10)
	                .viewFromAnimatedTexture("explosionSheet.png", 48, Duration.seconds(0.2), false, true)
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
		addMoney(100 + waveNum-1);
		if (waveNum == waveArr.length) {
			win();
		}
		waveButton.setDisable(false);
	}
	
	//Shows the menu for defense upgrades
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
	
	//Hides the menu for defense upgrades
	public static void hideDefenseMenu() {
		upgradeButton.setVisible(false);
		upgradeLabel.setVisible(false);
		defenseToUpgrade.getComponent(DefenseControl.class).showRadius(false);
		defenseToUpgrade.getComponent(DefenseControl.class).setSelectedTexture(false);
		menuIsVisible = false;
	}
	
	//goes to the next wave
	public void newWave() {
		if(waveArr.length > waveNum) {
			for (Wave wave:waveArr[waveNum]) {
				enemies.spawn(wave);
			}
		}
		waveNum++;
		updateWaveLabel();
	}
	
	//updates the wave number label
	public void updateWaveLabel() {
		waveLabel.setText("WAVE\n" + waveNum);
	}
	
	//checks if point is near another defense, in this case 60 pixels
	public static boolean isNotNearDefense(Point2D start) {
		for (Entity newEntity: world.getEntitiesByType(EntityType.DEFENSE)) {
			System.out.println(start.distance(newEntity.getCenter()));
			if (start.distance(newEntity.getCenter()) < 60) {
				return false;
			}
		}
		return true;
	}
	
	//displays the win screen
	public static void win() {
		scene.clear();
		LaserFactory.setGameOver();
		for (Entity i:world.getEntitiesByType(EntityType.DEFENSE))
			i.getView().setVisible(false);
		for (Entity i:world.getEntitiesByType(EntityType.ENEMY))
			i.getView().setVisible(false);
		scene.addGameView(new EntityView(new ImageView("assets/backgrounds/winScreen.jpg")));
	}
		
	//displays lose screen
	public static void lose() {
		scene.clear();
		LaserFactory.setGameOver();
		for (Entity i:world.getEntitiesByType(EntityType.DEFENSE))
			i.getView().setVisible(false);
		for (Entity i:world.getEntitiesByType(EntityType.ENEMY))
			i.getView().setVisible(false);
		scene.addGameView(new EntityView(new ImageView("/assets/backgrounds/loseScreen.jpg")));
	}
		
	public static void main(String[] args) {
		launch(args);
	}
	
}