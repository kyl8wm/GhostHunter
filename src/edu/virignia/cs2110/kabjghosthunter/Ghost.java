package edu.virignia.cs2110.kabjghosthunter;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Color;

public class Ghost {

	private int ghostColor;
	/**
	 * @return the ghostColor
	 */
	public int getGhostColor() {
		return ghostColor;
	}

	private ArrayList<Integer> colorList = new ArrayList<Integer>();
	private double ghostLatitude, ghostLongitude;
	private boolean ghostDead;
	private User mainUser;
	private double health;
	
	public void decreaseHealth(double damage) {
		double healthBefore = this.getHealth();
		this.setHealth(healthBefore - damage);
		if (this.getHealth() <= 0) {
			this.setGhostDead(true);
		}
	}
	/**
	 * @param ghostDead the ghostDead to set
	 */
	public void setGhostDead(boolean ghostDead) {
		this.ghostDead = ghostDead;
	}
	/**
	 * @return the health
	 */
	public double getHealth() {
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(double health) {
		this.health = health;
	}

	/**
	 * @return the ghostLatitude
	 */
	public double getGhostLatitude() {
		return ghostLatitude;
	}

	/**
	 * @param ghostLatitude the ghostLatitude to set
	 */
	public void setGhostLatitude(double ghostLatitude) {
		this.ghostLatitude = ghostLatitude;
	}

	/**
	 * @return the ghostLongitude
	 */
	public double getGhostLongitude() {
		return ghostLongitude;
	}

	/**
	 * @param ghostLongitude the ghostLongitude to set
	 */
	public void setGhostLongitude(double ghostLongitude) {
		this.ghostLongitude = ghostLongitude;
	}

	public Ghost(User mainUser, int inputColor){
		this.mainUser = mainUser;
		colorList.add(Color.WHITE);
		colorList.add(Color.RED);
		colorList.add(Color.BLUE);
		ghostColor = inputColor;
		ghostLatitude = mainUser.getLatitude() + (((new Random().nextDouble() * 2 -1) * 0.001654867256637));
		ghostLongitude = mainUser.getLongitude() + (((new Random().nextDouble() * 2 -1) * 0.001654867256637));
		
	}
	
	
	public void checkBones(){
		if(this.ghostColor == Color.WHITE){
			this.ghostDead = true;
			//remove ghost icon
			mainUser.setPoints(mainUser.getPoints()+500);
		}
//		else if(this.ghostColor == Color.BLUE){
//			if(mainUser.items.contains("Blue Bones")){
//				this.ghostDead = true;
//				mainUser.items.remove("Blue Bones");
//				//remove ghost icon
//				mainUser.setPoints(mainUser.getPoints() + 1000);
//			}
//		}
	}
	
	public boolean isDead(){
		if(ghostDead = false){
			return false;
		}
		else{
			//remove ghost icon
			return true;
		}
	}
	
}

