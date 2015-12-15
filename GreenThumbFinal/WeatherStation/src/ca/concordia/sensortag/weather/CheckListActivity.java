package ca.concordia.sensortag.weather;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;


public class CheckListActivity extends Activity implements OnClickListener {
	
	static public final String TAG = "CheckList"; // Tag for Android's logcat
	static protected final int UPDATE_PERIOD_MS = 1000; // How often measurements should be taken
	
	public Button mNewButton;
	public Button mOpenButton;
	public Button mHomeButton;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_list);
		

		mNewButton = (Button)findViewById(R.id.newButton);
		mNewButton.setOnClickListener(this);
		mOpenButton = (Button) findViewById(R.id.openButton);
		mOpenButton.setOnClickListener(this);
		mHomeButton = (Button)findViewById(R.id.homeButton);
		mHomeButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		
		if (v.getId() == R.id.newButton)
		{
			Intent returnIntent = new Intent(this, SaveCheckListActivity.class);
	        startActivity(returnIntent);
		}
		else if (v.getId() == R.id.openButton)
		{
			Intent returnIntent2 = new Intent(this, OpenCheckListActivity.class);
	        startActivity(returnIntent2);
		}
		else if (v.getId() == R.id.homeButton)
		{
			Intent returnIntent3 = new Intent(this, WeatherStationActivity.class);
	        startActivity(returnIntent3);
		}

	}

}
