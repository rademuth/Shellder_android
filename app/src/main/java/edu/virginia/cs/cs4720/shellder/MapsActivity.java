package edu.virginia.cs.cs4720.shellder;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MapsActivity extends AppCompatActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    // TODO - Do we need to close the database
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private BucketListItem bucketListItem;
    private Location currentLocation;

    private ImageView imageView;
    private String tmpPath;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get data from the bucket list activity
        Intent intent = getIntent();
        final int index = intent.getIntExtra("id", 0);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getReadableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = {index + ""};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();
        bucketListItem = new BucketListItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getFloat(3), cursor.getFloat(4), cursor.getString(5), cursor.getInt(6) > 0);
        cursor.close();

        final TextView id = (TextView) findViewById(R.id.textView1_map);
        id.setText(bucketListItem.getId() + "");

        final TextView title = (TextView) findViewById(R.id.textView2_map);
        title.setText(bucketListItem.getTitle());

        final CheckBox complete = (CheckBox) findViewById(R.id.checkBox1_map);
        complete.setChecked(bucketListItem.getComplete());

        final Button finish = (Button) findViewById(R.id.finishButton);
        finish.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_COMPLETE, 1);
                String select = "id = ?";
                String[] selectArgs = {index + ""};
                database.update(DatabaseHelper.TABLE_NAME, values, select, selectArgs);
                Intent i = new Intent(getApplicationContext(), BucketListActivity.class);
                startActivity(i);
            }
        });

        imageView = (ImageView) findViewById(R.id.imageView);

        tmpPath = bucketListItem.getPhotoPath();

        if (tmpPath.length() > 0) {
            File f = new File(tmpPath);
            Uri uri = Uri.fromFile(f);
            imageView.setImageURI(uri);
            ViewGroup.LayoutParams lp = imageView.getLayoutParams();
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        }

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
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

        // Add marker for the bucket list item location if a location exists
        final float latitude = bucketListItem.getLatitude();
        final float longitude = bucketListItem.getLongitude();
        final LatLng target = new LatLng(latitude, longitude);
        if (latitude != 0 || longitude != 0) {
            mMap.addMarker(new MarkerOptions().position(target));
        }

        // Zoom the map around
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (currentLocation == null) {
                    currentLocation = location;
                    double my_latitude = location.getLatitude();
                    double my_longitude = location.getLongitude();
                    LatLng source = new LatLng(my_latitude, my_longitude);
                    if (latitude == 0 && longitude == 0) {
                        // Only zoom around the current location
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(source).zoom(15).build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    } else {
                        // Zoom around the current location and the target
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        builder.include(source);
                        builder.include(target);
                        LatLngBounds bounds = builder.build();
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 250);
                        mMap.animateCamera(cu);
                    }
                }
            }
        });
    }

    // http://developer.android.com/training/camera/photobasics.html

    public void takePhoto(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Toast.makeText(this, "There was a problem saving the photo...", Toast.LENGTH_SHORT).show();
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoUri = Uri.fromFile(photoFile);
            tmpPath = photoUri.getPath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // User successfully took a new photo
            Toast.makeText(this, "Photo saved successfully...", Toast.LENGTH_SHORT).show();

            // Update the bucket list item
            bucketListItem.setPhotoPath(tmpPath);
            // Add the photo to the gallery
            galleryAddPic();
            // Add the photo to the database
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_PHOTO_PATH, bucketListItem.getPhotoPath());
            String select = "id = ?";
            String[] selectArgs = {bucketListItem.getId() + ""};
            database.update(DatabaseHelper.TABLE_NAME, values, select, selectArgs);

            // Set the image view
            //File f = new File(tmpPath);
            //Uri uri = Uri.fromFile(f);
            //imageView.setImageURI(uri);
            imageView.setImageURI(Uri.fromFile(new File(tmpPath)));
            ViewGroup.LayoutParams lp = imageView.getLayoutParams();
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            // User did not take a new photo

            // Reset the photo path
            tmpPath = bucketListItem.getPhotoPath();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

    }

    private void galleryAddPic() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        //File f = new File(tmpPath);
        //Uri contentUri = Uri.fromFile(f);
        //intent.setData(contentUri);
        intent.setData(Uri.fromFile(new File(tmpPath)));
        this.sendBroadcast(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
