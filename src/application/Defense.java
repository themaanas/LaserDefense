package application;

import java.util.List;

import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.components.CollidableComponent;

import javafx.geometry.Point2D;

public class Defense extends Entity {

	public static void defense(List<Entity> enemyList, GameWorld world, Point2D start, AudioPlayer audio) {
		// TODO Auto-generated constructor stub
		if (!Bounds.isInBounds(start.getX()-30,start.getY()-30)) {
			Entity enemyOne = new Entity();
			enemyOne.setType(EntityType.DEFENSE);
			enemyOne.setX(start.getX()-30);
			enemyOne.setY(start.getY()-30);
			enemyOne.setViewFromTextureWithBBox("turret1_60x60.png");
			enemyOne.addComponent(new DefenseControl(enemyList, world, audio));
			world.addEntity(enemyOne);
		}
	}
}
