package edu.virignia.cs2110.kabjghosthunter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Shop extends Activity {

	private int userMoney;
	private int userBombNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
		Bundle extras = getIntent().getExtras();
		this.userMoney = extras.getInt("userMoney");
		this.userBombNumber = extras.getInt("userBombs");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shop, menu);
		return true;
	}
	
	public void buyRedBones(View view) {
		if(userMoney >= 30) {
			int money = userMoney - 30;
			String value = "RedBones";
			Intent returnIntent = new Intent();
			returnIntent.putExtra("userMoney",(int)money);
			returnIntent.putExtra("value", value);
			setResult(RESULT_OK,returnIntent);     
			finish();
		}
		else {
			final Toast notEnough = Toast.makeText(this, "You don't have enough money.", Toast.LENGTH_SHORT);
			notEnough.show();
		}
	}
	
	public void buyBlueBones(View view) {
		if(userMoney >= 30) {
			int money = userMoney - 30;
			String value = "BlueBones";
			Intent returnIntent = new Intent();
			returnIntent.putExtra("userMoney",(int)money);
			returnIntent.putExtra("value", value);
			setResult(RESULT_OK,returnIntent);     
			finish();
		}
		else {
			final Toast notEnough = Toast.makeText(this, "You don't have enough money.", Toast.LENGTH_SHORT);
			notEnough.show();
		}
	}
	
	public void buyWhiteBones(View view) {
		if(userMoney >= 30) {
			int money = userMoney - 30;
			String value = "WhiteBones";
			Intent returnIntent = new Intent();
			returnIntent.putExtra("userMoney",(int)money);
			returnIntent.putExtra("value", value);
			setResult(RESULT_OK,returnIntent);     
			finish();
		}
		else {
			final Toast notEnough = Toast.makeText(this, "You don't have enough money.", Toast.LENGTH_SHORT);
			notEnough.show();
		}
	}
	
	public void buyBomb(View view) {
		if(userMoney >= 100) {
			int money = userMoney - 100;
			String value = "Bomb";
			Intent returnIntent = new Intent();
			returnIntent.putExtra("userMoney",(int)money);
			returnIntent.putExtra("value", value);
			setResult(RESULT_OK,returnIntent);     
			finish();
		}
		else {
			final Toast notEnough = Toast.makeText(this, "You don't have enough money.", Toast.LENGTH_SHORT);
			notEnough.show();
		}
	}
	
	public void useBomb(View view) {
		if(userBombNumber > 0) {
			int newBombNumber = userBombNumber - 1;
			String value = "UseBomb";
			Intent returnIntent = new Intent();
			returnIntent.putExtra("bombNumber",(int)newBombNumber);
			returnIntent.putExtra("value", value);
			setResult(RESULT_OK,returnIntent);     
			finish();
		}
		else {
			final Toast notEnough = Toast.makeText(this, "You don't have enough bombs.", Toast.LENGTH_SHORT);
			notEnough.show();
		}
	}

}
