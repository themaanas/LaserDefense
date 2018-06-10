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
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.time.TimerAction;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EnemyFactory {
	
	//typeOfEnemy:
	// 0 = EasyEnemyBlue
	public static void spawn(int type, GameWorld world, List<Entity> enemyList, int numSpawn, int distanceBetween, int speed, int delay) {
		for (int i = 0; i < numSpawn; i++) {
			if (type == 0) {
				Entity enemyOne = new Enemy();
				enemyOne.setType(EntityType.ENEMY);
				enemyOne.setX((-i*distanceBetween - 25)-delay);
				enemyOne.setY(309);
				enemyOne.addComponent(new EnemyControl(speed, enemyList));
				enemyOne.addComponent(new CollidableComponent(true));
				enemyList.add(enemyOne);
				world.addEntity(enemyOne);
			}
		}
	}
}
