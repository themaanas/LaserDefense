package application;

import org.controlsfx.control.Notifications;

import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.gameplay.GameState;
import com.almasb.fxgl.scene.GameScene;

import javafx.geometry.Point2D;

public class DefenseFactory {
	private GameState state;
	private GameWorld world;
	private GameScene scene;
	private AudioPlayer player;
	
	public DefenseFactory(GameState state, GameWorld world, GameScene scene, AudioPlayer player) {
		this.state = state;
		this.world = world;
		this.scene = scene;
		this.player = player;
	}
	
	public void spawn(Point2D start) {
		if (Bounds.isInBounds(start.getX()-30,start.getY()-30)) {
			//checks if its on the path
			Notifications.create()
	         .title("OOF")
	         .text("You can't put it on the path smh.")
	         .owner(scene.getRoot())
	         .showError();
		} else if (state.getInt("money") < 150) {
			//checks if player has enough money
			Notifications.create()
	         .title("BIG OOF")
	         .text("Ur broke boi.")
	         .owner(scene.getRoot())
	         .showError();
		} else if (!LaserDefense.isNotNearDefense(start)) {
			//checks if its not near any other defenses
			Notifications.create()
	         .title("OOF")
	         .text("You can't put it that close to another defense.")
	         .owner(scene.getRoot())
	         .showError();
		} else {
			Entity defense = new Entity();
			defense.setType(EntityType.DEFENSE);
			defense.setX(start.getX());
			defense.setY(start.getY());
			defense.setViewFromTextureWithBBox("defense.png");
			defense.addComponent(new DefenseControl(world, 1, player));
			world.addEntity(defense);
			LaserDefense.subtractMoney(150);
		}
	}
}
