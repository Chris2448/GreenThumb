package ca.concordia.sensortag.weather;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SaveCheckListActivity extends Activity implements OnClickListener{
	
	private CheckBox mCheckBoxWater;
	private CheckBox mCheckBoxFertilize;
	private CheckBox mCheckBoxPrune;
	private CheckBox mCheckBoxRotate;
	private CheckBox mCheckBoxInsecticide;
	private CheckBox mCheckBoxWeed;
	private CheckBox mCheckBoxGerminate;
	private CheckBox mCheckBoxPlant;
	private CheckBox mCheckBoxHarvest;
	private Button mClearButton;
	private Button mSaveButton;
	private EditText mDate;
	private EditText mName;
	private ArrayList detailsArrayList2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_check_list);
		
		mDate = (EditText) findViewById(R.id.editDate);
		mName = (EditText) findViewById(R.id.editName);
		mCheckBoxWater = (CheckBox) findViewById(R.id.checkBoxWater);
		mCheckBoxFertilize = (CheckBox) findViewById(R.id.checkBoxFertilize);
		mCheckBoxPrune = (CheckBox) findViewById(R.id.checkBoxPrune);
		mCheckBoxRotate = (CheckBox) findViewById(R.id.checkBoxRotate);
		mCheckBoxWeed = (CheckBox) findViewById(R.id.checkBoxWeed);
		mCheckBoxInsecticide = (CheckBox) findViewById(R.id.checkBoxInsecticide);
		mCheckBoxGerminate = (CheckBox) findViewById(R.id.checkBoxGerminate);
		mCheckBoxPlant = (CheckBox) findViewById(R.id.checkBoxPlant);
		mCheckBoxHarvest = (CheckBox) findViewById(R.id.checkBoxHarvest);
		mClearButton = (Button)findViewById(R.id.clearButton);
		mClearButton.setOnClickListener(this);
		mSaveButton = (Button) findViewById(R.id.buttonSave);
		mSaveButton.setOnClickListener(this);

		detailsArrayList2 = new ArrayList();
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save_check_list, menu);
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
	
	// Checking checkboxes
	public void onCheckboxClicked(View view) {
	    boolean checked = ((CheckBox) view).isChecked();
	    
	    switch(view.getId()){
	    case R.id.checkBoxWater:
	    	if (checked) {
	             mCheckBoxWater.setChecked(false);
	         }
	    	else
	    		mCheckBoxWater.setChecked(true);
	    	break;
	    case R.id.checkBoxFertilize:
	    	if (checked) {
	             mCheckBoxFertilize.setChecked(false);
	         }
	    	else
	    		mCheckBoxFertilize.setChecked(true);
	    	break;
	    case R.id.checkBoxPrune:
	    	if (checked) {
	             mCheckBoxPrune.setChecked(false);
	         }
	    	else
	    		mCheckBoxPrune.setChecked(true);
	    	break;
	    case R.id.checkBoxRotate:
	    	if (checked) {
	             mCheckBoxRotate.setChecked(false);
	         }
	    	else
	    		mCheckBoxRotate.setChecked(true);
	    	break;
	    case R.id.checkBoxWeed:
	    	if (checked) {
	             mCheckBoxWeed.setChecked(false);
	         }
	    	else
	    		mCheckBoxWeed.setChecked(true);
	    	break;
	    case R.id.checkBoxInsecticide:
	    	if (checked) {
	             mCheckBoxInsecticide.setChecked(false);
	         }
	    	else
	    		mCheckBoxInsecticide.setChecked(true);
	    	break;
	    case R.id.checkBoxGerminate:
	    	if (checked) {
	             mCheckBoxGerminate.setChecked(false);
	         }
	    	else
	    		mCheckBoxGerminate.setChecked(true);
	    	break;
	    case R.id.checkBoxPlant:
	    	if (checked) {
	             mCheckBoxPlant.setChecked(false);
	         }
	    	else
	    		mCheckBoxPlant.setChecked(true);
	    	break;
	    case R.id.checkBoxHarvest:
	    	if (checked) {
	             mCheckBoxHarvest.setChecked(false);
	         }
	    	else
	    		mCheckBoxHarvest.setChecked(true);
	    	break;
	    }  
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.clearButton)
		{
			// empty all checkboxes
        	mCheckBoxWater.setChecked(false);
        	mCheckBoxFertilize.setChecked(false);
        	mCheckBoxPrune.setChecked(false);
        	mCheckBoxRotate.setChecked(false);
        	mCheckBoxWeed.setChecked(false);
        	mCheckBoxInsecticide.setChecked(false);
        	mCheckBoxGerminate.setChecked(false);
        	mCheckBoxPlant.setChecked(false);
        	mCheckBoxHarvest.setChecked(false);
		}
		
		else if(v.getId() == R.id.buttonSave){
		
			// get and set user's checklist
			String userDate = mDate.getText().toString();
			String userName = mName.getText().toString();
			String userWater, userFertilize, userRotate, userPrune, userWeed, userInsecticide, userGerminate, userPlant, userHarvest;
			userWater = mCheckBoxWater.isChecked() ? "yes" : "no";
			userFertilize = mCheckBoxFertilize.isChecked() ? "yes" : "no";
			userRotate = mCheckBoxRotate.isChecked() ? "yes" : "no";
			userPrune = mCheckBoxPrune.isChecked() ? "yes" : "no";
			userWeed = mCheckBoxWeed.isChecked() ? "yes" : "no";
			userInsecticide = mCheckBoxInsecticide.isChecked() ? "yes" : "no";
			userGerminate = mCheckBoxGerminate.isChecked() ? "yes" : "no";
			userPlant = mCheckBoxPlant.isChecked() ? "yes" : "no";
			userHarvest = mCheckBoxHarvest.isChecked() ? "yes" : "no";
			

			// save detail values			
			Details2 detailsCheckList = new Details2();
			detailsCheckList.setDate(userDate);
			detailsCheckList.setName(userName);
			detailsCheckList.setWater(userWater);
			detailsCheckList.setFertilize(userFertilize);
			detailsCheckList.setRotate(userRotate);
			detailsCheckList.setPrune(userPrune);
			detailsCheckList.setWeed(userWeed);
			detailsCheckList.setInsecticide(userInsecticide);
			detailsCheckList.setGerminate(userGerminate);
			detailsCheckList.setPlant(userPlant);
			detailsCheckList.setHarvest(userHarvest);
			
			detailsArrayList2.add(detailsCheckList);
			
			//send to function to add to SQLDatabase
			saveCheckList(detailsCheckList);
			
			// toast message
			Toast.makeText(this, "CheckList Succesfully Saved", Toast.LENGTH_SHORT).show();	
			
			//return to splash screen
			Intent returnIntent = new Intent(this, CheckListActivity.class);
            startActivity(returnIntent);
		}
			
	}
	
public void saveCheckList(Details2 userDetails){
		
		// SQLDatabase
		AndroidOpenDbHelper2 androidOpenDbHelperObj = new AndroidOpenDbHelper2(this);
        SQLiteDatabase sqliteDatabase = androidOpenDbHelperObj.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        
        contentValues.put(AndroidOpenDbHelper2.COLUMN_DATE, userDetails.getDate());
        contentValues.put(AndroidOpenDbHelper2.COLUMN_NAME, userDetails.getName());
        contentValues.put(AndroidOpenDbHelper2.COLUMN_WATER, userDetails.getWater());
        contentValues.put(AndroidOpenDbHelper2.COLUMN_FERT, userDetails.getFertilize());
        contentValues.put(AndroidOpenDbHelper2.COLUMN_ROT, userDetails.getRotate());
        contentValues.put(AndroidOpenDbHelper2.COLUMN_PRUNE, userDetails.getPrune());
        contentValues.put(AndroidOpenDbHelper2.COLUMN_WEED, userDetails.getWeed());
        contentValues.put(AndroidOpenDbHelper2.COLUMN_INSEC, userDetails.getInsecticide());
        contentValues.put(AndroidOpenDbHelper2.COLUMN_GERM, userDetails.getGerminate());
        contentValues.put(AndroidOpenDbHelper2.COLUMN_PLANT, userDetails.getPlant());
        contentValues.put(AndroidOpenDbHelper2.COLUMN_HARV, userDetails.getHarvest());
        
        long affectedColumnId = sqliteDatabase.insert(AndroidOpenDbHelper2.TABLE_NAME1, null, contentValues);
        sqliteDatabase.close();

        Toast.makeText(this, "Values inserted: column ID is " + affectedColumnId, Toast.LENGTH_SHORT).show();
	}
}
