package application;

import java.util.ArrayList;
import java.util.List;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.geometry.Point2D;
import javafx.util.Duration;

public class EnemyControl extends Component{
	private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;
    
    //pixels p millisecond
    private int speed;
    
    
    public EnemyControl(int speed) {
        animWalk = new AnimationChannel("enemyOneSheet.png", 6, 46, 46, Duration.seconds(0.6), 0, 5);
		
        texture = new AnimatedTexture(animWalk);
        texture.loopAnimationChannel(animWalk);
        this.speed = speed;
    }

    @Override
    public void onAdded() {
        entity.setViewWithBBox(texture);
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
