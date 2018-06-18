package application;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.gameplay.GameState;

public class EnemyFactory {
	private GameWorld world;
	private GameState state;
	
	public EnemyFactory (GameWorld world, GameState state) {
		this.world = world;
		this.state = state;
	}
	
	//spawns a new enemy
	public void spawn(Wave wave) {
		for (int i = 0; i < wave.getNumSpawn(); i++) {
			Entity enemyOne = new Entity();
			enemyOne.setType(EntityType.ENEMY);
			enemyOne.setX((-i*wave.getDistance() - 25)-wave.getDelay());
			enemyOne.setY(309);
			enemyOne.addComponent(new EnemyControl(wave.getHealth(), state, world));
			enemyOne.addComponent(new CollidableComponent(true));
			world.addEntity(enemyOne);
		}
	}
}
