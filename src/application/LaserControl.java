package application;

import java.util.List;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import javafx.geometry.Point2D;

public class LaserControl extends Component {
	private Point2D target;
	private List<Entity> enemyList;
	public LaserControl(Point2D target, List<Entity> enemyList) {
		this.target = target;
		this.enemyList = enemyList;
		// TODO Auto-generated constructor stub
	}
	@Override
    public void onAdded() {
//		entity.setRotation(360-entity.getCenter().angle(target, new Point2D(999999, entity.getY())));
		entity.setRotation(45);
		if (entity.getCenter().getY() < target.getY()) {
			entity.setRotation(entity.getCenter().angle(target, new Point2D(999999, entity.getY())));
		} else {
			entity.setRotation(360-entity.getCenter().angle(target, new Point2D(999999, entity.getY())));
		}
    }

    @Override
    public void onUpdate(double tpf) {
        
//        Laser newEnemy = (Laser) entity;
//		
//		Point2D currentPos = newEnemy.getPosition();
//		Point2D destination = Path.get(newEnemy.getNum());
//		
//		if (currentPos.distance(destination) < speed) {
//			
//			//Increment to the next position
//			newEnemy.increment();
//			if (newEnemy.getNum() == Path.size()) {
//				entity.removeFromWorld();
//				return;
//			}
//			
//		}
//		if (currentPos.getX()-destination.getX() < -speed) {
//			newEnemy.setRotation(0);
//		} else if (currentPos.getY()-destination.getY() < -speed) {
//			newEnemy.setRotation(90);
//		} else if (currentPos.getY()-destination.getY() > -speed) {
//			newEnemy.setRotation(270);
//		}
		//Move towards destination
//    	if (entity.getCenter().distance(target) < 20) {
//    		entity.removeFromWorld();
//    		enemyList.remove(entity);
//    	}
    	if (entity.getPosition().subtract(target).getX() < 0) {
    		entity.removeFromWorld();
    		enemyList.remove(entity);
    	}
    	System.out.println();
    	entity.translateTowards(target, 10);
    }
}
