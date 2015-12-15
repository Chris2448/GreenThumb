package ca.concordia.sensortag.weather;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class AndroidOpenDbHelper extends SQLiteOpenHelper{

    // Database attributes
    public static final String DB_NAME = "database";
    public static final int DB_VERSION = 1;

    // Table attributes
    public static final String TABLE_NAME = "table_notes";
    public static final String COLUMN_NAME = "column_name";
    public static final String COLUMN_DATE = "column_date";
    public static final String COLUMN_NOTE = "column_note";

	
	public AndroidOpenDbHelper(Context context) 
	{
		
		// TODO Auto-generated constructor stub
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sqlQuery = "create table if not exists " + TABLE_NAME + " ( " + BaseColumns._ID + " integer primary key autoincrement, "
                + COLUMN_NAME + " text not null, "
                + COLUMN_DATE + " text not null, "
                + COLUMN_NOTE + " real not null);";

        db.execSQL(sqlQuery);

		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//No upgrade planned
	}

}
