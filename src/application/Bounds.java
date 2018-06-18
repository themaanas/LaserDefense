package application;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public final class Bounds {
	
	//Rectangles for the pathway and the menu
	private static List<Rectangle> bounds = Arrays.asList(
			new Rectangle(-30,250,250,65),
			new Rectangle(153,86,65,188),
			new Rectangle(208,86,250,65),
			new Rectangle(394,144,65,428),
			new Rectangle(458,516,242,65),
			new Rectangle(641,271,65,247),
			new Rectangle(699,250,285,65),
			new Rectangle(1019,0,288,719)
			);
	
	//Checks if the point is within any of the rectangles
	public static boolean isInBounds(double x, double y) {
		for (Rectangle i:bounds) {
			if (i.contains(new Point2D(x,y)))
				return true;
		}
		return false;
	}
}