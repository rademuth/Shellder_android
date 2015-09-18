package edu.virginia.cs.cs4720.shellder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    // TODO - Do we need to close the database
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private BucketListItem bucketListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("MapsActivity","onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Get data from the bucket list activity
        Intent intent = getIntent();
        int index = intent.getIntExtra("id", 0);

        Log.i("Index",index+"");

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getReadableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = {index + ""};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();
        bucketListItem = new BucketListItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getFloat(3), cursor.getFloat(4), cursor.getInt(5) > 0);
        cursor.close();

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {

        Log.i("MapsActivity","onResume()");

        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from thde SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setMyLocationEnabled(true);

        /*

        // Get current location
        Location location = mMap.getMyLocation();
        double my_latitude = location.getLatitude();
        double my_longitude = location.getLongitude();

        */

        // Add marker for the bucket list item location
        float latitude = bucketListItem.getLatitude();
        float longitude = bucketListItem.getLongitude();
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));

        /*

        // Zoom appropriately
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(my_latitude, my_longitude));
        builder.include(new LatLng(latitude, longitude));
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        mMap.moveCamera(cu);

        */

    }
}
