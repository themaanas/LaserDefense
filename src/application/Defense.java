package application;

import java.util.List;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.components.CollidableComponent;

import javafx.geometry.Point2D;

public class Defense extends Entity {

	public static void defense(List<Entity> enemyList, GameWorld world, Point2D start) {
		// TODO Auto-generated constructor stub
		Entity enemyOne = new Entity();
		enemyOne.setType(EntityType.DEFENSE);
		enemyOne.setX(start.getX());
		enemyOne.setY(start.getY());
		enemyOne.setViewFromTextureWithBBox("turret1_60x60.png");
		enemyOne.addComponent(new DefenseControl(enemyList, world));
		world.addEntity(enemyOne);
	}

}
