package com.example.goscratches;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusScreen extends AppCompatActivity {


    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    LocationManager lm;
    myLocationListener locationListener;
    EditText etName, etAge, etCherry, etDistance, etSuperCherry;
    shareRef sharedRef;
    Button buSetEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_screen);

        CheckUserPermissions();// Check for GPS permissions

        defineViews();// initialise EditText, Button and SharedPreferences

        collectData();// Collect sharedRef data and set data in the views

    }

    void defineViews(){
        etName = (EditText)findViewById(R.id.etName);
        etAge = (EditText)findViewById(R.id.etAge);
        etCherry = (EditText)findViewById(R.id.etCherry);
        etDistance = (EditText)findViewById(R.id.etDistance);
        etSuperCherry = (EditText)findViewById(R.id.etSuperCherry);
        buSetEdit = (Button)findViewById(R.id.buSetEdit);
        sharedRef = new shareRef(this);
    }
    void collectData(){
        StoredData sd = sharedRef.loadData();
        etName.setText(sd.Name);
        etAge.setText(String.valueOf(sd.Age));
        etCherry.setText(String.valueOf(sd.Cherry));
        etDistance.setText(String.valueOf(sd.Distance));
        etSuperCherry.setText(String.valueOf(sd.SuperCherry));
    }
    void CheckUserPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }

        getLocation();// init the contact list

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();// init the contact list
                } else {
                    // Permission Denied
                    Toast.makeText(this, "you denied location access", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @SuppressLint("MissingPermission")
    void getLocation() {
        locationListener = new myLocationListener(this);
        lm =(LocationManager)this.getSystemService(LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,10, locationListener);

    }

    public void setEdit(View view) {

        if(buSetEdit.getText().toString().equalsIgnoreCase("Edit")){
            etName.setEnabled(true);
            etAge.setEnabled(true);
            buSetEdit.setText("save");
        }
        else{
            sharedRef.saveData(etName.getText().toString(), Integer.parseInt(etAge.getText().toString()));
            etName.setEnabled(false);
            etAge.setEnabled(false);
            buSetEdit.setText("edit");
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_LONG).show();
        }
    }
}