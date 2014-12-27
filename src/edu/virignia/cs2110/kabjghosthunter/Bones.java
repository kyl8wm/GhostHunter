package edu.virignia.cs2110.kabjghosthunter;

import java.util.Random;

import android.util.Log;

public class Bones {

	private int bonesColor;
	private double bonesLatitude, bonesLongitude;
	
	public Bones(User mainUser, int bonesColor) {
		this.bonesColor = bonesColor;
		this.bonesLatitude = mainUser.getLatitude() + (((new Random().nextDouble() * 2 -1) * 0.001654867256637));
		this.bonesLongitude =  mainUser.getLongitude() +(((new Random().nextDouble() * 2 -1) * 0.001654867256637));
	}
	/**
	 * @return the bonesColor
	 */
	public int getBonesColor() {
		return bonesColor;
	}
	/**
	 * @param bonesColor the bonesColor to set
	 */
	public void setBonesColor(int bonesColor) {
		this.bonesColor = bonesColor;
	}
	/**
	 * @return the bonesLatitude
	 */
	public double getBonesLatitude() {
		return bonesLatitude;
	}
	/**
	 * @param bonesLatitude the bonesLatitude to set
	 */
	public void setBonesLatitude(double bonesLatitude) {
		this.bonesLatitude = bonesLatitude;
	}
	/**
	 * @return the bonesLongitude
	 */
	public double getBonesLongitude() {
		return bonesLongitude;
	}
	/**
	 * @param bonesLongitude the bonesLongitude to set
	 */
	public void setBonesLongitude(double bonesLongitude) {
		this.bonesLongitude = bonesLongitude;
	}

}
