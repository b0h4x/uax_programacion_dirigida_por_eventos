package com.example.feedback2;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.feedback2.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Madrid and move the camera
        LatLng madrid = new LatLng(40.41853, -3.69190);
        mMap.addMarker(new MarkerOptions().position(madrid).title(getString(R.string.title_map_marker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(madrid));
        // Get user geo
        mMap.setMyLocationEnabled(true);
        // Listener if user clicks in its position, to show the coordinates.
        mMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {
                Toast.makeText(getBaseContext(), getString(R.string.current_location_user) + location,
                                Toast.LENGTH_LONG)
                        .show();
            }
        });
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(getBaseContext(), R.string.location_button_clicked, Toast.LENGTH_LONG)
                        .show();
                return false;
            }
        });
    }
}