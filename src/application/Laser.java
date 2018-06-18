package application;

import java.util.List;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.components.CollidableComponent;

import javafx.geometry.Point2D;

public class Laser extends Entity {
	public Laser() {
		// TODO Auto-generated constructor stub
	}
	public static void lasah( GameWorld world, Point2D start) {
		Entity enemyOne = new Entity();
		enemyOne.setType(EntityType.LASER);
		enemyOne.setX(start.getX());
		enemyOne.setY(start.getY());
		enemyOne.setViewFromTextureWithBBox("laserBullet_48.png");
		List <Entity> enemyList = world.getEntitiesByType(EntityType.ENEMY);
		if (enemyList.size() > 0) {
			Entity closest = enemyList.get(0);
			for (Entity i:enemyList) {
				if (start.distance(i.getPosition()) < start.distance(closest.getPosition())) {
					closest = i;
				}
			}
			enemyOne.addComponent(new LaserControl(closest.getCenter()));
			enemyOne.addComponent(new CollidableComponent(true));
			world.addEntity(enemyOne);
		}
	}
}
