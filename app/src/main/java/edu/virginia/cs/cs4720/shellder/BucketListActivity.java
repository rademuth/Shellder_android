package edu.virginia.cs.cs4720.shellder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class BucketListActivity extends AppCompatActivity {

    public enum State {
        ALL, COMPLETE, INPROGRESS
    }
    private State state;

    private LinearLayout tab_all;
    private LinearLayout tab_complete;
    private LinearLayout tab_inProgress;

    private ListView bucketList;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("BucketListActivity", "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_list);

        tab_complete = (LinearLayout) findViewById(R.id.linearLayout_inner1);
        tab_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_complete(v);
            }
        });
        tab_all = (LinearLayout) findViewById(R.id.linearLayout_inner2);
        tab_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_all(v);
            }
        });
        tab_inProgress = (LinearLayout) findViewById(R.id.linearLayout_inner3);
        tab_inProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_inProgress(v);
            }
        });

        bucketList = (ListView) findViewById(R.id.listView);
        adapter = new CustomAdapter(this, State.ALL);

        bucketList.setAdapter(adapter);
        state = State.ALL;

        bucketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BucketListItem bucketListItem = adapter.getItem(position);
                // Send an intent to the map activity
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("id",bucketListItem.getId());
                startActivity(intent);
            }
        });

    }

    public void show_all(View v) {
        if (state != State.ALL) {
            state = State.ALL;
            adapter = new CustomAdapter(this, State.ALL);

            bucketList.setAdapter(adapter);

            bucketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BucketListItem bucketListItem = adapter.getItem(position);
                    // Send an intent to the map activity
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("id",bucketListItem.getId());
                    startActivity(intent);
                }
            });
        }
    }

    public void show_complete(View v) {
        if (state != State.COMPLETE) {
            state = State.COMPLETE;
            adapter = new CustomAdapter(this, State.COMPLETE);

            bucketList.setAdapter(adapter);

            bucketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BucketListItem bucketListItem = adapter.getItem(position);
                    // Send an intent to the map activity
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("id",bucketListItem.getId());
                    startActivity(intent);
                }
            });
        }
    }

    public void show_inProgress(View v) {
        if (state != State.INPROGRESS) {
            state = State.INPROGRESS;
            adapter = new CustomAdapter(this, State.INPROGRESS);

            bucketList.setAdapter(adapter);

            bucketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BucketListItem bucketListItem = adapter.getItem(position);
                    // Send an intent to the map activity
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("id",bucketListItem.getId());
                    startActivity(intent);
                }
            });
        }
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
