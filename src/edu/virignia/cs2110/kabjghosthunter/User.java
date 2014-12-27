package edu.virignia.cs2110.kabjghosthunter;

import java.util.ArrayList;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class User {
	
	private double health;
	private double latitude;
	private double longitude;
	private int money;
	private boolean hasMoved;
	private boolean isDead;
	private LocationManager userLocationManager;
	private int killCount;
	private int userBombNumber;
	
	/**
	 * @return the userBombNumber
	 */
	public int getUserBombNumber() {
		return userBombNumber;
	}

	/**
	 * @param userBombNumber the userBombNumber to set
	 */
	public void setUserBombNumber(int userBombNumber) {
		this.userBombNumber = userBombNumber;
	}

	public void subtractMoney(int amount) {
		int before = this.getMoney();
		this.setMoney(before - amount);
	}
	
	/**
	 * @return the killCount
	 */
	public int getKillCount() {
		return killCount;
	}

	/**
	 * @param killCount the killCount to set
	 */
	public void setKillCount(int killCount) {
		this.killCount = killCount;
	}

	public void addMoney(int addition) {
		int before = this.getMoney();
		this.setMoney(before + addition);
	}
	/**
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}
	/**
	 * @param money the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}
	/**
	 * @return the userLocationManager
	 */
	public LocationManager getUserLocationManager() {
		return userLocationManager;
	}
	/**
	 * @param userLocationManager the userLocationManager to set
	 */
	public void setUserLocationManager(LocationManager userLocationManager) {
		this.userLocationManager = userLocationManager;
	}

	private int points;
	private ArrayList userColorBonesList;
	/**
	 * User Constructor
	 * @param context
	 */
	public User(Context context) {
		userLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Location location = userLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		this.setLatitude(location.getLatitude());
		this.setLongitude(location.getLongitude());
		this.hasMoved = false;
		this.isDead = false;
		this.setHealth(100.0);
		userColorBonesList = new ArrayList<Integer>();
		this.money = 100;
		this.killCount = 0;
		this.userBombNumber = 0;
	}
	public void removeColorBones(int Color) {
		int index = this.userColorBonesList.indexOf(Color);
		userColorBonesList.remove(index);
	}
	public void addBones(Bones bones) {
		userColorBonesList.add(bones.getBonesColor());
	}
	/**
	 * Decreases the user's health by the specified amount, and if the hp decreases below or to 0, user is 
	 * declared dead.
	 * @param damage
	 */
	public void decreaseHealth(double damage) {
		double healthBefore = this.getHealth();
		this.setHealth(getHealth() - damage);
		if (this.getHealth() <= 0.0) {
			this.setDead(true);
		}
	}
	/**
	 * @return the userColorBonesList
	 */
	public ArrayList getUserColorBonesList() {
		return userColorBonesList;
	}

	/**
	 * @return the isDead
	 */
	public boolean isDead() {
		return isDead;
	}

	/**
	 * @param isDead the isDead to set
	 */
	public void setDead(boolean isDead) {
		this.isDead = isDead;
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

	public void addPoints(int pointsToAdd) {
		int previousPoints = this.getPoints();
		this.setPoints(previousPoints + pointsToAdd);
	}
	
	public void subtractPoints(int pointsToSubtract) {
		int previousPoints = this.getPoints();
		if(previousPoints > pointsToSubtract) {
			this.setPoints(previousPoints - pointsToSubtract);
		}
		else if (pointsToSubtract > previousPoints) {
			this.setPoints(0);
		}
	}
	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * Sets the latitude and longitude of the user according to the coordinates received from the GPS Provider
	 */
	public void getUserLocation() {
		double currentLat = this.getLatitude();
		double currentLon = this.getLongitude();
		Location location = userLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		this.setLatitude(location.getLatitude());
		this.setLongitude(location.getLongitude());
		
		if (currentLat != this.getLatitude() || currentLon != this.getLongitude()) {
			this.hasMoved = true;
		}
	}
	
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @return the hasMoved
	 */
	public boolean isHasMoved() {
		return hasMoved;
	}

	/**
	 * @param hasMoved the hasMoved to set
	 */
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	

}
