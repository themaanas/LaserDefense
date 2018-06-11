package application;

import java.util.List;

import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.scene.GameScene;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

public class DefenseFactory {
	
	public static void makePulse(List<Entity> enemyList, GameWorld world, Point2D start, AudioPlayer audio, GameScene scene) {
		if (!Bounds.isInBounds(start.getX(),start.getY())) {
			ImageView img = new ImageView("BaseCC_70x70.png");
			img.setX(start.getX()-35);
			img.setY(start.getY()-35);
			EntityView view = new EntityView(img);
			scene.addGameView(view);
			Entity enemyOne = new Entity();
			enemyOne.setType(EntityType.DEFENSE);
			enemyOne.setX(start.getX()-35);
			enemyOne.setY(start.getY()-35);
			enemyOne.setViewFromTextureWithBBox("Turret_I1_1_70x70.png");
			enemyOne.addComponent(new PulseControl(enemyList, world, audio));
			world.addEntity(enemyOne);
		}
	}
	
	public static void makeBurst(List<Entity> enemyList, GameWorld world, Point2D start, AudioPlayer audio, GameScene scene) {
		// TODO Auto-generated constructor stub
		if (!Bounds.isInBounds(start.getX()-30,start.getY()-30)) {
			ImageView img = new ImageView("base.png");
			img.setX(start.getX()-35);
			img.setY(start.getY()-35);
			EntityView view = new EntityView(img);
			scene.addGameView(view);
			Entity enemyOne = new Entity();
			enemyOne.setType(EntityType.DEFENSE);
			enemyOne.setX(start.getX()-35);
			enemyOne.setY(start.getY()-35);
			enemyOne.setViewFromTextureWithBBox("Turret_J3_70x70.png");
			enemyOne.addComponent(new BurstControl(enemyList, world, audio));
			world.addEntity(enemyOne);
			
		}
	}
}
