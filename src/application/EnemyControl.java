package application;

import java.util.ArrayList;
import java.util.List;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
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
    
    private int speed;
    private int health = 2;
    
    public EnemyControl(int speed, List<Entity> enemyList) {
        animWalk = new AnimationChannel("enemy"+health+"Sheet.png", 6, 46, 46, Duration.seconds(0.6), 0, 5);
		this.enemyList = enemyList;
        texture = new AnimatedTexture(animWalk);
        texture.loopAnimationChannel(animWalk);
        this.speed = speed;
    }

    @Override
    public void onAdded() {
        entity.setViewWithBBox(texture);
    }

    public int minusHealth() {
    	health--;
    	if (health > 0) {
    		texture = new AnimatedTexture(new AnimationChannel("enemy"+health+"Sheet.png", 6, 46, 46, Duration.seconds(0.6), 0, 5));
    		texture.loopAnimationChannel(new AnimationChannel("enemy"+health+"Sheet.png", 6, 46, 46, Duration.seconds(0.6), 0, 5));
    		entity.setViewWithBBox(texture);
    	}
    	return health;
    }
    @Override
    public void onUpdate(double tpf) {
        
        Enemy newEnemy = (Enemy) entity;
		
		Point2D currentPos = newEnemy.getPosition();
		Point2D destination = Path.get(newEnemy.getNum());
		
		if (currentPos.distance(destination) < speed) {
			
			//Increment to the next position
			newEnemy.increment();
			if (newEnemy.getNum() == Path.size()) {
				entity.removeFromWorld();
				enemyList.remove(entity);
				
				return;
			}
			
		}
		if (currentPos.getX()-destination.getX() < -speed) {
			newEnemy.setRotation(0);
		} else if (currentPos.getY()-destination.getY() < -speed) {
			newEnemy.setRotation(90);
		} else if (currentPos.getY()-destination.getY() > -speed) {
			newEnemy.setRotation(270);
		}
		
		//Move towards destination
    	entity.translateTowards(Path.get(newEnemy.getNum()), speed);
    }
}
