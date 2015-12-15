package ca.concordia.sensortag.weather;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.content.ContentValues;


public class UpdateDeleteActivity extends Activity implements OnClickListener{
	 	
		private EditText nameEditText;
	    private EditText dateEditText;
	    private EditText noteEditText;
	    private Button cancelButton;
	    private Button updateButton;
	    private Button deleteButton;
	    private String bundledName;
	    private String bundledDate;
	    private String bundledNote;
	    private String nameEditTextVal;
	    private String dateEditTextVal;
	    private String noteEditTextVal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_delete);
		 nameEditText = (EditText) findViewById(R.id.name2);
	        dateEditText = (EditText) findViewById(R.id.date2);
	        noteEditText = (EditText) findViewById(R.id.note2);

	        cancelButton = (Button) findViewById(R.id.cancel2);
	        cancelButton.setOnClickListener(this);
	        updateButton = (Button) findViewById(R.id.update);
	        updateButton.setOnClickListener(this);
	        deleteButton = (Button) findViewById(R.id.delete);
	        deleteButton.setOnClickListener(this);

	        Bundle takeBundledData = getIntent().getExtras();


	        bundledName = takeBundledData.getString("clickedName");
	        bundledDate = takeBundledData.getString("clickedDate");
	        bundledNote = takeBundledData.getString("clickedNote");


	        nameEditText.setText(bundledName);
	        dateEditText.setText(bundledDate);
	        noteEditText.setText(bundledNote);

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

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
        nameEditTextVal = nameEditText.getText().toString();
        dateEditTextVal = dateEditText.getText().toString();
        noteEditTextVal = noteEditText.getText().toString();

        Details noteDetails = new Details();

        noteDetails.setName(bundledName);
        noteDetails.setDate(bundledDate);
        noteDetails.setNote(bundledNote);

        if(v.getId() == R.id.cancel2)
        {
            Intent returnIntent = new Intent(this, NotesListActivity.class);
            startActivity(returnIntent);
        }
        else if(v.getId() == R.id.update)
        {
            updateDetails(noteDetails);
            Intent returnIntent = new Intent(this, NotesListActivity.class);
            startActivity(returnIntent);
        }
        else if(v.getId() == R.id.delete)
        {
            deleteDetails(noteDetails);
            Intent returnIntent = new Intent(this, NotesListActivity.class);
            startActivity(returnIntent);
        }

	}
	
    private void updateDetails(Details noteDetails)
    {

        AndroidOpenDbHelper androidOpenDbHelper = new AndroidOpenDbHelper(this);
        SQLiteDatabase sqliteDatabase = androidOpenDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(AndroidOpenDbHelper.COLUMN_NAME, nameEditTextVal);
        contentValues.put(AndroidOpenDbHelper.COLUMN_DATE, dateEditTextVal);
        contentValues.put(AndroidOpenDbHelper.COLUMN_NOTE, noteEditTextVal);

        String[] whereClauseArgument = new String[1];
        whereClauseArgument[0] = noteDetails.getName();

        System.out.println("whereClauseArgument[0] is :" + whereClauseArgument[0]);

        sqliteDatabase.update(AndroidOpenDbHelper.TABLE_NAME, contentValues, AndroidOpenDbHelper.COLUMN_NAME+"=?", whereClauseArgument);

        sqliteDatabase.close();

        finish();
    }

    private void deleteDetails(Details deleteDetails)
    {

        AndroidOpenDbHelper androidOpenDbHelper = new AndroidOpenDbHelper(this);
        SQLiteDatabase sqliteDatabase = androidOpenDbHelper.getWritableDatabase();

        String[] whereClauseArgument = new String[1];
        whereClauseArgument[0] = deleteDetails.getName();

        sqliteDatabase.delete(AndroidOpenDbHelper.TABLE_NAME, AndroidOpenDbHelper.COLUMN_NAME+"=?", whereClauseArgument);

        sqliteDatabase.close();
        finish();
    }

}
