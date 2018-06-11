package application;

import java.util.List;

import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.component.Component;

import javafx.geometry.Point2D;

public class BurstControl extends Component {

	private List<Entity> enemyList;
	private GameWorld gameWorld;
	private AudioPlayer player;
	
	private int tickCounter;
	
	private int FIRE_COUNT = 0;
	private final int FIRE_COOLDOWN = 60;
	private final int RANGE = 200;
	
	public BurstControl(List<Entity> enemyList, GameWorld world, AudioPlayer audio) {
		this.enemyList = enemyList;
		gameWorld = world;
		player = audio;
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
			if (closest.getCenter().distance(entity.getCenter()) < RANGE) {
				return closest;
			}
		}
		
		return null;
	}
    @Override
    public void onUpdate(double tpf) {
    	Entity closest = getClosest();
    	if (tickCounter >= FIRE_COOLDOWN && closest != null) {
    		if (entity.getCenter().getY() < closest.getCenter().getY()) {
    			entity.setRotation(entity.getCenter().angle(closest.getCenter(), new Point2D(999999, entity.getY()))-90);
    		} else {
    			entity.setRotation(270-entity.getCenter().angle(closest.getCenter(), new Point2D(999999, entity.getY())));
    		}
    		if (FIRE_COUNT == 0)
    			player.playSound("burst.wav");
    		if (tickCounter <= FIRE_COOLDOWN + 8 && FIRE_COUNT < 3) {
    			Laser.lasah(enemyList, gameWorld, entity.getCenter());
    			FIRE_COUNT++;
    			tickCounter -= 8;
    		} else {
    			FIRE_COUNT = 0;
    			tickCounter = 0;
    		}
//    		player.playSound("single.wav");
//    		tickCounter = 0;
    	} else {
    		tickCounter++;
    	}
    }

}
