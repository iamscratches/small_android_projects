package com.iamscratches.prichatscratches.view.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.iamscratches.prichatscratches.R;
import com.iamscratches.prichatscratches.databinding.ActivitySettingsBinding;
import com.iamscratches.prichatscratches.view.MainActivity;
import com.iamscratches.prichatscratches.view.auth.PhoneLoginActivity;
import com.iamscratches.prichatscratches.view.profile.ProfileActivity;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        progressDialog = new ProgressDialog(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        if(firebaseUser!=null){
            getInfo();
        }

        initClickAction();
    }

    private void initClickAction(){
        binding.lnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
                finish();
            }
        });
    }

    private void getInfo(){
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        firebaseFirestore.collection("Users").document(firebaseUser.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String username = Objects.requireNonNull(documentSnapshot.get("username")).toString();
                        String bio = Objects.requireNonNull(documentSnapshot.get("bio")).toString();
                        String imageProfile = documentSnapshot.get("imageProfile").toString();
                        if(imageProfile.length()>0){
                            Glide.with(getApplicationContext()).load(imageProfile).into(binding.imageProfile);
                        }
                        binding.tvUsername.setText(username);
                        binding.tvBio.setText(bio);
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Get Data", "onFailure: " + e.getMessage());
                Toast.makeText(getApplicationContext(), "Something went wrong pls try again", Toast.LENGTH_LONG).show();
                finish();
                progressDialog.dismiss();
            }
        });
    }
}