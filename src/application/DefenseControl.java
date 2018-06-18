package application;

import java.util.List;

import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.entity.Entities;
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
	private ViewComponent view;
	private Entity circleRadius;
	
	private int frameCounter;
	private int fireCounter;
	private int fireRate;
	
	private final int FIRE_COOLDOWN = 60;
	private final int RANGE = 150;
	
	public DefenseControl(GameWorld world, int fireRate, AudioPlayer player) {
		this.world = world;
		this.fireRate = fireRate;
		this.player = player;
	}
	
	@Override
    public void onAdded() {
		circleRadius = new Entity();
        view.getView().setOnMouseClicked(e -> {
        	entity.setViewFromTexture("defense.png");
        	LaserDefense.showDefenseMenu(entity);
        });
    }
	
	//upgrades the level
	public void upgrade() {
		fireRate++;
	}
	
	//returns level
	public int getLevel() {
		return fireRate;
	}
	
	//shows the radius once selected
	public void showRadius(Boolean show) {
		if (show) {
			circleRadius = Entities.builder()
			        .at(entity.getCenter().getX()-200, entity.getCenter().getY()-200)
			        .viewFromNode(new Circle(200, new Color(1,1,1,0.1))).buildAndAttach(world);
		} else {
			circleRadius.removeFromWorld();
		}
	}
	
	//changes the sprite to the selected sprite
	public void setSelectedTexture(Boolean selected) {
		entity.setViewFromTexture(selected ? "defense.png" : "defenseSelected.png");
	}
	
	//returns the closest enemy within range of the defense
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
	
	//Code to turn towards target and place laser
    @Override
    public void onUpdate(double tpf) {
    	enemyList = world.getEntitiesByType(EntityType.ENEMY);
    	Entity closest = getClosest();
    	if (frameCounter >= FIRE_COOLDOWN && closest != null) {
    		
    		//rotate by an angle found between two vectors
    		if (entity.getCenter().getY() < closest.getCenter().getY()) {
    			entity.setRotation(entity.getCenter().angle(closest.getCenter(), new Point2D(999999, entity.getY()))-90);
    		} else {
    			entity.setRotation(270-entity.getCenter().angle(closest.getCenter(), new Point2D(999999, entity.getY())));
    		}
    		if (frameCounter <= FIRE_COOLDOWN + 8 && fireCounter < fireRate) {
    			player.playSound("laser.wav");
    			LaserFactory.lasah(world, entity.getCenter());
    			fireCounter++;
    			frameCounter -= 8;
    		} else {
    			fireCounter = 0;
    			frameCounter = 0;
    		}
    	} else {
    		frameCounter++;
    	}
    }

}
