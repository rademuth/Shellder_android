package edu.virginia.cs.cs4720.shellder;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    // TODO - Do we need to close the database
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // TODO - Figure out why this returns 0 entries

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getReadableDatabase();

        String selection = "complete = ?";
        String[] selectionArgs = {1 + ""};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, null, selection, selectionArgs, null, null, null);

        int numComplete = cursor.getCount();
        int pctComplete = numComplete * 100 / 116;

        cursor.close();

        ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setProgress(pctComplete);

        TextView text = (TextView) findViewById(R.id.progressBarText);
        text.setText("Progress - " + pctComplete + "%");
    }

    public void show_list(View v) {
        // Switch to the bucket list activity
        Intent intent = new Intent(getApplicationContext(), BucketListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
