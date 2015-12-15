package ca.concordia.sensortag.weather;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;




public class NotesListActivity extends Activity implements OnClickListener,OnItemClickListener{

    private ListView NotesListView;
    private Button addNoteButton;
    private Button returnButton;
    private ListAdapter NotesListAdapter;
    private ArrayList<Details> dataArrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes_list);
        NotesListView = (ListView) findViewById(R.id.listView);
        NotesListView.setOnItemClickListener(this);
        addNoteButton = (Button) findViewById(R.id.addnote);
        addNoteButton.setOnClickListener(this);
        returnButton = (Button) findViewById(R.id.returnmain);
        returnButton.setOnClickListener(this);
        dataArrayList = new ArrayList<Details>();
        NotesListView.setAdapter(NotesListAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notes_list, menu);
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
	public void onItemClick(AdapterView<?> param0, View param1, int param2, long param3) 
	{
		// TODO Auto-generated method stub
        Toast.makeText(getApplicationContext(), "Clicked on :" + param2, Toast.LENGTH_SHORT).show();

        Intent updateDeleteIntent = new Intent(this, UpdateDeleteActivity.class);

        Details clickedObject = dataArrayList.get(param2);

        Bundle dataBundle = new Bundle();
        dataBundle.putString("clickedName", clickedObject.getName());
        dataBundle.putString("clickedDate", clickedObject.getDate());
        dataBundle.putString("clickedNote", clickedObject.getNote());

        updateDeleteIntent.putExtras(dataBundle);

        startActivity(updateDeleteIntent);
    }

		
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.addnote)
		{
		Intent addNewNoteIntent = new Intent(this, AddNewNoteActivity.class);
        startActivity(addNewNoteIntent);
		}
		else if(v.getId() == R.id.returnmain)
		{
		Intent returntomainIntent = new Intent(this, WeatherStationActivity.class);
	    startActivity(returntomainIntent);	
		}

	}
	
    public List<String> populateList()
    {
        List<String> noteNamesList = new ArrayList<String>();

        AndroidOpenDbHelper openHelperClass = new AndroidOpenDbHelper(this);

        SQLiteDatabase sqliteDatabase = openHelperClass.getReadableDatabase();

       Cursor cursor = sqliteDatabase.query(AndroidOpenDbHelper.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext())
        {
            String mName = cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.COLUMN_NAME));
            String mDate = cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.COLUMN_DATE));
            String mNote = cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.COLUMN_NOTE));

            Details mClass = new Details();

            mClass.setName(mName);
            mClass.setDate(mDate);
            mClass.setNote(mNote);

            dataArrayList.add(mClass);

            noteNamesList.add(mName);
        }

 
        sqliteDatabase.close();

        return noteNamesList;
    }

    protected void onResume()
    {
        super.onResume();
        NotesListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, populateList());
        NotesListView.setAdapter(NotesListAdapter);
    }

    protected void onStart()
    {
        super.onStart();
        dataArrayList = new ArrayList<Details>();
        NotesListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, populateList());
        NotesListView.setAdapter(NotesListAdapter);
    }
    
    
}
