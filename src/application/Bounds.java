package application;

import java.util.Arrays;
import java.util.List;

import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.scene.GameScene;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class Bounds {
	private static List<Rectangle> bounds = Arrays.asList(
			new Rectangle(0,280,250,65),
			new Rectangle(183,116,65,188),
			new Rectangle(238,116,250,65),
			new Rectangle(424,174,65,428),
			new Rectangle(488,546,242,65),
			new Rectangle(611,301,65,247),
			new Rectangle(729,280,285,65),
			new Rectangle(1019,0,288,719)
			);
	
	public static boolean isInBounds(double x, double y) {
		for (Rectangle i:bounds) {
			if (i.contains(new Point2D(x,y)))
				return true;
		}
		return false;
	}
}