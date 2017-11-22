package edu.orangecoastcollege.cs273.caffeinefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

//TODO: (1) Implement the OnMapReadCallback interface for Google Maps
//TODO: First, you'll need to compile GoogleMaps in build.gradle
//TODO: and add permissions and your Google Maps API key in the AndroidManifest.xml
public class CaffeineListActivity extends AppCompatActivity implements OnMapReadyCallback {

    private DBHelper db;
    private List<Location> allLocationsList;
    private ListView locationsListView;
    private LocationListAdapter locationListAdapter;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caffeine_list);

        deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);
        db.importLocationsFromCSV("locations.csv");

        allLocationsList = db.getAllCaffeineLocations();
        locationsListView = (ListView) findViewById(R.id.locationsListView);
        locationListAdapter = new LocationListAdapter(this, R.layout.location_list_item, allLocationsList);
        locationsListView.setAdapter(locationListAdapter);

        // COMPLETED: (2) Load the support map fragment asynchronously
        // Instruct Android to load a Google Map into our fragment (caffeineMapFregment)
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.caffeineMapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // This method is called *AFTER* the map is loaded from Google Play Services
        // At this point the map is ready

        // Store the reference to the Google Map in our member variable
        mMap = googleMap;

        // Custom marker (Big Blue one - my_marker.png)
        LatLng myPosition = new LatLng(33.671028, -117.911305); // being hardcoded this time

        // Add a custom marker at "myPosition"
        mMap.addMarker(new MarkerOptions()
                .position(myPosition)
                .title("My Location")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.my_marker)));

        // Center the camera over myPosition (instead of Africa)
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(myPosition)
                .zoom(15.0f)
                .build(); // zoom from 0 to 24

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

        // Move the map from Africa to our cameraUpdate
        mMap.moveCamera(cameraUpdate);

        // Now, let's plot each location from the list with a standard marker
        for (Location location : allLocationsList)
        {
            LatLng caffeineLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(caffeineLocation).title(location.getName()));
        }
    }

    public void viewLocationDetails(View v)
    {
        if (v instanceof LinearLayout) {
            LinearLayout selectedLayout = (LinearLayout) v;
            Location selectedLocation = (Location) selectedLayout.getTag();
            Log.i("Gamers Delight", selectedLocation.toString());
            Intent detailsIntent = new Intent(this, LocationDetailsActivity.class);
            /* OLD WAY
            detailsIntent.putExtra("Name", selectedGame.getName());
            detailsIntent.putExtra("Description", selectedGame.getDescription());
            detailsIntent.putExtra("Rating", selectedGame.getRating());
            detailsIntent.putExtra("ImageName", selectedGame.getImageName());
            */
            // NEW WAY:
            detailsIntent.putExtra("SelectedLocation", selectedLocation);

            startActivity(detailsIntent);
        }
    }
    // TODO: (3) Implement the onMapReady method, which will add a special "marker" for our current location,
    // TODO: which is 33.671028, -117.911305  (MBCC 139)
    // TODO: Then add normal markers for all the caffeine locations from the allLocationsList.
    // TODO: Set the zoom level of the map to 15.0f


    // TODO: (4) Create a viewLocationsDetails(View v) method to create a new Intent to the
    // TODO: CaffeineDetailsActivity class, sending it the selectedLocation the user picked from the locationsListView
}
