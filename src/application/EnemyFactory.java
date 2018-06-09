package application;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

import java.util.List;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.time.TimerAction;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EnemyFactory {
	
	//typeOfEnemy:
	// 0 = EasyEnemyBlue
	public static void spawn(int type, GameWorld world, List<Entity> enemyList) {
		if (type == 0) {
			Entity enemyOne = new Enemy();
			enemyOne.setView(new Rectangle(25, 25, Color.BLUE));
			enemyOne.setX(-25);
			enemyOne.setY(318);
			enemyOne.setViewFromTextureWithBBox("/enemy1.png");
			enemyList.add(enemyOne);
			world.addEntity(enemyOne);
		}
	}
}
