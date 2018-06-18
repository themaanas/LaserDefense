package application;

import java.util.List;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.components.CollidableComponent;

import javafx.geometry.Point2D;

public class LaserFactory extends Entity {
	private static boolean gameOver = false;
	
	//Creates new laser
	public static void lasah(GameWorld world, Point2D start) {
		if (!gameOver) {
			Entity enemyOne = new Entity();
			enemyOne.setType(EntityType.LASER);
			enemyOne.setX(start.getX());
			enemyOne.setY(start.getY());
			enemyOne.setViewFromTextureWithBBox("laser.png");
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
	
	//sets game over variable to true so that new lasers aren't instantiated
	public static void setGameOver() {
		gameOver = true;
	}
}
