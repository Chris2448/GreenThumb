package ca.concordia.sensortag.weather;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class OpenCheckListActivity extends Activity implements OnClickListener, OnItemClickListener {
	
	private ListView CheckListView;
    private ListAdapter CheckListAdapter;
    private ArrayList<Details2> dataArrayList2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_check_list);
		
		CheckListView = (ListView) findViewById(R.id.checklistView);
        CheckListView.setOnItemClickListener(this);
        dataArrayList2 = new ArrayList<Details2>();
        CheckListView.setAdapter(CheckListAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.open_check_list, menu);
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
	public void onItemClick(AdapterView<?> param0, View param1, int param2, long param3) {
		
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Clicked on :" + param2, Toast.LENGTH_SHORT).show();
		
		Intent updateDeleteIntent2 = new Intent(this, UpdateDeleteActivity2.class);
		
		Details2 clickedObject = dataArrayList2.get(param2);

        Bundle dataBundle2 = new Bundle();
        dataBundle2.putString("clickedName", clickedObject.getName());
        dataBundle2.putString("clickedDate", clickedObject.getDate());
        dataBundle2.putString("clickedWater", clickedObject.getWater());
        dataBundle2.putString("clickedFertilize", clickedObject.getFertilize());
        dataBundle2.putString("clickedRotate", clickedObject.getRotate());
        dataBundle2.putString("clickedPrune", clickedObject.getPrune());
        dataBundle2.putString("clickedWeed" , clickedObject.getWeed());
        dataBundle2.putString("clickedInsecticide",clickedObject.getInsecticide());
        dataBundle2.putString("clickedGerminate", clickedObject.getGerminate());
        dataBundle2.putString("clickedPlant", clickedObject.getPlant());
        dataBundle2.putString("clickedHarvest", clickedObject.getHarvest());
        
        updateDeleteIntent2.putExtras(dataBundle2);
        
        startActivity(updateDeleteIntent2);	

        
	}

	@Override
	public void onClick(View v) {
	}
	
	public List<String> populateList()
    {
		List<String> CheckListNamesList = new ArrayList<String>();
		
        AndroidOpenDbHelper2 openHelperClass2 = new AndroidOpenDbHelper2(this);
        SQLiteDatabase sqliteDatabase = openHelperClass2.getReadableDatabase();
        
        Cursor cursor2 = sqliteDatabase.query(AndroidOpenDbHelper2.TABLE_NAME1, null, null, null, null, null, null);
        
        while (cursor2.moveToNext())
        {
            String mName = cursor2.getString(cursor2.getColumnIndex(AndroidOpenDbHelper2.COLUMN_NAME));
            String mDate = cursor2.getString(cursor2.getColumnIndex(AndroidOpenDbHelper2.COLUMN_DATE));
            String mWater = cursor2.getString(cursor2.getColumnIndex(AndroidOpenDbHelper2.COLUMN_WATER));
            String mFert = cursor2.getString(cursor2.getColumnIndex(AndroidOpenDbHelper2.COLUMN_FERT));
            String mPrune = cursor2.getString(cursor2.getColumnIndex(AndroidOpenDbHelper2.COLUMN_PRUNE));
            String mRotate = cursor2.getString(cursor2.getColumnIndex(AndroidOpenDbHelper2.COLUMN_PRUNE));
            String mWeed = cursor2.getString(cursor2.getColumnIndex(AndroidOpenDbHelper2.COLUMN_WEED));
            String mInsec = cursor2.getString(cursor2.getColumnIndex(AndroidOpenDbHelper2.COLUMN_INSEC));
            String mGerm = cursor2.getString(cursor2.getColumnIndex(AndroidOpenDbHelper2.COLUMN_GERM));
            String mPlant = cursor2.getString(cursor2.getColumnIndex(AndroidOpenDbHelper2.COLUMN_PLANT));
            String mHarv = cursor2.getString(cursor2.getColumnIndex(AndroidOpenDbHelper2.COLUMN_HARV));
            

            Details2 mClass2 = new Details2();

            mClass2.setName(mName);
            mClass2.setDate(mDate);
            mClass2.setWater(mWater);
            mClass2.setFertilize(mFert);
            mClass2.setPrune(mPrune);
            mClass2.setRotate(mRotate);
            mClass2.setWeed(mWeed);
            mClass2.setInsecticide(mInsec);
            mClass2.setGerminate(mGerm);
            mClass2.setPlant(mPlant);
            mClass2.setHarvest(mHarv);
            
            dataArrayList2.add(mClass2);
            CheckListNamesList.add(mName);
        }

        sqliteDatabase.close();

        return CheckListNamesList;
    }
	
	 protected void onResume()
	    {
	        super.onResume();
	        CheckListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, populateList());
	        CheckListView.setAdapter(CheckListAdapter);
	    }

	    protected void onStart()
	    {
	        super.onStart();
	        dataArrayList2 = new ArrayList<Details2>();
	        CheckListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, populateList());
	        CheckListView.setAdapter(CheckListAdapter);
	    }
}
