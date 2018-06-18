package application;

import java.util.List;

import org.controlsfx.control.Notifications;

import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.gameplay.GameState;
import com.almasb.fxgl.scene.GameScene;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

public class DefenseFactory {
	private GameState state;
	private GameWorld world;
	private GameScene scene;
	private List<Entity> enemyList;
	
	public DefenseFactory(GameState state, GameWorld world, GameScene scene, List<Entity> enemyList) {
		this.state = state;
		this.world = world;
		this.scene = scene;
		this.enemyList = enemyList;
	}
	
	public void spawn(Point2D start) {
		// TODO Auto-generated constructor stub
		if (Bounds.isInBounds(start.getX()-30,start.getY()-30)) {
			Notifications.create()
	         .title("OOF")
	         .text("You can't put it on the path smh.")
	         .owner(scene.getRoot())
	         .showError();
		} else if (state.getInt("money") < 150) {
			Notifications.create()
	         .title("BIG OOF")
	         .text("Ur broke.")
	         .owner(scene.getRoot())
	         .showError();
		} else if (!LaserGame.isNotNearDefense(start)) {
			Notifications.create()
	         .title("OOF")
	         .text("You can't put it that close to another defense.")
	         .owner(scene.getRoot())
	         .showError();
		} else {
			ImageView img = new ImageView("base.png");
			img.setX(start.getX());
			img.setY(start.getY());
			EntityView view = new EntityView(img);
//			scene.addGameView(view);
			Entity enemyOne = new Entity();
			enemyOne.setType(EntityType.DEFENSE);
			enemyOne.setX(start.getX());
			enemyOne.setY(start.getY());
			enemyOne.setViewFromTextureWithBBox("turret1_60x60.png");
			enemyOne.addComponent(new DefenseControl(world, 1));
			world.addEntity(enemyOne);
			LaserGame.subtractMoney(150);
		}
	}
}
