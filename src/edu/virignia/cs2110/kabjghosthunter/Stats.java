package edu.virignia.cs2110.kabjghosthunter;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Stats extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String killCount = extras.getString("killCount");
		    String userHealth = extras.getString("userHealth");
		    String userCoords = extras.getString("userCoords");
		    String userMoney = extras.getString("userMoney");
		    String userPoints = extras.getString("userPoints");
		    String bombNumber = extras.getString("bombNumber");
		    ArrayList<String> bones = extras.getStringArrayList("boneList");
		    String defaultString = "Number of Ghosts Killed: " + killCount + "\n" + "User Health: " + userHealth + "\n" + "User Coordinates: " + userCoords + "\n" + "User Amount of Money: " + userMoney + "\n" + "User Points: " + userPoints + "\n" + "Number of bombs: " + bombNumber + "\n" + "\n" + "Inventory:" + "\n";
		    for(String s: bones) {
		    	defaultString = defaultString + s + "\n";
		    }
		    TextView t = new TextView(this);
			t = (TextView)findViewById(R.id.test);
			t.setText(defaultString) ;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats, menu);
		return true;
	}

}
