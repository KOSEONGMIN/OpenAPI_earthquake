package com.example.koseongmin.project01;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    Double latitude;
    Double longitude;
    private GoogleMap mMap;
    Intent intent;
    private ArrayList<String> lon = new ArrayList<String>();
    private ArrayList<String> lat = new ArrayList<String>();
    private ArrayList<String> vt_acmdfclty_nm = new ArrayList<String>();
    private ArrayList<String> dtl_adres = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        intent = getIntent();

        latitude = intent.getDoubleExtra("latitude", 37.450770);
        longitude = intent.getDoubleExtra("longitude", 127.128844);

        StrictMode.enableDefaults();
        APIThread apiThread = new APIThread(latitude, longitude);
        apiThread.run();
        lon = apiThread.getLon();
        lat = apiThread.getLat();
        vt_acmdfclty_nm = apiThread.getVt_acmdfclty_nm();
        dtl_adres = apiThread.getDtl_adres();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }





    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;




        // Add a marker in Sydney and move the camera
        LatLng myLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(myLocation).title("내 위치")).showInfoWindow();
        for (int i = 1; i < lat.size(); i++) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat.get(i)), Double.parseDouble(lon.get(i)))).snippet(dtl_adres.get(i)).title(vt_acmdfclty_nm.get(i)).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.gps))));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 16));
    }
}
