package edu.virginia.cs.cs4720.shellder;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Robbie on 9/10/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bucket_list.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME = "bucket_list_items";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_COMPLETE = "complete";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (" + COLUMN_ID + " integer, " +  COLUMN_DESCRIPTION + " text, " + COLUMN_COMPLETE + " integer);");

        Resources res = this.context.getResources();
        String[] descriptions = res.getStringArray(R.array.bucket_list_descriptions);

        for (int i = 0; i < 116; i++) {
            ContentValues values = new ContentValues();
            values.put(this.COLUMN_ID, i+1);
            values.put(this.COLUMN_DESCRIPTION, descriptions[i]);
            values.put(this.COLUMN_COMPLETE, 0);
            db.insert(TABLE_NAME,null,values);
        }
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p>
     * <p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
