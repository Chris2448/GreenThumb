package ca.concordia.sensortag.weather;

//import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import java.util.ArrayList;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddNewNoteActivity extends Activity implements OnClickListener{

	private EditText nameEditText;
    private EditText dateEditText;
    private EditText noteEditText;
    private Button cancelButton;
    private Button saveButton;
    private ArrayList detailsArrayList; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_note);
		nameEditText = (EditText) findViewById(R.id.name);
        dateEditText = (EditText) findViewById(R.id.date);
        noteEditText = (EditText) findViewById(R.id.note);
 
        cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);
        saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(this);

        detailsArrayList = new ArrayList();
 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_note, menu);
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
		if (v.getId() == R.id.cancel)
        {
            Intent returnIntent = new Intent(this, NotesListActivity.class);
            startActivity(returnIntent);
        }
        else if (v.getId() == R.id.save)
        {
            String providedName = nameEditText.getText().toString();
            String providedDate = dateEditText.getText().toString();
            String providedNote = noteEditText.getText().toString();

            Details detailsObject = new Details();
            detailsObject.setName(providedName);
            detailsObject.setDate(providedDate);
            detailsObject.setNote(providedNote);

            detailsArrayList.add(detailsObject);

            insertNote(detailsObject);

            Intent returnIntent = new Intent(this, NotesListActivity.class);
            startActivity(returnIntent);
        }

	}
    public void insertNote(Details insertDetail)
    {


        AndroidOpenDbHelper androidOpenDbHelperObj = new AndroidOpenDbHelper(this);


        SQLiteDatabase sqliteDatabase = androidOpenDbHelperObj.getWritableDatabase();


        ContentValues contentValues = new ContentValues();


        contentValues.put(AndroidOpenDbHelper.COLUMN_NAME, insertDetail.getName());
        contentValues.put(AndroidOpenDbHelper.COLUMN_DATE, insertDetail.getDate());
        contentValues.put(AndroidOpenDbHelper.COLUMN_NOTE, insertDetail.getNote());


       long affectedColumnId = sqliteDatabase.insert(AndroidOpenDbHelper.TABLE_NAME, null, contentValues);

       sqliteDatabase.close();

       Toast.makeText(this, "Column ID is :" + affectedColumnId, Toast.LENGTH_SHORT).show();
    }

}
