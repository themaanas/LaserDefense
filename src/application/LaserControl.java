package application;

import com.almasb.fxgl.entity.component.Component;

import javafx.geometry.Point2D;

public class LaserControl extends Component {
	private Point2D target;
	private boolean isPositive;
	
	public LaserControl(Point2D target) {
		this.target = target;
	}
	
	@Override
    public void onAdded() {
		isPositive = entity.getPosition().subtract(target).getX() < 0;
		if (entity.getCenter().getY() < target.getY()) {
			entity.setRotation(entity.getCenter().angle(target, new Point2D(999999, entity.getY())));
		} else {
			entity.setRotation(360-entity.getCenter().angle(target, new Point2D(999999, entity.getY())));
		}
    }

    @Override
    public void onUpdate(double tpf) {
    	if ((entity.getPosition().subtract(target).getX() < 0 && !isPositive) || (entity.getPosition().subtract(target).getX() > 0 && isPositive)) {
    		entity.removeFromWorld();
    	}
    	entity.translateTowards(target, 10);
    }
}
