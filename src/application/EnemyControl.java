package application;

import java.util.ArrayList;
import java.util.List;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.gameplay.GameState;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.geometry.Point2D;
import javafx.util.Duration;

public class EnemyControl extends Component{
	private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;
    private List<Entity> enemyList;
    private GameState state;
    private int pathNum;
    private GameWorld world;
    
    private int speed;
    private int health;
    private int reward;
    
    public EnemyControl(int health, GameState state, GameWorld world) {
        animWalk = new AnimationChannel("enemy"+health+"Sheet.png", 6, 46, 46, Duration.seconds(0.6), 0, 5);
		this.enemyList = world.getEntitiesByType(EntityType.ENEMY);
        texture = new AnimatedTexture(animWalk);
        texture.loopAnimationChannel(animWalk);
        this.speed = health;
        this.state = state;
        this.health = health;
        this.world = world;
        reward = health;
    }
    
    public int getReward() {
    	return reward;
    }

    @Override
    public void onAdded() {
        entity.setViewWithBBox(texture);
        
    }

    public int minusHealth() {
    	health--;
    	speed = health;
    	if (health > 0) {
    		texture = new AnimatedTexture(new AnimationChannel("enemy"+health+"Sheet.png", 6, 46, 46, Duration.seconds(0.6), 0, 5));
    		texture.loopAnimationChannel(new AnimationChannel("enemy"+health+"Sheet.png", 6, 46, 46, Duration.seconds(0.6), 0, 5));
    		entity.setViewWithBBox(texture);
    	}
    	return health;
    }
    @Override
    public void onUpdate(double tpf) {
        
		
		Point2D currentPos = entity.getPosition();
		Point2D destination = Path.get(pathNum);
		
		if (currentPos.distance(destination) < speed) {
			
			//Increment to the next position
			pathNum++;
			if (pathNum == Path.size()) {
				entity.removeFromWorld();
				state.setValue("lives", state.getInt("lives")-health);
				if (state.getInt("lives") <= 0) {
					LaserGame.loseModal();
				}
				if(world.getEntitiesByType(EntityType.ENEMY).size() == 0)
					LaserGame.enableButton();
				return;
			}
			
		}
		if (currentPos.getX()-destination.getX() < -speed) {
			entity.setRotation(0);
		} else if (currentPos.getY()-destination.getY() < -speed) {
			entity.setRotation(90);
		} else if (currentPos.getY()-destination.getY() > -speed) {
			entity.setRotation(270);
		}
		
		//Move towards destination
    	entity.translateTowards(Path.get(pathNum), speed);
    }
}
