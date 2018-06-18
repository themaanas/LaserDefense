package application;

public class Wave {
	private int health;
	private int numSpawn;
	private int distanceBetween;
	private int delay;
	
	public Wave(int health, int numSpawn, int distanceBetween, int delay) {
		this.health = health;
		this.numSpawn = numSpawn;
		this.distanceBetween = distanceBetween;
		this.delay = delay;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getNumSpawn() {
		return numSpawn;
	}
	
	public int getDistance() {
		return distanceBetween;
	}
	
	public int getDelay() {
		return delay;
	}
}
