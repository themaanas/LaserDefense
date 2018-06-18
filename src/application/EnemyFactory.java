package application;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;

import java.sql.Time;
import java.util.List;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.extra.entity.components.HealthComponent;
import com.almasb.fxgl.gameplay.GameState;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.time.TimerAction;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EnemyFactory {
	private GameWorld world;
	private GameState state;
	
	public EnemyFactory (GameWorld world, GameState state) {
		this.world = world;
		this.state = state;
	}
	
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
