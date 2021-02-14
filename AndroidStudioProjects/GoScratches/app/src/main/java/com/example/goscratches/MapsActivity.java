package com.example.goscratches;

import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

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
        LoadCherry();
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
        myThread th = new myThread();
        th.start();
    }
    Location oldLocation;
    class myThread extends Thread{
        myThread(){
            oldLocation = new Location("start");
            oldLocation.setLatitude(0);
            oldLocation.setLongitude(0);
        }
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(1000);
                    if(oldLocation.distanceTo(myLocationListener.location) == 0 )
                        continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                oldLocation = myLocationListener.location;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMap.clear();
                        if(myLocationListener.location != null) {
                            LatLng mark = new LatLng(myLocationListener.location.getLatitude(), myLocationListener.location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(mark).title("I'm here").icon(BitmapDescriptorFactory.defaultMarker()));

                            int i=0;
                            while( i<cherryContents.size() ){
                                CherryContent contents = cherryContents.get(i);
                                LatLng cherries = new LatLng(contents.location.getLatitude(), contents.location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(cherries).title("cherry")
                                                .snippet(contents.name + "cherries")
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cherry)));
                                if(myLocationListener.location.distanceTo(contents.location)<20){
                                    cherryContents.remove(i);
                                    i--;
                                }
                                i++;
                            }
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mark, 16));
                        }
                    }
                });

            }
        }
    }
    //list of cherry and super cherry
    ArrayList<CherryContent> cherryContents = new ArrayList<>();
    void LoadCherry(){
        cherryContents.add(new CherryContent("cherry", 5, R.drawable.cherry, false, 22.576881666666665, 88.37330666666666));
        cherryContents.add(new CherryContent("cherry", 5,  R.drawable.cherry, false, 22.577565, 88.37289333333334));
        cherryContents.add(new CherryContent("cherry", 5,  R.drawable.cherry, false, 22.577575, 88.3725555));
    }
}