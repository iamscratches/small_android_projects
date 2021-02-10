package com.example.gps_and_maps;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

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
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        myThread th = new myThread();
        th.start();

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        LatLng sydney2 = new LatLng(-34.01, 151.02);
//        LatLng sydney3 = new LatLng(-34.07, 151.03);
//
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.addCircle(new CircleOptions().center(sydney).radius(200).strokeColor(Color.BLUE).fillColor(Color.CYAN));
//        mMap.addPolyline(new PolylineOptions().add(sydney,sydney2, sydney3).width(5).color(Color.BLUE).geodesic(true));
//        mMap.addPolygon(new PolygonOptions().add(sydney, sydney2, sydney3));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,14));

//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                mMap.addMarker(new MarkerOptions().position(latLng).title("Add by click"));
//            }
//        });
    }
    class myThread extends Thread{
        @Override
        public void run() {
            while(true){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMap.clear();
                        if(myLocationListener.location != null) {
                            LatLng mark = new LatLng(myLocationListener.location.getLatitude(), myLocationListener.location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(mark).title("I'm here"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mark, 16));

                        }
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
//public class MapsActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback {
//
//    private GoogleMap mMap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        StreetViewPanoramaFragment mapFragment = (StreetViewPanoramaFragment) getFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getStreetViewPanoramaAsync(this);
//    }
//
//    @Override
//    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
//        streetViewPanorama.setPosition(new LatLng(40.706082, -74.008968));
//        streetViewPanorama.setStreetNamesEnabled(true);
//        streetViewPanorama.setUserNavigationEnabled(true);
//        streetViewPanorama.setZoomGesturesEnabled(true);
//        streetViewPanorama.setPanningGesturesEnabled(true);
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
//}