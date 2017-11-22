package edu.orangecoastcollege.cs273.caffeinefinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationDetailsActivity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    private Location selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        TextView locationDetailsNameTextView = (TextView) findViewById(R.id.nameDetailsTextView);
        TextView locationDetailsAddressTextView = (TextView) findViewById(R.id.addressDetailsTextView);
        TextView locationDetailsPhoneextView = (TextView) findViewById(R.id.phoneDetailsTextView);


        // NEW WAY
        selectedLocation = getIntent().getExtras().getParcelable("SelectedLocation");

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.locationDetailsMapFragment);
        mapFragment.getMapAsync(this);

        locationDetailsNameTextView.setText(selectedLocation.getName());
        locationDetailsAddressTextView.setText(selectedLocation.getAddress());
        locationDetailsPhoneextView.setText(selectedLocation.getPhone());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // This method is called *AFTER* the map is loaded from Google Play Services
        // At this point the map is ready

        // Store the reference to the Google Map in our member variable
        mMap = googleMap;

        // Custom marker (Big Blue one - my_marker.png)
        LatLng myPosition = new LatLng(selectedLocation.getLatitude(), selectedLocation.getLongitude());

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

    }
}
