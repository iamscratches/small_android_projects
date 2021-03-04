package com.iamscratches.firebaseprojectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText tvMsg;
    DatabaseReference myRef, myRef2;
    FirebaseDatabase database;
    EditText etAge, etUsername, etPassword;
    FirebaseRemoteConfig mFirebaseRemoteConfig;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        define();
        firebaseRealtimeDatabase();
        firebaseMessagingNotification();
        firebaseRemoteConfig();
        firebaseStorage();

    }

    public void buGet(View view) {
        database = FirebaseDatabase.getInstance();
        myRef= database.getReference("msg");
        myRef.setValue(tvMsg.getText().toString());


    }

    public void setValue(View view) {

        myRef2.child("Users").child(etUsername.getText().toString()).child("Username").setValue(etUsername.getText().toString());
        myRef2.child("Users").child(etUsername.getText().toString()).child("Password").setValue(etPassword.getText().toString());
        myRef2.child("Users").child(etUsername.getText().toString()).child("age").setValue(etAge.getText().toString());
    }

    private void define(){
        tvMsg = (EditText)findViewById(R.id.tvMsg);
        etAge = (EditText)findViewById(R.id.etAge);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etUsername = (EditText)findViewById(R.id.etUsername);
        imageView = (ImageView)findViewById(R.id.imageView);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("msg");
        myRef2 = database.getReference();
    }

    private void firebaseRealtimeDatabase(){
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("TAG", "Value is: " + value);
                tvMsg.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
    private void firebaseMessagingNotification(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt);
                        Log.d("TAG", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void firebaseRemoteConfig(){
        long exp = 5;
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.price_tool);
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(exp)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(this,  new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    String price = mFirebaseRemoteConfig.getString("price");
                    Toast.makeText(getApplicationContext(), price, Toast.LENGTH_LONG).show();
                    etAge.setText(price);
                }
            }

        });
        try{
            int x = 10/0;
        }catch (Exception e){

        }

    }

    private void firebaseStorage(){

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://androidproject-304415.appspot.com");
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child("images/mountains.png");
        // Get the data from an ImageView as bytes
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                String downloadURL = taskSnapshot.getMetadata().getBucket();
                Toast.makeText(getApplicationContext(), downloadURL, Toast.LENGTH_LONG).show();
            }
        });
    }
}