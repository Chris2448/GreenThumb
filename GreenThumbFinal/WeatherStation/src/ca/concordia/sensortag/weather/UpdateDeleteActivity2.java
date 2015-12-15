package ca.concordia.sensortag.weather;

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

public class UpdateDeleteActivity2 extends Activity implements OnClickListener {
	
	private EditText nameEditText;
    private EditText dateEditText;
    private Button clearButton2;
    private Button deleteButton2;
    private Button modifyButton;
	private CheckBox mCheckBoxWaterU;
	private CheckBox mCheckBoxFertilizeU;
	private CheckBox mCheckBoxPruneU;
	private CheckBox mCheckBoxRotateU;
	private CheckBox mCheckBoxInsecticideU;
	private CheckBox mCheckBoxWeedU;
	private CheckBox mCheckBoxGerminateU;
	private CheckBox mCheckBoxPlantU;
	private CheckBox mCheckBoxHarvestU;
    
    private String bundledName;
    private String bundledDate;
    private String bundledWater;
    private String bundledFert;
    private String bundledPrune;
    private String bundledRotate;
    private String bundledWeed;
    private String bundledInsec;
    private String bundledGerm;
    private String bundledPlant;
    private String bundledHarv;
    
    private String nameEditTextMod;
    private String dateEditTextMod;
    private String waterCheckMod;
    private String fertCheckMod;
    private String pruneCheckMod;
    private String rotateCheckMod;
    private String weedCheckMod;
    private String insecCheckMod;
    private String germCheckMod;
    private String plantCheckMod;
    private String harvCheckMod;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_delete_activity2);

		
		nameEditText = (EditText)findViewById(R.id.editName2);
		dateEditText = (EditText)findViewById(R.id.editDate2);
		clearButton2 = (Button)findViewById(R.id.clearButton2);
		clearButton2.setOnClickListener(this);
		deleteButton2 = (Button)findViewById(R.id.deleteButton2);
		deleteButton2.setOnClickListener(this);
		modifyButton = (Button)findViewById(R.id.modifyButton);
		modifyButton.setOnClickListener(this);
		
		mCheckBoxWaterU = (CheckBox) findViewById(R.id.checkBoxWater2);
		mCheckBoxFertilizeU = (CheckBox) findViewById(R.id.checkBoxFertilize2);
		mCheckBoxPruneU = (CheckBox) findViewById(R.id.checkBoxPrune2);
		mCheckBoxRotateU = (CheckBox) findViewById(R.id.checkBoxRotate2);
		mCheckBoxWeedU = (CheckBox) findViewById(R.id.checkBoxWeed2);
		mCheckBoxInsecticideU = (CheckBox) findViewById(R.id.checkBoxInsecticide2);
		mCheckBoxGerminateU = (CheckBox) findViewById(R.id.checkBoxGerminate2);
		mCheckBoxPlantU = (CheckBox) findViewById(R.id.checkBoxPlant2);
		mCheckBoxHarvestU = (CheckBox) findViewById(R.id.checkBoxHarvest2);
		
		
		// receive and set values
		Bundle receiveBundledData = getIntent().getExtras();

        bundledName = receiveBundledData.getString("clickedName");
        bundledDate = receiveBundledData.getString("clickedDate");
        bundledWater = receiveBundledData.getString("clickedWater");
        bundledFert = receiveBundledData.getString("clickedFertilize");
        bundledRotate = receiveBundledData.getString("clickedRotate");
        bundledPrune = receiveBundledData.getString("clickedPrune");
        bundledWeed = receiveBundledData.getString("clickedWeed");
        bundledInsec = receiveBundledData.getString("clickedInsecticide");
        bundledGerm = receiveBundledData.getString("clickedGerminate");
        bundledPlant = receiveBundledData.getString("clickedPlant");
        bundledHarv = receiveBundledData.getString("clickedHarvest");
        
        
        nameEditText.setText(bundledName);
        dateEditText.setText(bundledDate);
        
        bundledWater = mCheckBoxWaterU.isChecked() ? "yes" : "no";
        
      
    
        if(bundledWater == "yes"){
        	mCheckBoxWaterU.setChecked(true);
        }
        else
        	mCheckBoxWaterU.setChecked(false);
        
        if(bundledFert == "yes"){
        	mCheckBoxFertilizeU.setChecked(true);
        }
        else
        	mCheckBoxFertilizeU.setChecked(false);
        
        if(bundledPrune == "yes"){
        	mCheckBoxPruneU.setChecked(true);
        }
        else
        	mCheckBoxPruneU.setChecked(false);
        
        if(bundledRotate == "yes"){
        	mCheckBoxRotateU.setChecked(true);
        }
        else
        	mCheckBoxRotateU.setChecked(false);
        
        if(bundledWeed == "yes"){
        	mCheckBoxWeedU.setChecked(true);
        }
        else
        	mCheckBoxWeedU.setChecked(false);
        
        if(bundledInsec == "yes"){
        	mCheckBoxInsecticideU.setChecked(true);
        }
        else
        	mCheckBoxInsecticideU.setChecked(false);
        
        if(bundledGerm == "yes"){
        	mCheckBoxGerminateU.setChecked(true);
        }
        else
        	mCheckBoxGerminateU.setChecked(false);
        
        if(bundledPlant == "yes"){
        	mCheckBoxPlantU.setChecked(true);
        }
        else
        	mCheckBoxPlantU.setChecked(false);
        
        if(bundledHarv == "yes"){
        	mCheckBoxHarvestU.setChecked(true);
        }
        else
        	mCheckBoxHarvestU.setChecked(false);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_delete, menu);
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
	
	public void onCheckboxClicked(View view) {
		
		// live modify checklist
		boolean checked = ((CheckBox) view).isChecked();
			    
			    switch(view.getId()){
			    case R.id.checkBoxWater2:
			    	if (checked) {
			             mCheckBoxWaterU.setChecked(false);
			             //waterCheckMod = "no";
			             //bundledWater = "no";
			         }
			    	else{
			    		mCheckBoxWaterU.setChecked(true);
			    		//waterCheckMod = "yes";
			    		//bundledWater = "yes";
			    	}
			    	break;
			    case R.id.checkBoxFertilize2:
			    	if (checked) {
			             mCheckBoxFertilizeU.setChecked(false);
			             //fertCheckMod = "no";
			             //bundledFert = "no";
			         }
			    	else{
			    		mCheckBoxFertilizeU.setChecked(true);
			    		//fertCheckMod = "yes";
			    		//bundledFert = "yes";
			    	}
			    	break;
			    case R.id.checkBoxPrune2:
			    	if (checked) {
			             mCheckBoxPruneU.setChecked(false);
			             //pruneCheckMod = "no";
			             //bundledPrune = "no";
			         }
			    	else{
			    		mCheckBoxPruneU.setChecked(true);
			    		//pruneCheckMod = "yes";
			    		//bundledPrune = "yes";
			    	}
			    	break;
			    case R.id.checkBoxRotate2:
			    	if (checked) {
			             mCheckBoxRotateU.setChecked(false);
			             //rotateCheckMod = "no";
			             //bundledRotate = "no";
			         }
			    	else{
			    		mCheckBoxRotateU.setChecked(true);
			    		//rotateCheckMod = "yes";
			    		//bundledRotate = "yes";
			    	}
			    	break;
			    case R.id.checkBoxWeed2:
			    	if (checked) {
			             mCheckBoxWeedU.setChecked(false);
			             //weedCheckMod = "no";
			             //bundledWeed = "no";
			         }
			    	else{
			    		mCheckBoxWeedU.setChecked(true);
			    		//weedCheckMod = "yes";
			    		//bundledWeed = "yes";
			    		}
			    	break;
			    case R.id.checkBoxInsecticide2:
			    	if (checked) {
			             mCheckBoxInsecticideU.setChecked(false);
			             //insecCheckMod = "no";
			             //bundledInsec = "no";
			         }
			    	else{
			    		mCheckBoxInsecticideU.setChecked(true);
			    		//insecCheckMod = "yes";
			    		//bundledInsec = "yes";
			    	}
			    	break;
			    case R.id.checkBoxGerminate2:
			    	if (checked) {
			             mCheckBoxGerminateU.setChecked(false);
			             //germCheckMod = "no";
			             //bundledGerm = "no";
			         }
			    	else{
			    		mCheckBoxGerminateU.setChecked(true);
			    		//germCheckMod = "yes";
			    		//bundledGerm = "yes";
			    	}
			    	break;
			    case R.id.checkBoxPlant2:
			    	if (checked) {
			             mCheckBoxPlantU.setChecked(false);
			             //plantCheckMod = "no";
			             //bundledPlant = "no";
			         }
			    	else{
			    		mCheckBoxPlantU.setChecked(true);
			    		//plantCheckMod = "yes";
			    		//bundledPlant = "yes";
			    	}
			    	break;
			    case R.id.checkBoxHarvest2:
			    	if (checked) {
			             mCheckBoxHarvestU.setChecked(false);
			             //harvCheckMod = "no";
			             //bundledHarv = "no";
			         }
			    	else{
			    		mCheckBoxHarvestU.setChecked(true);
			    		//harvCheckMod = "yes";
			    		//bundledHarv = "yes";
			    	}
			    	break;
			    }  
		
	}
	

	@Override
	public void onClick(View v) {


        nameEditTextMod = nameEditText.getText().toString();
        dateEditTextMod = dateEditText.getText().toString();
        waterCheckMod = mCheckBoxWaterU.isChecked() ? "yes" : "no";
        fertCheckMod = mCheckBoxFertilizeU.isChecked() ? "yes" : "no";
        pruneCheckMod = mCheckBoxPruneU.isChecked() ? "yes" : "no";
        rotateCheckMod = mCheckBoxRotateU.isChecked() ? "yes" : "no";
        weedCheckMod = mCheckBoxWeedU.isChecked() ? "yes" : "no";
        insecCheckMod = mCheckBoxInsecticideU.isChecked() ? "yes" : "no";
        germCheckMod = mCheckBoxGerminateU.isChecked()? "yes" : "no";
        plantCheckMod = mCheckBoxPlantU.isChecked() ? "yes":"no";
        harvCheckMod = mCheckBoxHarvestU.isChecked() ? "yes" : "no";
        
        bundledWater = mCheckBoxWaterU.isChecked() ? "yes" : "no";
        bundledFert = mCheckBoxFertilizeU.isChecked() ? "yes" : "no";
        bundledPrune = mCheckBoxPruneU.isChecked() ? "yes" : "no";
        bundledRotate = mCheckBoxRotateU.isChecked() ? "yes" : "no";
        bundledWeed = mCheckBoxWeedU.isChecked() ? "yes" : "no";
        bundledInsec = mCheckBoxInsecticideU.isChecked() ? "yes" : "no";
        bundledGerm = mCheckBoxGerminateU.isChecked()? "yes" : "no";
        bundledPlant = mCheckBoxPlantU.isChecked() ? "yes":"no";
        bundledHarv = mCheckBoxHarvestU.isChecked() ? "yes" : "no";
        
        
        Details2 checkListDetails = new Details2();
        
        checkListDetails.setName(bundledName);
        checkListDetails.setDate(bundledDate);
        checkListDetails.setWater(bundledWater);
        checkListDetails.setFertilize(bundledFert);
        checkListDetails.setPrune(bundledPrune);
        checkListDetails.setRotate(bundledRotate);
        checkListDetails.setWeed(bundledWeed);
        checkListDetails.setInsecticide(bundledInsec);
        checkListDetails.setGerminate(bundledGerm);
        checkListDetails.setPlant(bundledPlant);
        checkListDetails.setHarvest(bundledHarv);
	
		if(v.getId() == R.id.clearButton2){
			// empty all checkboxes
        	mCheckBoxWaterU.setChecked(false);
        	mCheckBoxFertilizeU.setChecked(false);
        	mCheckBoxPruneU.setChecked(false);
        	mCheckBoxRotateU.setChecked(false);
        	mCheckBoxWeedU.setChecked(false);
        	mCheckBoxInsecticideU.setChecked(false);
        	mCheckBoxGerminateU.setChecked(false);
        	mCheckBoxPlantU.setChecked(false);
        	mCheckBoxHarvestU.setChecked(false);
		}
		// save new settings
		else if (v.getId() == R.id.modifyButton){
			updateDetails(checkListDetails);
            Intent returnIntent = new Intent(this, CheckListActivity.class);
            startActivity(returnIntent);
		}
		//delete all settings
		else if (v.getId() == R.id.deleteButton2){
            deleteDetails(checkListDetails);
            Intent returnIntent2 = new Intent(this, CheckListActivity.class);
            startActivity(returnIntent2);
		}		
	}
	
	private void updateDetails(Details2 checkListDetails)
    {

        AndroidOpenDbHelper2 androidOpenDbHelper = new AndroidOpenDbHelper2(this);
        SQLiteDatabase sqliteDatabase = androidOpenDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(AndroidOpenDbHelper2.COLUMN_NAME, nameEditTextMod);
        contentValues.put(AndroidOpenDbHelper2.COLUMN_DATE, dateEditTextMod);
        contentValues.put(AndroidOpenDbHelper2.COLUMN_WATER, waterCheckMod);
        contentValues.put(AndroidOpenDbHelper2.COLUMN_FERT, fertCheckMod);
        contentValues.put(AndroidOpenDbHelper2.COLUMN_PRUNE, pruneCheckMod);
        contentValues.put(AndroidOpenDbHelper2.COLUMN_ROT, rotateCheckMod);
        contentValues.put(AndroidOpenDbHelper2.COLUMN_WEED, weedCheckMod);
        contentValues.put(AndroidOpenDbHelper2.COLUMN_INSEC, insecCheckMod);
        contentValues.put(AndroidOpenDbHelper2.COLUMN_GERM, germCheckMod);
        contentValues.put(AndroidOpenDbHelper2.COLUMN_PLANT, plantCheckMod);
        contentValues.put(AndroidOpenDbHelper2.COLUMN_HARV, harvCheckMod);
        

        String[] whereClauseArgument = new String[1];
        whereClauseArgument[0] = checkListDetails.getName();

        System.out.println("whereClauseArgument[0] is :" + whereClauseArgument[0]);

        sqliteDatabase.update(AndroidOpenDbHelper2.TABLE_NAME1, contentValues, AndroidOpenDbHelper2.COLUMN_NAME+"=?", whereClauseArgument);

        sqliteDatabase.close();
        
     // toast message
     	Toast.makeText(this, "CheckList Succesfully Modified", Toast.LENGTH_SHORT).show();	

        finish();
    }

    private void deleteDetails(Details2 deleteDetails)
    {

        AndroidOpenDbHelper2 androidOpenDbHelper = new AndroidOpenDbHelper2(this);
        SQLiteDatabase sqliteDatabase = androidOpenDbHelper.getWritableDatabase();

        String[] whereClauseArgument = new String[1];
        whereClauseArgument[0] = deleteDetails.getName();

        sqliteDatabase.delete(AndroidOpenDbHelper2.TABLE_NAME1, AndroidOpenDbHelper2.COLUMN_NAME+"=?", whereClauseArgument);

        sqliteDatabase.close();
        
        // toast message
     	Toast.makeText(this, "CheckList Succesfully Deleted", Toast.LENGTH_SHORT).show();	
     	
        finish();
    }
}
