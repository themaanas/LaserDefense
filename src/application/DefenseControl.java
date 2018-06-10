package application;

import java.util.List;

import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.*;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class DefenseControl extends Component {
	private List<Entity> enemyList;
	private GameWorld gameWorld;
	private int tick;
	private AudioPlayer player;
	
	public DefenseControl(List<Entity> enemyList, GameWorld world, AudioPlayer audio) {
		this.enemyList = enemyList;
		gameWorld = world;
		player = audio;
		// TODO Auto-generated constructor stub
	}
	@Override
    public void onAdded() {
//		Entities.builder()
//        .at(entity.getCenter().getX()-100, entity.getCenter().getY()-100)
//        .viewFromNode(new Circle(100, new Color(0,0,1,0.5)))
//        .buildAndAttach(gameWorld);
    }
	private Entity getClosest() {
		if (enemyList.size() > 0) {
			Entity closest = enemyList.get(0);
			for (Entity i:enemyList) {
				if (entity.getCenter().distance(i.getPosition()) < entity.getCenter().distance(closest.getPosition())) {
					closest = i;
				}
			}
			if (closest.getCenter().distance(entity.getCenter()) < 100) {
				return closest;
			}
		}
		
		return null;
	}
    @Override
    public void onUpdate(double tpf) {
    	Entity closest = getClosest();
    	if (tick >= 60 && closest != null) {
    		if (entity.getCenter().getY() < closest.getCenter().getY()) {
    			entity.setRotation(entity.getCenter().angle(closest.getCenter(), new Point2D(999999, entity.getY()))-90);
    		} else {
    			entity.setRotation(270-entity.getCenter().angle(closest.getCenter(), new Point2D(999999, entity.getY())));
    		}
    		Laser.lasah(enemyList, gameWorld, entity.getCenter());
//    		player.playSound("single.wav");
    		tick = 0;
    	} else {
    		tick++;
    	}
    	
//    	if ((entity.getPosition().subtract(target).getX() < 0 && !isPositive) || (entity.getPosition().subtract(target).getX() > 0 && isPositive)) {
//    		entity.removeFromWorld();
//    		enemyList.remove(entity);
//    	}
//    	entity.translateTowards(target, 10);
    }
}
