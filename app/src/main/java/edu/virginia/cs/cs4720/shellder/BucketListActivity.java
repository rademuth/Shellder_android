package edu.virginia.cs.cs4720.shellder;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class BucketListActivity extends AppCompatActivity {

    public enum State {
        ALL, COMPLETE, INPROGRESS
    }
    private State state;

    private LinearLayout tab_all;
    private LinearLayout tab_complete;
    private LinearLayout tab_inProgress;

    private ImageView image_all;
    private ImageView image_complete;
    private ImageView image_inProgress;

    private TextView text_all;
    private TextView text_complete;
    private TextView text_inProgress;

    private ListView bucketList;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("BucketListActivity", "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_list);

        tab_complete = (LinearLayout) findViewById(R.id.linearLayout_inner1);
        image_complete = (ImageView) findViewById(R.id.image1);
        text_complete = (TextView) findViewById(R.id.complete_items);
        tab_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_complete(v);
            }
        });
        tab_all = (LinearLayout) findViewById(R.id.linearLayout_inner2);
        image_all = (ImageView) findViewById(R.id.image2);
        text_all = (TextView) findViewById(R.id.all_items);
        tab_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_all(v);
            }
        });
        tab_inProgress = (LinearLayout) findViewById(R.id.linearLayout_inner3);
        image_inProgress = (ImageView) findViewById(R.id.image3);
        text_inProgress = (TextView) findViewById(R.id.remaining_items);
        tab_inProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_inProgress(v);
            }
        });

        bucketList = (ListView) findViewById(R.id.listView);

        if (savedInstanceState == null) {
            show_all(null);
        } else {
            State s = (State) savedInstanceState.getSerializable("state");
            if (s == State.COMPLETE) {
                show_complete(null);
            } else if (s == State.INPROGRESS) {
                show_inProgress(null);
            } else {
                show_all(null);
            }
        }
    }

    public void show(State s) {
        state = s;
        adapter = new CustomAdapter(this, s);

        bucketList.setAdapter(adapter);

        bucketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BucketListItem bucketListItem = adapter.getItem(position);
                // Send an intent to the map activity
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("id", bucketListItem.getId());
                startActivity(intent);
            }
        });
    }

    public void show_complete(View v) {
        if (state != State.COMPLETE) {
            // Change tab display settings
            tab_complete.setBackgroundColor(Color.GRAY);
            tab_all.setBackgroundColor(Color.TRANSPARENT);
            tab_inProgress.setBackgroundColor(Color.TRANSPARENT);
            image_complete.setImageResource(R.drawable.ic_done_light);
            image_all.setImageResource(R.drawable.ic_list_dark);
            image_inProgress.setImageResource(R.drawable.ic_progress_dark);
            text_complete.setTextColor(Color.WHITE);
            text_all.setTextColor(Color.GRAY);
            text_inProgress.setTextColor(Color.GRAY);
            show(State.COMPLETE);
        }
    }

    public void show_all(View v) {
        if (state != State.ALL) {
            // Change tab display settings
            tab_complete.setBackgroundColor(Color.TRANSPARENT);
            tab_all.setBackgroundColor(Color.GRAY);
            tab_inProgress.setBackgroundColor(Color.TRANSPARENT);
            image_complete.setImageResource(R.drawable.ic_done_dark);
            image_all.setImageResource(R.drawable.ic_list_light);
            image_inProgress.setImageResource(R.drawable.ic_progress_dark);
            text_complete.setTextColor(Color.GRAY);
            text_all.setTextColor(Color.WHITE);
            text_inProgress.setTextColor(Color.GRAY);
            show(State.ALL);
        }
    }

    public void show_inProgress(View v) {
        if (state != State.INPROGRESS) {
            // Change tab display settings
            tab_complete.setBackgroundColor(Color.TRANSPARENT);
            tab_all.setBackgroundColor(Color.TRANSPARENT);
            tab_inProgress.setBackgroundColor(Color.GRAY);
            image_complete.setImageResource(R.drawable.ic_done_dark);
            image_all.setImageResource(R.drawable.ic_list_dark);
            image_inProgress.setImageResource(R.drawable.ic_progress_light);
            text_complete.setTextColor(Color.GRAY);
            text_all.setTextColor(Color.GRAY);
            text_inProgress.setTextColor(Color.WHITE);
            show(State.INPROGRESS);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable("state", state);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
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
