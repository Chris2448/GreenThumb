package ca.concordia.sensortag.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class AndroidOpenDbHelper2 extends SQLiteOpenHelper {
	
	// Database attributes
    public static final String DB_NAME = "Checklist_db";
    public static final int DB_VERSION = 1;
    
    // Table attributes
    public static final String TABLE_NAME1 = "table_checklist2";
    public static final String COLUMN_DATE = "column_date";
    public static final String COLUMN_NAME = "column_name";
    public static final String COLUMN_WATER = "column_water";
    public static final String COLUMN_FERT = "column_fertilize";
    public static final String COLUMN_ROT = "column_rotate";
    public static final String COLUMN_PRUNE = "column_prune";
    public static final String COLUMN_WEED = "column_weed";
    public static final String COLUMN_INSEC = "column_insecticide";
    public static final String COLUMN_GERM = "column_germinate";
    public static final String COLUMN_PLANT = "column_plant";
    public static final String COLUMN_HARV = "column_harvest";
    
    public AndroidOpenDbHelper2(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate (SQLiteDatabase db)
    {
        String sqlQuery = "create table if not exists " + TABLE_NAME1 + " ( " + BaseColumns._ID + " integer primary key autoincrement, "
        		+ COLUMN_NAME + " text not null, "
                + COLUMN_DATE + " text not null, "
                + COLUMN_WATER + " text not null, "
                + COLUMN_FERT + " text not null, "
                + COLUMN_ROT + " text not null, "
                + COLUMN_PRUNE + " text not null, "
                + COLUMN_WEED + " text not null, "
                + COLUMN_INSEC + " text not null, "
                + COLUMN_GERM + " text not null, "
                + COLUMN_PLANT + " text not null, "
                + COLUMN_HARV + " text not null);";

        db.execSQL(sqlQuery);
    }
    
    @Override
    public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion)
    {
        //No upgrade
    }

}
