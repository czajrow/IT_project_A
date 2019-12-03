package com.example.scheduler;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "MapsActivity";

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Map<String, LatLng> pins = MapsPins.pins;

        if (!pins.isEmpty()) {
            for (Map.Entry<String, LatLng> entry : pins.entrySet()) {
                mMap.addMarker(new MarkerOptions().position(entry.getValue()).title(entry.getKey()));
            }
            LatLng first = pins.get(MapsPins.pins.keySet().toArray()[0]);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(first));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(first, (float) 16));
        } else {
            // main building
            LatLng mainBuilding = new LatLng(46.896139, 19.66875);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mainBuilding));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mainBuilding, (float) 16));
        }


    }
}
