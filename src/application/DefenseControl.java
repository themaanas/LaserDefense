package application;

import java.util.List;

import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entities.EntityBuilder;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DefenseControl extends Component {

	private List<Entity> enemyList;
	private GameWorld world;
	private AudioPlayer player;
	
	private int tickCounter;
	private int fireCounter = 0;
	private int fireRate;
	private ViewComponent view;
	private Entity circleRadius;
	private final int FIRE_COOLDOWN = 60;
	private final int RANGE = 200;
	
	public DefenseControl(GameWorld world, int fireRate) {
		this.enemyList = world.getEntitiesByType(EntityType.ENEMY);
		this.world = world;
		this.fireRate = fireRate;
	}
	public void upgrade() {
		fireRate++;
	}
	public int getLevel() {
		return fireRate;
	}
	@Override
    public void onAdded() {
		circleRadius = new Entity();
        view.getView().setOnMouseClicked(e -> {
        	entity.setViewFromTexture("turret1_60x60.png");
        	LaserGame.showDefenseMenu(entity);
        });
    }
	
	public void showRadius(Boolean show) {
		if (show) {
			circleRadius = Entities.builder()
			        .at(entity.getCenter().getX()-200, entity.getCenter().getY()-200)
			        .viewFromNode(new Circle(200, new Color(255/255,255/255,255/255,0.1))).buildAndAttach(world);
		} else {
			circleRadius.removeFromWorld();
		}
		
	}
	
	public void setSelectedTexture(Boolean selected) {
		entity.setViewFromTexture(selected ? "turret1_60x60selected.png" : "turret1_60x60.png");
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
    	enemyList = world.getEntitiesByType(EntityType.ENEMY);
    	Entity closest = getClosest();
    	if (tickCounter >= FIRE_COOLDOWN && closest != null) {
    		if (entity.getCenter().getY() < closest.getCenter().getY()) {
    			entity.setRotation(entity.getCenter().angle(closest.getCenter(), new Point2D(999999, entity.getY()))-90);
    		} else {
    			entity.setRotation(270-entity.getCenter().angle(closest.getCenter(), new Point2D(999999, entity.getY())));
    		}
//    		if (FIRE_COUNT == 0)
//    			player.playSound("burst.wav");
    		if (tickCounter <= FIRE_COOLDOWN + 8 && fireCounter < fireRate) {
    			Laser.lasah(world, entity.getCenter());
    			fireCounter++;
    			tickCounter -= 8;
    		} else {
    			fireCounter = 0;
    			tickCounter = 0;
    		}
//    		player.playSound("single.wav");
//    		tickCounter = 0;
    	} else {
    		tickCounter++;
    	}
    }

}
