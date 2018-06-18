package application;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Point2D;

public final class Path {
	//path for enemy to follow
	private static List<Point2D> path = Arrays.asList(
			new Point2D(193, 309),
			new Point2D(193, 123),
			new Point2D(435, 123),
			new Point2D(435, 553),
			new Point2D(680, 553),
			new Point2D(680, 306),
			new Point2D(985, 306));
	
	//returns a new point to travel to
	public static Point2D get(int i) {
		return path.get(i);
	}
	
	//returns path size
	public static int size() {
		return path.size();
	}
}
