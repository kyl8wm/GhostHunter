package edu.virignia.cs2110.kabjghosthunter;

import java.util.ArrayList;


import java.util.Random;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	
	private GoogleMap googleMap;
	private User mainUser;
	private Handler userTrackHandler = new Handler();
	private Handler ghostCreationHandler = new Handler();

	
	private ArrayList<Ghost> ghostList = new ArrayList<Ghost>();
	private ArrayList<Bones> bonesList = new ArrayList<Bones>();
	private ArrayList<Integer> colorList = new ArrayList<Integer>();
	private ArrayList<Marker> ghostMarkerList = new ArrayList<Marker>();
	private ArrayList<Marker> boneMarkerList = new ArrayList<Marker>();
	private ArrayList<String> colorNames = new ArrayList<String>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/** 
		 * Colors to use
		 */
		colorList.add(Color.BLUE);
		colorList.add(Color.RED);
		colorList.add(Color.WHITE);


		colorNames.add("Blue");
		colorNames.add("Red");
		colorNames.add("White");

		
		final Toast proximityAlert= Toast.makeText(this, "You are getting close to a ghost!", Toast.LENGTH_SHORT);
		final Toast killedGhostAlert = Toast.makeText(this, "You killed a ghost and used up its bones! But it also dropped a pair of different bones that you picked up!", Toast.LENGTH_LONG);
		final Toast pickedUpBonesAlert = Toast.makeText(this,"You picked up a set of bones!", Toast.LENGTH_LONG);
		final Toast deadAlert = Toast.makeText(this, "You have been killed. Game Over", Toast.LENGTH_LONG);
		final Toast damageAlert = Toast.makeText(this, "You have taken damage because you are too close to a ghost and don't have its bones!", Toast.LENGTH_SHORT);
		this.setMainUser(new User(this));
		
		
		/** Runnable method that creates ghosts every 2 minutes */
		Runnable createGhosts = new Runnable() {
			public void run() {
				if(mainUser.isDead() == false && ghostList.size() < 100) {
					int randomColor = colorList.get(new Random().nextInt(colorList.size()));
					ghostList.add(new Ghost(mainUser, randomColor));
					int colorIndex = colorList.indexOf(randomColor);
					double lat = ((Ghost)ghostList.get(ghostList.size()-1)).getGhostLatitude();
					double lon = ((Ghost)ghostList.get(ghostList.size()-1)).getGhostLongitude();
					if(colorNames.get(colorIndex).equals("Blue")) {
						ghostMarkerList.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title(colorNames.get(colorIndex)+" Ghost").icon(BitmapDescriptorFactory.fromAsset("BlueGhost.jpg"))));
					}
					if(colorNames.get(colorIndex).equals("Red")) {
						ghostMarkerList.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title(colorNames.get(colorIndex)+" Ghost").icon(BitmapDescriptorFactory.fromAsset("RedGhost.jpg"))));
					}
					if(colorNames.get(colorIndex).equals("White")) {
						ghostMarkerList.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title(colorNames.get(colorIndex)+" Ghost").icon(BitmapDescriptorFactory.fromAsset("WhiteGhost.jpg"))));
					}
					bonesList.add(new Bones(mainUser, randomColor));
					double bonelat = ((Bones)bonesList.get(bonesList.size()-1)).getBonesLatitude();
					double bonelon = ((Bones)bonesList.get(bonesList.size()-1)).getBonesLongitude();
					if(colorNames.get(colorIndex).equals("Blue")) {
						boneMarkerList.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(bonelat,bonelon)).title(colorNames.get(colorIndex)+" Bones").icon(BitmapDescriptorFactory.fromAsset("BlueBones.png"))));
					}
					if(colorNames.get(colorIndex).equals("White")) {
						boneMarkerList.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(bonelat,bonelon)).title(colorNames.get(colorIndex)+" Bones").icon(BitmapDescriptorFactory.fromAsset("WhiteBones.png"))));
					}
					if(colorNames.get(colorIndex).equals("Red")) {
						boneMarkerList.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(bonelat,bonelon)).title(colorNames.get(colorIndex)+" Bones").icon(BitmapDescriptorFactory.fromAsset("RedBones.png"))));
					}
					ghostCreationHandler.postDelayed(this, 20000);
				}
			}
		};
		
		/** Runnable method that updates the user's location every 20 seconds
		 * Also tells the user if a ghost is nearby
		 */
		Runnable trackUser = new Runnable() {
			
			@Override
			public void run() {
				if(mainUser.isDead() == false) {
					Log.w("Bomb number", "" + mainUser.getUserBombNumber());
					for(Ghost ghost : new ArrayList<Ghost>(ghostList)) {
						Log.w("Distance", ""+getUserGhostDistance(ghost));
						/**
						 * If userghost distance is less than 10 but greater than 1, proximity alert pops up
						 */
						if(getUserGhostDistance(ghost) < 20) {
							proximityAlert.show();
						}
						/**
						 * Checks to see if the user is close enough to the ghost to be hurt
						 * Alex work on this!
						 */
						if (getUserGhostDistance(ghost) <= 5) {
							if(mainUser.getUserColorBonesList().contains(ghost.getGhostColor())) {
								killGhost(mainUser, ghost , 10);
								killedGhostAlert.show();
							}
							else {
								damageAlert.show();
								mainUser.decreaseHealth(5);
							}
						}
					}
					/**
					 * If comes close to a set of bones, automatically pick them up
					 * add to userboneslist
					 * delete from mainactivity boneslist
					 */
					for(int i = 0; i < bonesList.size(); i ++) {
						if(getUserBonesDistance(bonesList.get(i)) < 10) {
							mainUser.addBones(bonesList.get(i));
							pickedUpBonesAlert.show();
							bonesList.remove(i);
							boneMarkerList.get(i).remove();
							boneMarkerList.remove(i);
						}
					}
				mainUser.getUserLocation();
				userTrackHandler.postDelayed(this, 5000);
			}
				if (mainUser.isDead() == true) {
					deadAlert.show();
				}
			}
			
		};
	
		
		
		
		
		try {
            // Loading map
            initilizeMap();
            googleMap.setMyLocationEnabled(true);

 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
		
		createGhosts.run();
		trackUser.run();
	}
	
	 private void initilizeMap() {
	        if (googleMap == null) {
	            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
	                    R.id.map)).getMap();
	            CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(mainUser.getLatitude(),mainUser.getLongitude()));
	    	    CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

	    		googleMap.moveCamera(center);
	    		googleMap.animateCamera(zoom);
	 
	            // check if map is created successfully or not
	            if (googleMap == null) {
	                Toast.makeText(getApplicationContext(),
	                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
	                        .show();
	            }
	        }
	    }
	 
	    @Override
	    protected void onResume() {
	        super.onResume();
	        initilizeMap();
	    }
	    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		 if (item.getItemId() == R.id.action_settings) {
		        startActivity(new Intent(this, SettingsActivity.class));
		    }
		 if (item.getItemId() == R.id.action_stats) {
			 Intent i = new Intent(getApplicationContext(), Stats.class);
			 i.putExtra("killCount", ""+mainUser.getKillCount());
			 i.putExtra("userHealth", ""+mainUser.getHealth());
			 i.putExtra("userCoords", ""+mainUser.getLatitude() + "," + mainUser.getLongitude());
			 i.putExtra("userMoney",""+mainUser.getMoney());
			 i.putExtra("userPoints", ""+mainUser.getPoints());
			 i.putExtra("bombNumber",""+mainUser.getUserBombNumber());
			 ArrayList<Integer> colors = new ArrayList<Integer>(mainUser.getUserColorBonesList());
			 ArrayList<String> colorBones = new ArrayList<String>();
			 for(int x = 0; x < mainUser.getUserColorBonesList().size(); x ++) {
				 if(colors.get(x) == Color.RED) {
					 colorBones.add("Red Bones");
				 }
				 if(colors.get(x) == Color.WHITE) {
					 colorBones.add("White Bones");
				 }
				 if(colors.get(x) == Color.BLUE) {
					 colorBones.add("Blue Bones");
				 }
			 }
			 i.putExtra("boneList", (ArrayList<String>)colorBones);
		     startActivity(i);
		    }
		 if (item.getItemId() == R.id.action_shop) {
			 Intent i = new Intent(getApplicationContext(), Shop.class);
			 i.putExtra("userMoney",(int)mainUser.getMoney());
			 i.putExtra("userBombs", (int)mainUser.getUserBombNumber());
		     startActivityForResult(i,1);
		    }
		 return super.onOptionsItemSelected(item);
	}
	/**
	 * @return the mainUser
	 */
	public User getMainUser() {
		return mainUser;
	}

	/**
	 * @param mainUser the mainUser to set
	 */
	public void setMainUser(User mainUser) {
		this.mainUser = mainUser;
	}
	
	public double getUserGhostDistance(Ghost ghost) {
		double distance = coordDistance(mainUser.getLatitude(), mainUser.getLongitude(), ghost.getGhostLatitude(), ghost.getGhostLongitude());
		return distance;
	}
	
	public double getUserBonesDistance(Bones bones) {
		double distance = coordDistance(mainUser.getLatitude(), mainUser.getLongitude(), bones.getBonesLatitude(), bones.getBonesLongitude());
		return distance;
	}
	
	public double coordDistance( double lat1, double lon1, double lat2, double lon2) {
	    double earthRadius = 6371; 
	    double distLat = Math.toRadians(lat2 - lat1);
	    double distLon = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(distLat / 2) * Math.sin(distLat / 2) +
	       Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) 
	      * Math.sin(distLon / 2) * Math.sin(distLon / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double d = earthRadius * c;
	    return d*100;
	}
	
	public void killGhost(User user, Ghost ghost, int pointsForKill) {
		int index = ghostList.indexOf(ghost);
		Marker ghostMarker = (ghostMarkerList.get(index));
		ghostMarker.remove();
		ghostMarkerList.remove(index);
		ghostList.remove(index);
		user.addPoints(pointsForKill);
		int previousKillCount = user.getKillCount();
		user.setKillCount(previousKillCount+1);
		mainUser.removeColorBones(ghost.getGhostColor());
		mainUser.addMoney(25);
		
		int randomColor = colorList.get(new Random().nextInt(colorList.size()));
		mainUser.addBones(new Bones(mainUser, randomColor));
	}
	
	public void bombGhost(User user, Ghost ghost, int pointsForKill) {
		int index = ghostList.indexOf(ghost);
		Marker ghostMarker = (ghostMarkerList.get(index));
		ghostMarker.remove();
		ghostMarkerList.remove(index);
		ghostList.remove(index);
		user.addPoints(pointsForKill);
		int previousKillCount = user.getKillCount();
		user.setKillCount(previousKillCount+1);
		mainUser.addMoney(25);
		
		int randomColor = colorList.get(new Random().nextInt(colorList.size()));
		mainUser.addBones(new Bones(mainUser, randomColor));
	}
	public void igniteBomb(){
		final Toast noBombs = Toast.makeText(this, "You don't have any bombs.", Toast.LENGTH_SHORT);
		if(mainUser.getUserBombNumber() > 0){
			for(int i = 0; i < ghostList.size(); i++){
				if(getUserGhostDistance((Ghost)ghostList.get(i)) < 20){
					bombGhost(mainUser, (Ghost)ghostList.get(i), 25);
				}
			}
		mainUser.setUserBombNumber(mainUser.getUserBombNumber()-1);
		}
		else{
			noBombs.show();
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {

		     if(resultCode == RESULT_OK){      
		         int money = data.getIntExtra("userMoney", mainUser.getMoney()); 
		         String result = data.getStringExtra("value");
		         if ( result.equals("RedBones")) {
		        	 mainUser.addBones(new Bones(mainUser, Color.RED));
		         }
		         if ( result.equals("BlueBones")) {
		        	 mainUser.addBones(new Bones(mainUser, Color.BLUE));
		         }
		         if ( result.equals("WhiteBones")) {
		        	 mainUser.addBones(new Bones(mainUser, Color.WHITE));
		         }
		         if ( result.equals("Bomb")) {
		        	 mainUser.setUserBombNumber(mainUser.getUserBombNumber() + 1);
		         }
		         if ( result.equals("UseBomb")) {
		        	 igniteBomb();
		         }
		         mainUser.setMoney(money);
		         
		     }
		  }
		}
}
